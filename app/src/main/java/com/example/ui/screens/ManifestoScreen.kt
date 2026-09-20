package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.AshRed
import com.example.ui.theme.BrassGold
import com.example.ui.theme.PhosphorGreen
import com.example.ui.viewmodel.AtlasTab
import com.example.ui.viewmodel.AtlasViewModel

@Composable
fun ManifestoScreen(
    viewModel: AtlasViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var selectedSection by remember { mutableStateOf("TÜMÜ") }
    var expandedAudienceItem by remember { mutableStateOf<String?>("kul_01") }

    // Application Form State
    var aliasInput by remember { mutableStateOf("") }
    var serviceSelection by remember { mutableStateOf("KÜL // Kara Delik Protokolü") }
    var caseDetailInput by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF040608))
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TOP HEADER BAR
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.selectTab(AtlasTab.KONSOL) },
                modifier = Modifier.testTag("manifesto_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Geri",
                    tint = AntiqueGold
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Text(
                    text = "ATLAS EPİFİZ // TEŞKİLAT MANİFESTOSU",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Sivil Dijital İstihbarat ve Analiz Teşkilatı",
                    color = BrassGold,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // CORE MANIFESTO HERO CARD
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1E1710),
                            Color(0xFF120E0A),
                            Color(0xFF080604)
                        )
                    )
                )
                .border(1.5.dp, Color(0xFFC9B896), RoundedCornerShape(12.dp))
                .padding(18.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "AMACIMIZ NEDİR?",
                    color = AntiqueGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Dijital dünya hafıza değildir.\nDijital dünya bir kayıt sistemidir.",
                    color = Color(0xFFF8FAFC),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Her tıklama, her yorum, her fotoğraf, her işlem kaydedilir, sınıflandırılır ve satılır.\n\nPeki bu kayıtları kim yönetiyor?\nATLAS EPİFİZ diyor ki: SİZ YÖNETECEKSİNİZ.",
                    color = Color(0xFFCBD5E1),
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF000000).copy(alpha = 0.6f))
                        .border(1.dp, Color(0xFF38291F), RoundedCornerShape(6.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "“Biz devlet değiliz. Biz hacker değiliz. Bireylerin ve kurumların dijital varlıkları üzerinde mutlak kontrol kurmasını sağlayan sivil bir istihbarat ve analiz teşkilatıyız.”",
                        color = Color(0xFFE2E8F0),
                        fontSize = 11.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        lineHeight = 16.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "“İki göz görür. Üçüncü göz bilir.”",
                    color = AntiqueGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // SECTION: BU SİSTEM KİM İÇİN KURULDU?
        Text(
            text = "BU SİSTEM KİM İÇİN KURULDU?",
            color = BrassGold,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 1.5.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Filter Pills
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterPill(
                label = "TÜMÜ",
                isSelected = selectedSection == "TÜMÜ",
                onClick = { selectedSection = "TÜMÜ" }
            )
            FilterPill(
                label = "🖤 KÜL",
                isSelected = selectedSection == "KÜL",
                color = AshRed,
                onClick = { selectedSection = "KÜL" }
            )
            FilterPill(
                label = "🤍 SEDEF",
                isSelected = selectedSection == "SEDEF",
                color = BrassGold,
                onClick = { selectedSection = "SEDEF" }
            )
            FilterPill(
                label = "🩶 İZ",
                isSelected = selectedSection == "İZ",
                color = PhosphorGreen,
                onClick = { selectedSection = "İZ" }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // AUDIENCE TARGET CARDS
        if (selectedSection == "TÜMÜ" || selectedSection == "KÜL") {
            AudienceCategoryHeader("🖤 KÜL — KARA DELİK PROTOKOLÜ (YOK ETME)", AshRed)
            
            AudienceAccordionItem(
                id = "kul_01",
                tag = "01",
                title = "İFŞA MAĞDURU",
                summary = "Birisi seni internete döktü. Fotoğrafın, videon, ismin orada. 'İçerik politikamıza aykırı değil' dediler.",
                solution = "Ağladın, bekledin, unuttular mı diye umut ettin. Unutmadılar. KÜL unutturur. OSINT ajanları arama motorlarını, data brokerları ve arşivleri tarar, yasal kaldırma ve zehirleme (data poisoning) ile olay örgüsünden koparır: 'Artık hiç doğmadın.'",
                accentColor = AshRed,
                isExpanded = expandedAudienceItem == "kul_01",
                onToggle = { expandedAudienceItem = if (expandedAudienceItem == "kul_01") null else "kul_01" }
            )

            AudienceAccordionItem(
                id = "kul_02",
                tag = "02",
                title = "GEÇMİŞİNDEN KAÇAN",
                summary = "On yıl önceki forum yorumu, beş yıl önceki fotoğraf, üç yıl önceki haber. Sen değiştin, internet değişmedi.",
                solution = "Her iş başvurusunda HR seni Google'lıyor. Her yeni tanışmada o geçmiş çıkıyor ve bugünü yiyor. KÜL geçmişi siler; bugün sıfırdan başlar.",
                accentColor = AshRed,
                isExpanded = expandedAudienceItem == "kul_02",
                onToggle = { expandedAudienceItem = if (expandedAudienceItem == "kul_02") null else "kul_02" }
            )

            AudienceAccordionItem(
                id = "kul_03",
                tag = "03",
                title = "ÜST DÜZEY YÖNETİCİ / KAMUSAL İSİM",
                summary = "Adın çok yerde çıkıyor. Rakipler, gazeteciler, avukatlar seni izliyor. Dijital varlığın sana doğrultulmuş bir silah oldu.",
                solution = "Adresin, aile fertlerin, eski açıklamaların. KÜL sizi görünmez yapar. Yalnızca görmelerini istediğiniz stratejik katmanları bırakır.",
                accentColor = AshRed,
                isExpanded = expandedAudienceItem == "kul_03",
                onToggle = { expandedAudienceItem = if (expandedAudienceItem == "kul_03") null else "kul_03" }
            )
        }

        if (selectedSection == "TÜMÜ" || selectedSection == "SEDEF") {
            Spacer(modifier = Modifier.height(10.dp))
            AudienceCategoryHeader("🤍 SEDEF — DÖNÜŞÜM PROTOKOLÜ (VAR ETME)", AntiqueGold)

            AudienceAccordionItem(
                id = "sedef_01",
                tag = "01",
                title = "GİRİŞİMCİ / KURUCU",
                summary = "Harika bir ürünün, vizyonun var. Ama yatırımcı seni Google'lıyor ve hiçbir şey çıkmıyor. Hiçbir şey çıkmaması da bir şey söyler.",
                solution = "Güven, görünürlükten geçer. İtibar, hikayeden geçer. SEDEF seni inşa eder: Yatırımcı seni aradığında görmek istediği vizyoner lideri görür.",
                accentColor = AntiqueGold,
                isExpanded = expandedAudienceItem == "sedef_01",
                onToggle = { expandedAudienceItem = if (expandedAudienceItem == "sedef_01") null else "sedef_01" }
            )

            AudienceAccordionItem(
                id = "sedef_02",
                tag = "02",
                title = "İTİBAR KRİZİ YAŞAYAN",
                summary = "Bir haber çıktı. Doğru ya da yanlış. Google'da adının yanında o haber duruyor. Müşteriler, ortaklar, ailen görüyor.",
                solution = "Bir hata tüm hayatı tanımlamak zorunda değil. SEDEF o haberin üzerine katman katman organik ve saygın gerçeklik yazar; ta ki o haber 5. sayfaya düşüp yok olana kadar.",
                accentColor = AntiqueGold,
                isExpanded = expandedAudienceItem == "sedef_02",
                onToggle = { expandedAudienceItem = if (expandedAudienceItem == "sedef_02") null else "sedef_02" }
            )

            AudienceAccordionItem(
                id = "sedef_03",
                tag = "03",
                title = "UZMAN / DANIŞMAN / DOKTOR / AVUKAT",
                summary = "Yıllarca birikmiş binlerce saatlik uzmanlık. Ama dijitalde sessizsin. Rakibin senden iyi değil, sadece daha görünür.",
                solution = "SEDEF uzmanlığını organik yayınlar, röportajlar ve prestijli referanslarla parlatır. Hakkında arama yapan doğrudan otoriteni görür.",
                accentColor = AntiqueGold,
                isExpanded = expandedAudienceItem == "sedef_03",
                onToggle = { expandedAudienceItem = if (expandedAudienceItem == "sedef_03") null else "sedef_03" }
            )
        }

        if (selectedSection == "TÜMÜ" || selectedSection == "İZ") {
            Spacer(modifier = Modifier.height(10.dp))
            AudienceCategoryHeader("🩶 İZ — AĞ PROTOKOLÜ (GÖRME / OSINT)", PhosphorGreen)

            AudienceAccordionItem(
                id = "iz_01",
                tag = "01",
                title = "YATIRIMCI / DUE DILIGENCE",
                summary = "Masada biri oturuyor. İyi konuşuyor, para istiyor. Neredeyse imzaladın ama içine bir şüphe düştü.",
                solution = "İZ o şeyi bulur: Eski batık ortaklıklar, mahkeme kayıtları, arka plandaki bağlar. İmzalamadan ve kaybetmeden önce masana şifreli dosyayı koyar.",
                accentColor = PhosphorGreen,
                isExpanded = expandedAudienceItem == "iz_01",
                onToggle = { expandedAudienceItem = if (expandedAudienceItem == "iz_01") null else "iz_01" }
            )

            AudienceAccordionItem(
                id = "iz_02",
                tag = "02",
                title = "İŞE ALIM YÖNETİCİSİ / İK",
                summary = "Mükemmel bir CV geldi. Her şey nizami. Ama kurumsal hafıza eksik: Bu kişi gerçekte kim?",
                solution = "LinkedIn'de yazılmayanlar, önceki ayrılığın gerçek sebebi, açık kaynaklardaki ayak izleri. İşe almadan ve pişman olmadan önce net dosya.",
                accentColor = PhosphorGreen,
                isExpanded = expandedAudienceItem == "iz_02",
                onToggle = { expandedAudienceItem = if (expandedAudienceItem == "iz_02") null else "iz_02" }
            )

            AudienceAccordionItem(
                id = "iz_03",
                tag = "03",
                title = "KENDİNİ ARAŞTIRAN / GİZLİLİK SAHİBİ",
                summary = "İnternet hakkımda ne biliyor? Data brokerlar ne saklıyor? Kim beni nasıl bulabilir?",
                solution = "Açık kaynaklardaki tüm sızıntılarını, unutulmuş hesaplarını ve bağlantılarını başkaları görmeden önce tek raporda sana teslim eder.",
                accentColor = PhosphorGreen,
                isExpanded = expandedAudienceItem == "iz_03",
                onToggle = { expandedAudienceItem = if (expandedAudienceItem == "iz_03") null else "iz_03" }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ENCRYPTED APPLICATION FORM (DAVETE ÖZEL PROTOKOL TALEBİ)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("application_form_card"),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF090D14)),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF38291F)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Security",
                        tint = AntiqueGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ŞİFRELİ PROTOKOL BAŞVURUSU",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                }

                Text(
                    text = "Sıfır-Log ve AES-256 kriptolama ile doğrudan Teşkilat Masasına iletilir.",
                    color = Color(0xFF94A3B8),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )

                if (isSubmitted) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF064E3B).copy(alpha = 0.5f))
                            .border(1.dp, PhosphorGreen, RoundedCornerShape(8.dp))
                            .padding(14.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Success",
                                    tint = PhosphorGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "BAŞVURU MÜHÜRLENDİ",
                                    color = PhosphorGreen,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Text(
                                text = "Dosyanız Epifiz Çekirdeği tarafından şifreli kabul edildi. Protokol incelemesi başlatıldı. Kimliğiniz sistemde loglanmaz.",
                                color = Color(0xFFD1FAE5),
                                fontSize = 10.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                } else {
                    OutlinedTextField(
                        value = aliasInput,
                        onValueChange = { aliasInput = it },
                        label = { Text("KOD ADI VEYA KURUMSAL RUMUZ", fontSize = 9.sp, color = Color(0xFF94A3B8)) },
                        placeholder = { Text("Örn: Atlas-713 veya Anonim", color = Color(0xFF475569), fontSize = 11.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("applicant_alias_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = AntiqueGold,
                            unfocusedBorderColor = Color(0xFF334155),
                            cursorColor = AntiqueGold
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "TALEP EDİLEN PROTOKOL:",
                        color = Color(0xFF94A3B8),
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf("KÜL", "SEDEF", "İZ", "HEPSİ").forEach { service ->
                            val isSelected = serviceSelection.startsWith(service)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isSelected) Color(0xFF2E2116) else Color(0xFF0F172A))
                                    .border(1.dp, if (isSelected) AntiqueGold else Color(0xFF1E293B), RoundedCornerShape(4.dp))
                                    .clickable { serviceSelection = service }
                                    .padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = service,
                                    color = if (isSelected) AntiqueGold else Color(0xFF94A3B8),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = caseDetailInput,
                        onValueChange = { caseDetailInput = it },
                        label = { Text("DURUM / KRİZ / HEDEF ÖZETİ", fontSize = 9.sp, color = Color(0xFF94A3B8)) },
                        placeholder = { Text("Örn: Google'da 3. sayfadaki haberin kaldırılması veya ortaklık öncesi kişi analizi...", color = Color(0xFF475569), fontSize = 11.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .testTag("applicant_case_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = AntiqueGold,
                            unfocusedBorderColor = Color(0xFF334155),
                            cursorColor = AntiqueGold
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (caseDetailInput.isNotBlank()) {
                                isSubmitted = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E2116)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AntiqueGold),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("submit_application_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = AntiqueGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "KRİPTOLA VE TEŞKİLAT MASASINA İLET",
                            color = AntiqueGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun FilterPill(
    label: String,
    isSelected: Boolean,
    color: Color = AntiqueGold,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) color.copy(alpha = 0.25f) else Color(0xFF0F172A))
            .border(1.dp, if (isSelected) color else Color(0xFF1E293B), RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) color else Color(0xFF94A3B8),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
private fun AudienceCategoryHeader(title: String, color: Color) {
    Text(
        text = title,
        color = color,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        letterSpacing = 1.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

@Composable
private fun AudienceAccordionItem(
    id: String,
    tag: String,
    title: String,
    summary: String,
    solution: String,
    accentColor: Color,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onToggle() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF090D14)),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isExpanded) accentColor else Color(0xFF1E293B)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Text(
                        text = tag,
                        color = accentColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Toggle",
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = summary,
                color = Color(0xFF94A3B8),
                fontSize = 10.5.sp,
                lineHeight = 15.sp
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF000000).copy(alpha = 0.5f))
                        .border(1.dp, accentColor.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "TEŞKİLAT ÇÖZÜMÜ:",
                        color = accentColor,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = solution,
                        color = Color(0xFFF1F5F9),
                        fontSize = 10.5.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
