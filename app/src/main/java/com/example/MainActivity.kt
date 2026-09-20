package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.ConsoleScreen
import com.example.ui.screens.EpifizOracleScreen
import com.example.ui.screens.IzProtocolScreen
import com.example.ui.screens.KulProtocolScreen
import com.example.ui.screens.ManifestoScreen
import com.example.ui.screens.SedefProtocolScreen
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.AshRed
import com.example.ui.theme.BrassGold
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PhosphorGreen
import com.example.ui.viewmodel.AtlasTab
import com.example.ui.viewmodel.AtlasViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: AtlasViewModel = viewModel()
                val currentTab by viewModel.currentTab.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFF040608),
                    bottomBar = {
                        AtlasBottomNavigation(
                            currentTab = currentTab,
                            onTabSelected = { viewModel.selectTab(it) }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Crossfade(
                            targetState = currentTab,
                            label = "tab_crossfade"
                        ) { tab ->
                            when (tab) {
                                AtlasTab.KONSOL -> ConsoleScreen(viewModel = viewModel)
                                AtlasTab.MANIFESTO -> ManifestoScreen(viewModel = viewModel)
                                AtlasTab.KUL -> KulProtocolScreen(viewModel = viewModel)
                                AtlasTab.SEDEF -> SedefProtocolScreen(viewModel = viewModel)
                                AtlasTab.IZ -> IzProtocolScreen(viewModel = viewModel)
                                AtlasTab.KAHIN -> EpifizOracleScreen(viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AtlasBottomNavigation(
    currentTab: AtlasTab,
    onTabSelected: (AtlasTab) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF090D14))
            .border(1.dp, Color(0xFF38291F), RoundedCornerShape(12.dp))
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier
                .height(60.dp)
                .testTag("atlas_bottom_navigation")
        ) {
            NavigationBarItem(
                selected = currentTab == AtlasTab.KONSOL,
                onClick = { onTabSelected(AtlasTab.KONSOL) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Dashboard,
                        contentDescription = "Konsol",
                        modifier = Modifier.size(18.dp)
                    )
                },
                label = {
                    Text(
                        text = "KONSOL",
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                },
                colors = navigationColors(BrassGold),
                modifier = Modifier.testTag("nav_tab_konsol")
            )

            NavigationBarItem(
                selected = currentTab == AtlasTab.MANIFESTO,
                onClick = { onTabSelected(AtlasTab.MANIFESTO) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = "Doktrin",
                        modifier = Modifier.size(18.dp)
                    )
                },
                label = {
                    Text(
                        text = "DOKTRİN",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                },
                colors = navigationColors(AntiqueGold),
                modifier = Modifier.testTag("nav_tab_manifesto")
            )

            NavigationBarItem(
                selected = currentTab == AtlasTab.KUL,
                onClick = { onTabSelected(AtlasTab.KUL) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.DeleteSweep,
                        contentDescription = "Kül",
                        modifier = Modifier.size(18.dp)
                    )
                },
                label = {
                    Text(
                        text = "KÜL",
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                },
                colors = navigationColors(AshRed),
                modifier = Modifier.testTag("nav_tab_kul")
            )

            NavigationBarItem(
                selected = currentTab == AtlasTab.SEDEF,
                onClick = { onTabSelected(AtlasTab.SEDEF) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Sedef",
                        modifier = Modifier.size(18.dp)
                    )
                },
                label = {
                    Text(
                        text = "SEDEF",
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                },
                colors = navigationColors(AntiqueGold),
                modifier = Modifier.testTag("nav_tab_sedef")
            )

            NavigationBarItem(
                selected = currentTab == AtlasTab.IZ,
                onClick = { onTabSelected(AtlasTab.IZ) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "İz",
                        modifier = Modifier.size(18.dp)
                    )
                },
                label = {
                    Text(
                        text = "İZ",
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                },
                colors = navigationColors(PhosphorGreen),
                modifier = Modifier.testTag("nav_tab_iz")
            )

            NavigationBarItem(
                selected = currentTab == AtlasTab.KAHIN,
                onClick = { onTabSelected(AtlasTab.KAHIN) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.RemoveRedEye,
                        contentDescription = "Kâhin",
                        modifier = Modifier.size(18.dp)
                    )
                },
                label = {
                    Text(
                        text = "KÂHİN",
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                },
                colors = navigationColors(Color(0xFF60A5FA)),
                modifier = Modifier.testTag("nav_tab_kahin")
            )
        }
    }
}

@Composable
private fun navigationColors(activeColor: Color) = NavigationBarItemDefaults.colors(
    selectedIconColor = activeColor,
    selectedTextColor = activeColor,
    unselectedIconColor = Color(0xFF64748B),
    unselectedTextColor = Color(0xFF64748B),
    indicatorColor = Color(0xFF1E293B)
)
