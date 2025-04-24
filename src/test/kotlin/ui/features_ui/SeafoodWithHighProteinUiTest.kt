package ui.features_ui

import io.mockk.mockk
import org.example.logic.use_case.SeafoodWithHighProteinUseCase
import org.example.ui.Viewer
import org.example.ui.features_ui.SeafoodWithHighProteinUi
import org.junit.jupiter.api.BeforeEach

class SeafoodWithHighProteinUiTest {

    private val dummyUseCase: SeafoodWithHighProteinUseCase = mockk(relaxed = true)
    private val viewer: Viewer = mockk(relaxed = true)
    private lateinit var seafoodWithHighProteinUi: SeafoodWithHighProteinUi

    @BeforeEach
    fun setup() {
        seafoodWithHighProteinUi = SeafoodWithHighProteinUi(dummyUseCase, viewer)
    }
    
}
