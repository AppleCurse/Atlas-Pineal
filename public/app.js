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
    target: {
      title: 'Hedef ve Hesap İstihbaratı Kapsamı.',
      copy: 'Açık kaynaklardaki tüm profiller 12 saha ajanı ve 7 dalga motoruyla taranır. Zafiyetler, hassasiyetler ve karşı tarafın savunmasını düşüren ilk temas stratejisi hazırlanır.',
      mark: '⌖'
    },
    purge: {
      title: 'Dijital Kül: İzi Silme & Unutulma Kapsamı.',
      copy: 'Geçmişte kalmış hatalı, yanıltıcı veya istenmeyen verilerinizin arama motorları ve içerik sağlayıcılar üzerinden yasal ve teknik yollarla kökünden kaldırılması sürecidir.',
      mark: '🔥'
    },
    rebuild: {
      title: 'Dijital Sedef: Yeni Kişilik & İtibar İnşası Kapsamı.',
      copy: 'Geçmişi arkada bırakın. İnternette hakkınızda yalnızca saygın, güçlü ve hedefinize uygun profesyonel kayıtların çıkacağı sarsılmaz bir itibar kalkanı örülür.',
      mark: '🐚'
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

      scopeTitle.textContent = response.title;
      scopeDescription.textContent = response.copy;
      scopeMark.textContent = response.mark;
    });
  });

  const listenButton = document.querySelector('#listen-overview');
  const listenLabel = document.querySelector('#listen-label');
  const speechStatus = document.querySelector('#speech-status');
  const overviewText = [
    'Atlas Pineal. Dijital dünya bir hafıza değil, kayıt sistemidir. O kayıtlara her gün tıklanıyor.',
    'Atlas Pineal der ki: Kayıtları siz yöneteceksiniz. Merkezde hiç kapanmayan Epifiz Gözü var.',
    'Bir hesabı veya şahsı araştırmak, kiminle karşı karşıya olduğunuzu kanıtıyla bilmek, internetteki bütün varlığınızı silmek ya da sıfırdan yeni ve lekesiz bir kişilik inşa etmek isteyenler için özel hizmet sunuyoruz.',
    'Arka planda on iki saha ajanı ve yedi dalga motoruyla çalışan Pineal Epifiz çekirdeği bulunur. Her iddia kanıtla mühürlenir.'
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
        speechStatus.textContent = 'Manifesto okunuyor.';
      };
      utterance.onend = () => {
        resetListenButton();
        speechStatus.textContent = 'Sesli özet tamamlandı.';
      };
      utterance.onerror = () => {
        resetListenButton();
        speechStatus.textContent = 'Ses bu cihazda başlatılamadı.';
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
    listenButton.setAttribute('aria-label', 'Manifestoyu dinle');
    listenLabel.textContent = 'Manifestoyu Dinle';
  }
})();
