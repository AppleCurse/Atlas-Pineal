package com.example

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.ui.viewmodel.AtlasTab
import com.example.ui.viewmodel.AtlasViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class AtlasViewModelTest {

    private lateinit var viewModel: AtlasViewModel

    @Before
    fun setup() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        viewModel = AtlasViewModel(app)
    }

    @Test
    fun verifyInitialConsoleState() {
        assertNotNull(viewModel.status.value)
        assertEquals(AtlasTab.KONSOL, viewModel.currentTab.value)
        assertTrue(viewModel.status.value.awarenessLevel >= 50f)
        assertTrue(viewModel.status.value.coreIntegrity >= 50f)
        assertTrue(viewModel.status.value.zeroLogProtection)
    }

    @Test
    fun testTabSelection() {
        viewModel.selectTab(AtlasTab.MANIFESTO)
        assertEquals(AtlasTab.MANIFESTO, viewModel.currentTab.value)

        viewModel.selectTab(AtlasTab.KUL)
        assertEquals(AtlasTab.KUL, viewModel.currentTab.value)

        viewModel.selectTab(AtlasTab.SEDEF)
        assertEquals(AtlasTab.SEDEF, viewModel.currentTab.value)

        viewModel.selectTab(AtlasTab.IZ)
        assertEquals(AtlasTab.IZ, viewModel.currentTab.value)

        viewModel.selectTab(AtlasTab.KAHIN)
        assertEquals(AtlasTab.KAHIN, viewModel.currentTab.value)
    }

    @Test
    fun testZeroLogAndTimerToggles() {
        val initialProtection = viewModel.status.value.zeroLogProtection
        viewModel.toggleZeroLogProtection()
        assertEquals(!initialProtection, viewModel.status.value.zeroLogProtection)

        val initialTimer = viewModel.status.value.isTimerActive
        viewModel.toggleFocusTimer()
        assertEquals(!initialTimer, viewModel.status.value.isTimerActive)
    }

    @Test
    fun testKnobs() {
        viewModel.setKulIntensity(0.9f)
        assertEquals(0.9f, viewModel.status.value.kulIntensity, 0.01f)

        viewModel.setSedefTuning(0.75f)
        assertEquals(0.75f, viewModel.status.value.sedefTuning, 0.01f)

        viewModel.setIzFocus(0.85f)
        assertEquals(0.85f, viewModel.status.value.izFocus, 0.01f)
    }
}
