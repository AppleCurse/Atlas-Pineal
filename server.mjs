import http from 'node:http';
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const PUBLIC_DIR = path.resolve(__dirname, 'public');
const PORT = Number(process.env.PORT || 3000);

const MIME_TYPES = {
  '.html': 'text/html; charset=utf-8',
  '.css': 'text/css; charset=utf-8',
  '.js': 'text/javascript; charset=utf-8',
  '.json': 'application/json; charset=utf-8',
  '.png': 'image/png',
  '.jpg': 'image/jpeg',
  '.jpeg': 'image/jpeg',
  '.svg': 'image/svg+xml',
  '.mp4': 'video/mp4',
  '.webm': 'video/webm',
  '.ico': 'image/x-icon',
  '.woff2': 'font/woff2',
  '.ttf': 'font/ttf'
};

const SECURITY_HEADERS = {
  'X-Content-Type-Options': 'nosniff',
  'Referrer-Policy': 'no-referrer',
  'Permissions-Policy': 'camera=(), microphone=(), geolocation=()',
  'Content-Security-Policy': "default-src 'self'; script-src 'self'; style-src 'self'; img-src 'self' data:; font-src 'self'; media-src 'self'; connect-src 'none'; object-src 'none'; base-uri 'self'; form-action 'self'"
};

const server = http.createServer((req, res) => {
  void handleRequest(req, res);
});

async function handleRequest(req, res) {
  for (const [name, value] of Object.entries(SECURITY_HEADERS)) {
    res.setHeader(name, value);
  }

  if (req.method !== 'GET' && req.method !== 'HEAD') {
    res.setHeader('Allow', 'GET, HEAD');
    sendText(res, 405, 'Method not allowed');
    return;
  }

  let pathname;
  try {
    pathname = decodeURIComponent(new URL(req.url || '/', 'http://localhost').pathname);
  } catch {
    sendText(res, 400, 'Bad request');
    return;
  }

  if (pathname.includes('\0')) {
    sendText(res, 400, 'Bad request');
    return;
  }

  const relativePath = pathname === '/' ? 'index.html' : pathname.replace(/^\/+/, '');
  const filePath = path.resolve(PUBLIC_DIR, relativePath);
  const pathFromPublic = path.relative(PUBLIC_DIR, filePath);
  if (pathFromPublic.startsWith('..') || path.isAbsolute(pathFromPublic)) {
    sendText(res, 403, 'Forbidden');
    return;
  }

  let stats;
  try {
    stats = await fs.promises.stat(filePath);
  } catch {
    sendText(res, 404, 'Not found');
    return;
  }

  if (!stats.isFile()) {
    sendText(res, 404, 'Not found');
    return;
  }

  const extension = path.extname(filePath).toLowerCase();
  const contentType = MIME_TYPES[extension] || 'application/octet-stream';
  const rangeHeader = req.headers.range;
  const range = rangeHeader ? parseByteRange(rangeHeader, stats.size) : null;

  if (rangeHeader && !range) {
    res.writeHead(416, {
      ...SECURITY_HEADERS,
      'Content-Range': `bytes */${stats.size}`,
      'Accept-Ranges': 'bytes'
    });
    res.end();
    return;
  }

  const cacheControl = extension === '.html' ? 'no-cache' : 'public, max-age=3600';
  const headers = {
    ...SECURITY_HEADERS,
    'Content-Type': contentType,
    'Accept-Ranges': 'bytes',
    'Cache-Control': cacheControl
  };

  if (range) {
    const contentLength = range.end - range.start + 1;
    res.writeHead(206, {
      ...headers,
      'Content-Range': `bytes ${range.start}-${range.end}/${stats.size}`,
      'Content-Length': contentLength
    });
    if (req.method === 'HEAD') {
      res.end();
      return;
    }
    pipeFile(filePath, { start: range.start, end: range.end }, res);
    return;
  }

  res.writeHead(200, { ...headers, 'Content-Length': stats.size });
  if (req.method === 'HEAD') {
    res.end();
    return;
  }
  pipeFile(filePath, undefined, res);
}

function parseByteRange(header, size) {
  const match = /^bytes=(\d*)-(\d*)$/.exec(header);
  if (!match || size <= 0) return null;

  let start;
  let end;
  if (match[1] === '') {
    const suffixLength = Number(match[2]);
    if (!Number.isSafeInteger(suffixLength) || suffixLength <= 0) return null;
    start = Math.max(size - suffixLength, 0);
    end = size - 1;
  } else {
    start = Number(match[1]);
    end = match[2] === '' ? size - 1 : Number(match[2]);
    if (!Number.isSafeInteger(start) || !Number.isSafeInteger(end)) return null;
    if (start < 0 || start >= size || end < start) return null;
    end = Math.min(end, size - 1);
  }

  return { start, end };
}

function pipeFile(filePath, options, res) {
  const stream = fs.createReadStream(filePath, options);
  stream.on('error', () => {
    if (!res.headersSent) {
      sendText(res, 500, 'Internal server error');
    } else {
      res.destroy();
    }
  });
  stream.pipe(res);
}

function sendText(res, statusCode, message) {
  const body = `${message}\n`;
  res.writeHead(statusCode, {
    ...SECURITY_HEADERS,
    'Content-Type': 'text/plain; charset=utf-8',
    'Content-Length': Buffer.byteLength(body),
    'Cache-Control': 'no-store'
  });
  res.end(body);
}

server.listen(PORT, '0.0.0.0', () => {
  console.log(`Atlas Pineal prototype ready on 0.0.0.0:${PORT}`);
});
