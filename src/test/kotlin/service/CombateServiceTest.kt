package service

import TestUtils
import com.coppolaop.service.CombateService
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CombateServiceTest {
    val service = CombateService()

    @Test
    fun PJsVencem_True() {
        service.criarPJs()
        val monstro = TestUtils.gerarMonstroComMuitoHP()
        service.criarPDMs(mutableListOf(monstro))

        val response = service.PJsVencem()

        assertTrue(response)
    }

    @Test
    fun PJsVencem_False() {
        service.criarPJs()
        val monstro = TestUtils.gerarMonstroExtremamentePoderoso()
        service.criarPDMs(mutableListOf(monstro))

        val response = service.PJsVencem()

        assertFalse(response)
    }
}