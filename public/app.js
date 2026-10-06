(() => {
  const year = document.querySelector('#current-year');
  const menuToggle = document.querySelector('.menu-toggle');
  const primaryNav = document.querySelector('#primary-nav');

  if (year) year.textContent = String(new Date().getFullYear());

  if (menuToggle && primaryNav) {
    const closeMenu = () => {
      menuToggle.setAttribute('aria-expanded', 'false');
      menuToggle.querySelector('.visually-hidden').textContent = 'Gezinme menüsünü aç';
      primaryNav.classList.remove('is-open');
    };

    menuToggle.addEventListener('click', () => {
      const isOpen = menuToggle.getAttribute('aria-expanded') === 'true';
      menuToggle.setAttribute('aria-expanded', String(!isOpen));
      menuToggle.querySelector('.visually-hidden').textContent = isOpen
        ? 'Gezinme menüsünü aç'
        : 'Gezinme menüsünü kapat';
      primaryNav.classList.toggle('is-open', !isOpen);
    });

    primaryNav.addEventListener('click', (event) => {
      if (event.target.closest('a')) closeMenu();
    });

    document.addEventListener('keydown', (event) => {
      if (event.key === 'Escape' && menuToggle.getAttribute('aria-expanded') === 'true') {
        closeMenu();
        menuToggle.focus();
      }
    });
  }

  const scopeCopy = {
    self: {
      title: 'Bu, doğru başlangıç kapsamı.',
      copy: 'Kendi hesaplarınızı ve hakkınızdaki açık kayıtları kaynaklarıyla kontrol edin. Yanlışsa, yayımlayan yerin resmî düzeltme yoluna başvurabilirsiniz.',
      mark: '✓'
    },
    organization: {
      title: 'Yazılı yetki ve net sınır gerekir.',
      copy: 'Temsil ettiğiniz kurum için hangi alanların inceleneceği ve hangi işlemlere izin verildiği önceden belirlenmelidir. Parola veya özel hesap erişimi istenmez.',
      mark: '✓'
    },
    'third-party': {
      title: 'Bu talebi kabul etmeyiz.',
      copy: 'Özel bir kişiyi rızası olmadan araştırmayız; ailesinden, arkadaşlarından, komşularından veya köyünden dedikodu toplamayız. Gerekli bir resmî kontrol varsa, açık rıza ve uygun resmî kaynaklar kullanılmalıdır.',
      mark: '×'
    }
  };

  const scopeResponse = document.querySelector('#scope-response');
  const scopeTitle = document.querySelector('#scope-response-title');
  const scopeDescription = document.querySelector('#scope-response-copy');
  const scopeMark = document.querySelector('#scope-response-mark');
  const scopeButtons = [...document.querySelectorAll('[data-scope-choice]')];

  scopeButtons.forEach((button) => {
    button.addEventListener('click', () => {
      const selectedScope = button.dataset.scopeChoice;
      const response = scopeCopy[selectedScope];
      if (!response) return;

      scopeButtons.forEach((choice) => {
        const selected = choice === button;
        choice.classList.toggle('is-selected', selected);
        choice.setAttribute('aria-pressed', String(selected));
      });

      scopeResponse.classList.toggle('is-boundary', selectedScope === 'third-party');
      scopeTitle.textContent = response.title;
      scopeDescription.textContent = response.copy;
      scopeMark.textContent = response.mark;
    });
  });

  const listenButton = document.querySelector('#listen-overview');
  const listenLabel = document.querySelector('#listen-label');
  const speechStatus = document.querySelector('#speech-status');
  const overviewText = [
    'Atlas Pineal, internette sizin hakkınızda ne göründüğünü anlamanıza yardımcı olur.',
    'Önce yalnızca sizin veya yetki verdiğiniz kurumun kapsamı belirlenir. Sonra kaynağa bakılır, bilgi doğrulanır ve gerekiyorsa resmî düzeltme yolu seçilir.',
    'Hiçbir kaldırma sonucu garanti edilmez. Özel kişiler hakkında izinsiz araştırma yapılmaz; aileden, komşudan veya köyden dedikodu toplanmaz.',
    'Bu web sayfası tarama başlatmaz ve kişisel bilgi toplamaz. Karar ve onay her zaman sizde kalır.'
  ].join(' ');

  if (listenButton && listenLabel && speechStatus) {
    listenButton.addEventListener('click', () => {
      if (!('speechSynthesis' in window) || !('SpeechSynthesisUtterance' in window)) {
        speechStatus.textContent = 'Bu tarayıcı sesli okumayı desteklemiyor. Özet metin sayfada görünür.';
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
        listenButton.setAttribute('aria-label', 'Sesli özeti durdur');
        listenLabel.textContent = 'Dinlemeyi durdur';
        speechStatus.textContent = 'Kısa özet okunuyor.';
      };
      utterance.onend = () => {
        resetListenButton();
        speechStatus.textContent = 'Sesli özet tamamlandı.';
      };
      utterance.onerror = () => {
        resetListenButton();
        speechStatus.textContent = 'Ses bu cihazda başlatılamadı. Metni okuyabilir veya cihazınızın erişilebilirlik ayarlarını kullanabilirsiniz.';
      };

      listenButton.setAttribute('aria-pressed', 'true');
      listenButton.setAttribute('aria-label', 'Sesli özeti durdur');
      listenLabel.textContent = 'Dinleme başlatılıyor';
      synthesis.speak(utterance);
    });
  }

  function resetListenButton() {
    if (!listenButton || !listenLabel) return;
    listenButton.setAttribute('aria-pressed', 'false');
    listenButton.setAttribute('aria-label', 'Atlas Pineal kısa özetini dinle');
    listenLabel.textContent = 'Kısa özeti dinle';
  }
})();
