# Atlas Pineal

**Ürün ilkesi:** Yetkili olunan dijital varlıklar için kanıta dayalı maruziyet incelemesi ve düzeltme süreçleri. Atlas Pineal, “gizli dünyayı bilen” ya da her şeyi silebilen bir teşkilat değildir. Gücü; kapsamı tanımlamak, kaynağı doğrulamak, belirsizliği göstermek ve haricî adımları yetkiye bağlamaktır.

## Ürün sınırları

- İnceleme yalnızca varlık sahibinin veya açık yetkiyle temsil edilen kurumun kapsamındadır.
- OSINT, yasal ve herkese açık kaynakların dikkatle incelenmesidir; sızıntı verisi, özel hesap, izinsiz erişim ya da izinsiz kişi dosyası değildir.
- Her bulgu kaynak, erişim zamanı, bağlam ve belirsizlik bilgisiyle taşınmalıdır. İsim benzerliği tek başına eşleşme sayılmaz.
- KÜL, dış içeriği sihirli biçimde “yok etmez”; uygun olduğunda resmî düzeltme/kaldırma kanalına başvurmayı anlatır.
- SEDEF, sahte kimlik veya arama manipülasyonu değil; kişinin/kurumun kontrol ettiği kanallarda doğru bilgi yayımlamaktır.
- Talep gönderilmiş olması, içeriğin kaldırıldığı anlamına gelmez. Sonuç kaynağında doğrulanmalıdır.
- Şifreleme, sıfır-log ve başarı oranı gibi güvenlik iddiaları yalnızca gerçek uygulama ve denetim kanıtıyla kullanılmalıdır.

## Bu checkout'taki katmanlar

Bu `Atlas-Pineal` deposu, **kamuya dönük anlatım yüzeyini** ve eski bir Android denemesini içerir. Buradaki web sunucusu yalnızca statik sayfaları sunar: tarama başlatmaz, form verisi toplamaz ve operasyon motoruna çağrı yapmaz.

İşleyen **Pineal Epifiz operasyon motoru ayrı bir repo/çalışma katmanındadır**; bu checkout'a gömülü değildir. Motorun iç mimarisi, yetenekleri ve testleri kendi kod tabanında değerlendirilir. Bu web sunucusu motoru çağırmaz ve motorun yerini tutmaz.

### Web yüzeyini çalıştırma

```sh
node server.mjs
```

Ardından `http://localhost:3000` adresini açın. Frontend dış font, analitik veya tarama servisi çağırmaz. Sunucu `public/` dosyalarını `GET`/`HEAD` ile sunar; form, telemetri veya video-yükleme API'si yoktur. Güvenlik başlıkları, Content Security Policy, güvenli dosya yolu kontrolü ve medya için byte-range desteği uygulanır.

## Pineal Core / Pineal Heretic

Pineal Core, ayrı motor katmanındaki yeteneklerin ortak omurga ve erişim kapılarıyla çalışmasını ifade eder. Dil tespiti gibi deterministik ölçümlerde sinyal ve güven düzeyi; çeviri gibi işlerde ise yerel motor koşulu açıkça belirtilmelidir. Belirsiz sonuç etiket uydurarak kapatılmamalıdır.

Pineal Heretic, güçlü iddiaları karşı kanıtla sınama ve yanlış eşleşmeleri arama disiplinini temsil eder. Bu ad, doğaüstü bir yetenek iddiası değildir. Heretic yaklaşımının operasyonel olarak etkin olduğu ancak ilgili motor kodu ve testleriyle ayrıca doğrulanabilir.

## Pilot ve yayına çıkış öncesi kanıtlar

Tasarım tek başına güven oluşturmaz. Hizmet kapsamı ve yetki akışı, tüzel kişi ve erişilebilir iletişim, KVKK/GDPR bilgilendirmesi, veri saklama/silme süreleri, kullanılan kaynakların izin/kullanım koşulları, kaynak ve tarih içeren örnek raporlar, güvenlik mimarisi ve bağımsız test sonuçları hazırlanmalıdır. Bunlar doğrulanmadan “üst düzey istihbarat”, “şifreli/sıfır log” veya başarı garantisi yayımlanmamalıdır.

## Bu depodaki Android denemesi

`app/` altındaki Jetpack Compose uygulaması, ayrı Pineal Epifiz operasyon motoruyla karıştırılmamalıdır. Mevcut Android kaynaklarında eski veri-zehirleme/üçüncü-kişi araştırması metinleri, doğrulanmamış AES/sıfır-log ifadeleri ve istemciden doğrudan Gemini çağrısı gibi yayımlama öncesi yeniden ele alınması gereken izler bulunur. Bu Android denemesi güvenlik/gizlilik incelemesinden geçirilmeden yayımlanmamalıdır; üretim API anahtarı APK içine konmamalıdır.

`.env.example` içindeki anahtar yalnızca Android denemesi için yer tutucudur. Gerçek kullanım, açık bilgilendirme/onay, veri saklama politikası ve güvenli sunucu tarafı anahtar yönetimi gerektirir.
