(() => {
  const year = document.querySelector('#current-year');
  if (year) year.textContent = String(new Date().getFullYear());

  // Sesli Manifesto Okuyucu
  const listenButton = document.querySelector('#listen-overview');
  const listenLabel = document.querySelector('#listen-label');
  const speechStatus = document.querySelector('#speech-status');
  const overviewText = [
    'Atlas Pineal. Dijital dünya hafıza değil, kayıt sistemidir. O kayıtlara her an tıklanıyor.',
    'Atlas Pineal der ki: Kayıtları siz yöneteceksiniz. Merkezde hiç kapanmayan Epifiz Gözü var.',
    'Bir hesabı veya kişiyi açık kaynaklardan çözmek, kiminle masada olduğunuzu bilmek, internetteki bütün varlığınızı silmek ya da sıfırdan yeni ve lekesiz bir kişilik inşa etmek isteyenler için özel hizmet sunuyoruz.',
    'Arka planda on iki uzman saha ajanı ve yedi dalga motoruyla çalışan devasa Pineal Epifiz motoru devrededir. Her iddia kanıtla mühürlenir.'
  ].join(' ');

  if (listenButton && listenLabel && speechStatus) {
    listenButton.addEventListener('click', () => {
      if (!('speechSynthesis' in window) || !('SpeechSynthesisUtterance' in window)) {
        speechStatus.textContent = 'Bu tarayıcı sesli okumayı desteklemiyor. Özet metin sayfada mevcuttur.';
        return;
      }

      const synthesis = window.speechSynthesis;
      if (synthesis.speaking || synthesis.pending) {
        synthesis.cancel();
        resetListenButton();
        speechStatus.textContent = 'Sesli özet durduruldu.';
        return;
      }

      const utterance = new SpeechSynthesisUtterance(overviewText);
      utterance.lang = 'tr-TR';
      utterance.rate = 0.94;
      utterance.onstart = () => {
        listenButton.setAttribute('aria-pressed', 'true');
        listenLabel.textContent = 'Dinlemeyi Durdur';
        speechStatus.textContent = 'Manifesto okunuyor...';
      };
      utterance.onend = () => {
        resetListenButton();
        speechStatus.textContent = 'Sesli manifesto tamamlandı.';
      };
      utterance.onerror = () => {
        resetListenButton();
        speechStatus.textContent = 'Ses sentezi başlatılamadı.';
      };

      synthesis.speak(utterance);
    });
  }

  function resetListenButton() {
    if (!listenButton || !listenLabel) return;
    listenButton.setAttribute('aria-pressed', 'false');
    listenLabel.textContent = 'Manifestoyu Dinle (Sesli)';
  }
})();

// Terminal Simülatörü ve Konsol İstek Yöneticisi
const termOutput = document.getElementById('term-output');

function scrollToTerminal() {
  const terminalEl = document.getElementById('terminal-section');
  if (terminalEl) {
    terminalEl.scrollIntoView({ behavior: 'smooth', block: 'center' });
  }
}

function handleConsoleSubmit(event, type) {
  event.preventDefault();
  let inputVal = '';
  if (type === 'target') inputVal = document.getElementById('input-target')?.value || 'Hedef';
  else if (type === 'radar') inputVal = document.getElementById('input-radar')?.value || 'Şahıs/Kurum';
  else if (type === 'purge') inputVal = document.getElementById('input-purge')?.value || 'Silinecek Kayıt';
  else if (type === 'rebuild') inputVal = document.getElementById('input-rebuild')?.value || 'Yeni İtibar Hedefi';

  runSimFlow(type, inputVal);
}

function runPresetSim(type) {
  const presets = {
    target: '@investigate_target',
    radar: 'A. Yılmaz / Finans Danışmanlık',
    purge: '2014-2018 Eski Forum & Sızıntı Kayıtları',
    rebuild: 'Uluslararası Teknoloji Yatırımcısı & Danışman'
  };
  runSimFlow(type, presets[type] || 'Test Girdisi');
}

function runSimFlow(type, targetName) {
  scrollToTerminal();
  if (!termOutput) return;

  const timestamp = new Date().toISOString();
  const mockSha = Array.from({ length: 64 }, () => Math.floor(Math.random() * 16).toString(16)).join('');

  let steps = [];

  if (type === 'target') {
    steps = [
      { text: `[EMİR ALINDI] KONSOL 01: HEDEF İSTİHBARATI BAŞLATILDI -> Girdi: "${targetName}"`, cls: 'term-brass' },
      { text: `[12 SAHA AJANI] OSINT, Depth ve Behavior ajanları açık kaynak dizinlerine salındı...`, cls: 'term-cyan' },
      { text: `[FREQUENCY ENGINE] Hedefin paylaşım saatlerinden sirkadiyen uyku/lokasyon ritmi çıkartılıyor... [UYUŞMA: %94]`, cls: 'term-green' },
      { text: `[COGNITIVE AGENT] Gizlenen ilgi alanları, zafiyetler ve savunmayı düşüren 3 hamleli ilk temas köprüsü hazırlandı.`, cls: 'term-amber' },
      { text: `[VERIFIER] Kanıt Paketi SHA-256 damgasıyla adli olarak mühürlendi: ${mockSha.substring(0, 32)}...`, cls: 'term-brass' },
      { text: `[SONUÇ] Hedef İstihbarat Dosyası oluşturuldu. Tam adli rapor ve operasyonel temas için doğrudan Pineal Operatörü ile görüşebilirsiniz.`, cls: 'term-green' }
    ];
  } else if (type === 'radar') {
    steps = [
      { text: `[EMİR ALINDI] KONSOL 02: KİŞİ GEÇMİŞİ & RİSK RADARI BAŞLATILDI -> Girdi: "${targetName}"`, cls: 'term-brass' },
      { text: `[MIRROR AGENT] Sahnelenen vitrin ile arka plandan sızan gerçeklik arasındaki tutarsızlık puanı hesaplanıyor...`, cls: 'term-cyan' },
      { text: `[SEISMOS ENGINE] Sahte takipçi, bot ağları ve yapay etkileşim çiftlikleri ayrıştırıldı.`, cls: 'term-green' },
      { text: `[STRATA ENGINE] Tarihsel sicil ve çapraz açık kaynak referansları doğrulandı.`, cls: 'term-amber' },
      { text: `[VERIFIER] Kanıt Paketi SHA-256 damgasıyla adli olarak mühürlendi: ${mockSha.substring(0, 32)}...`, cls: 'term-brass' },
      { text: `[SONUÇ] Risk ve Maske Raporu hazırlandı. Masaya oturmadan önceki kritik adli güvence tamamlandı.`, cls: 'term-green' }
    ];
  } else if (type === 'purge') {
    steps = [
      { text: `[EMİR ALINDI] KONSOL 03: PROTOKOL 02 (KÜL) - İZİ SİLME & UNUTULMA -> Girdi: "${targetName}"`, cls: 'term-brass' },
      { text: `[VOID ENGINE] Arama motoru dizinleri, eski forumlar, sızıntı veritabanları ve önbellekler haritalandırıldı.`, cls: 'term-cyan' },
      { text: `[AUTH AGENT] Yasal zemin (KVKK Md. 11 / GDPR Art. 17 Unutulma Hakkı) ve teknik başvuru paketi oluşturuldu.`, cls: 'term-green' },
      { text: `[PULSE ENGINE] İçerik sağlayıcı ve dizin düşürme sinyalleri kuyruğa alındı.`, cls: 'term-amber' },
      { text: `[KÜL KURALI] Teyit edilmeden "silindi" denmez; kaldırma kaynağında adli olarak denetlenir.`, cls: 'term-brass' }
    ];
  } else if (type === 'rebuild') {
    steps = [
      { text: `[EMİR ALINDI] KONSOL 04: PROTOKOL 03 (SEDEF) - YENİ KİŞİLİK İNŞASI -> Girdi: "${targetName}"`, cls: 'term-brass' },
      { text: `[SYNTH AGENT] Yeni itibar mimarisi ve saygın açık kaynak profil haritası kurgulanıyor...`, cls: 'term-cyan' },
      { text: `[KEY ENGINE] Arama motorlarının ilk 3 sayfasında yalnızca saygın, güçlü ve doğru kayıtların belireceği sedef zırhı örüldü.`, cls: 'term-green' },
      { text: `[SOVEREIGN AGENT] İftira ve itibar suikastlerine karşı otomatik karşı kalkan aktif.`, cls: 'term-amber' },
      { text: `[SEDEF KURALI] Geçmiş silinir; yeni insan sarsılmaz bir itibar kabuğu içinde yeniden doğar.`, cls: 'term-brass' }
    ];
  }

  termOutput.innerHTML = `<p class="term-line term-dim">[ZAMAN DAMGASI: ${timestamp}]</p>`;

  let delay = 0;
  steps.forEach((s) => {
    setTimeout(() => {
      const p = document.createElement('p');
      p.className = `term-line ${s.cls}`;
      p.textContent = s.text;
      termOutput.appendChild(p);
    }, delay);
    delay += 400;
  });
}
