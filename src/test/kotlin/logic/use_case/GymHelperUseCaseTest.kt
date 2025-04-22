package logic.use_case


import io.mockk.mockk
import org.example.logic.RecipesRepository
import org.example.logic.use_case.GymHelperUseCase
import org.junit.jupiter.api.BeforeEach

class GymHelperUseCaseTest {
    private lateinit var repository: RecipesRepository
    private lateinit var gymHelperUseCase: GymHelperUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        gymHelperUseCase = GymHelperUseCase(repository)
    }

}