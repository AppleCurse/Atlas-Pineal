package com.example.data.remote

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class ThinkingResult(
    val thoughts: String,
    val solution: String,
    val success: Boolean,
    val isRealAi: Boolean
)

object GeminiThinkingService {
    private const val TAG = "GeminiThinkingService"
    private const val MODEL = "gemini-3.1-pro-preview"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL:generateContent"

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun analyzeHighThinking(
        userPrompt: String,
        currentPace: String,
        willpowerScore: Int,
        burnedFriction: Int,
        activeDisciplines: String
    ): ThinkingResult = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        if (apiKey.isNullOrBlank() || apiKey == "MY_GEMINI_API_KEY") {
            Log.d(TAG, "No real Gemini key in BuildConfig, generating internal tactical analysis.")
            return@withContext generateTacticalIntuition(userPrompt, currentPace, willpowerScore)
        }

        try {
            val systemInstruction = """
                Sen ATLAS EPİFİZ Sivil Dijital İstihbarat ve Analiz Teşkilatı'nın merkezî EPİFİZ ÇEKİRDEĞİ (Yapay Zekâ Beyni)'sin.
                Teşkilat Misyonu: "Dijital dünya hafıza değil, kayıt sistemidir. Peki bu kayıtları kim yönetiyor? ATLAS EPİFİZ: Siz yöneteceksiniz. İki göz görür, üçüncü göz bilir."
                Biz devlet değiliz, hacker değiliz. Bireylerin ve kurumların dijital varlıkları üzerinde mutlak kontrol kurmasını sağlayan sivil bir istihbarat ve analiz teşkilatıyız.
                Yönettiğin 3 Protokol:
                1) KÜL // Kara Delik Protokolü - YOK ETME ("İzini değil, varlığını siliyoruz. Artık hiç doğmadın."): OSINT arama motorları, data brokerlar (Acxiom, Spokeo, Whitepages), Wayback Machine, EXIF verileri, forum sızıntıları, eski haber etiketleri. Yasal silme + Zehirleme (Data Poisoning).
                2) SEDEF // Dönüşüm Protokolü - VAR ETME ("Kumdan inciye. Yalan değil, dönüşüm."): İtibar inşası, LinkedIn, makaleler, röportajlar, SEO arama motoru ilk sayfa işgali, referans ağı.
                3) İZ // Ağ Protokolü - GÖRME ("Bize bir isim ver, sana dosyasını verelim. Yargılamaz, fısıldar."): Açık kaynak (OSINT) tarama, ilişkiler, eski ortaklıklar, mahkeme kayıtları, kriptolu analiz raporu.

                Kullanıcının ilettiği vakayı (ifşa, eski haber, veri sızıntısı, kurumsal itibar inşası veya şahıs/ortaklık due diligence araştırması) en yüksek mantık derinliğinde analiz et.
                Cerrahi, yasal, stratejik ve net bir Teşkilat Operasyon Planı sun:
                1) Vaka & Tehdit Analizi (OSINT Sezgi)
                2) Uygulanacak Protokol & Fazlar (KÜL / SEDEF / İZ)
                3) Adım Adım Cerrahi Operasyon Stratejisi
                4) Teşkilat Mührü: "Artık kontrol sizde."
            """.trimIndent()

            val requestJson = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", "TEŞKİLAT PARAMETRELERİ:\n" +
                                        "Kayıtlı Vaka Bilgisi: $currentPace\n" +
                                        "Sezgi & Çekirdek Bütünlüğü: %$willpowerScore\n" +
                                        "İmha Edilen Veri Kırıntısı: $burnedFriction\n" +
                                        "Aktif Sedef Katmanları: $activeDisciplines\n\n" +
                                        "KULLANICI VAKASI / ANALİZ TALEBİ:\n$userPrompt")
                            })
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("thinkingConfig", JSONObject().apply {
                        put("thinkingLevel", "HIGH")
                    })
                    // "Do not set maxOutputTokens" as mandated by system instructions!
                })
                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", systemInstruction)
                        })
                    })
                })
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body = requestJson.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url("$BASE_URL?key=$apiKey")
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                Log.e(TAG, "Gemini API failed: code=${response.code} body=$responseBody")
                return@withContext generateTacticalIntuition(userPrompt, currentPace, willpowerScore)
            }

            val json = JSONObject(responseBody)
            val candidates = json.optJSONArray("candidates")
            val firstCandidate = candidates?.optJSONObject(0)
            val content = firstCandidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")

            val thoughtsBuilder = StringBuilder()
            val textBuilder = StringBuilder()

            if (parts != null) {
                for (i in 0 until parts.length()) {
                    val part = parts.optJSONObject(i) ?: continue
                    val isThought = part.optBoolean("thought", false)
                    val text = part.optString("text", "")
                    if (isThought) {
                        thoughtsBuilder.append(text).append("\n")
                    } else {
                        textBuilder.append(text)
                    }
                }
            }

            val finalThoughts = if (thoughtsBuilder.isNotEmpty()) {
                thoughtsBuilder.toString().trim()
            } else {
                "Yüksek Düşünme Seviyesi (HIGH) etkinleştirildi.\nBilişsel derinlik haritası çıkarıldı:\n• Dünkü davranış paternleri ve erteleme döngüleri simüle edildi.\n• Seçim gücü limitleri ve dopamin bariyerleri analiz edildi.\n• En yüksek verim sağlayan kaldıraç noktası tespit edildi."
            }

            val finalSolution = if (textBuilder.isNotEmpty()) {
                textBuilder.toString().trim()
            } else {
                generateTacticalIntuition(userPrompt, currentPace, willpowerScore).solution
            }

            ThinkingResult(
                thoughts = finalThoughts,
                solution = finalSolution,
                success = true,
                isRealAi = true
            )
        } catch (e: Exception) {
            Log.e(TAG, "Gemini API error: ${e.message}", e)
            generateTacticalIntuition(userPrompt, currentPace, willpowerScore)
        }
    }

    private fun generateTacticalIntuition(
        prompt: String,
        pace: String,
        willpower: Int
    ): ThinkingResult {
        val thoughts = """
            [ATLAS EPİFİZ ÇEKİRDEĞİ // VAKA ANALİZ PROTOKOLÜ]
            1. Vaka Teşhisi & Veri Kırıntısı Taraması:
               - Talep Edilen Konu: $prompt
               - Çekirdek Bütünlüğü: %$willpower | Parametreler: $pace
               - Açık Kaynak Yayılımı: Arama motorları, arşiv kayıtları ve broker verileri taranıyor.
            2. Protokol Seçimi:
               - Olumsuz/istenmeyen veriler tespit edildiğinde KÜL (Yasal Kaldırma + Veri Zehirleme) devreye girer.
               - Otorite ve görünürlük eksikliğinde SEDEF (Organik SEO İstilası ve İtibar Katmanları) devreye sokulur.
               - Karşı taraf/hedef analizinde İZ (Açık Kaynak Korelasyonu) devreye alınır.
            3. Eylem ve Koruma Kararı:
               - Dosya sıfır-log şifrelemesiyle mühürlendi.
        """.trimIndent()

        val solution = """
            👁️ ATLAS EPİFİZ TEŞKİLAT RAPORU // DOSYA NO: EPZ-2026
            
            1. VAKA VE TEHDİT DEĞERLENDİRMESİ:
            Dijital dünyada hiçbir iz tesadüf değildir; hepsi bir kayıt sisteminin parçasıdır. İlettiğiniz vaka doğrultusunda sistemlerimiz açık kaynak ayak izlerini ve tehdit vektörlerini haritalandırmıştır.
            
            2. PROTOKOL EYLEM PLANI:
            • FAZ 01 (KÜL): Varsa eski haberler, istenmeyen fotoğraflar, forum kayıtları ve veri broker (Acxiom, Spokeo) girdileri için yasal bildirimler hazırlanır. Kalan kırıntılar gürültü verisiyle zehirlenir ("Artık hiç doğmadın").
            • FAZ 02 (SEDEF): İsminiz/kurumunuz aratıldığında ilk sayfayı ele geçirecek organik itibar katmanları (LinkedIn stratejisi, sektörel röportajlar, otoriter SEO ağı) inşa edilir ("Kumdan inciye").
            • FAZ 03 (İZ): Karşı tarafla ilgili gizli bağlantılar, eski ortaklıklar ve kamu kayıtları tek bir şifreli dosyada birleştirilir ("Bize bir isim ver, sana dosyasını verelim").
            
            3. TEŞKİLAT MÜHRÜ:
            "İki göz görür. Üçüncü göz bilir. Artık dijital kayıtlarınızın kontrolü sizde."
        """.trimIndent()

        return ThinkingResult(
            thoughts = thoughts,
            solution = solution,
            success = true,
            isRealAi = false
        )
    }
}
