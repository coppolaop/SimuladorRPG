package service

import TestUtils
import com.coppolaop.service.CombateService
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CombateServiceTest {
    val service = CombateService()

    @Test
    fun iniciarCombate_True() {
        service.criarPJs()
        val monstro = TestUtils.gerarMonstroComMuitoHP()
        service.criarPDMs(mutableListOf(monstro))

        val response = service.iniciarCombate()

        assertTrue(response.pjsVenceram)
        assertEquals(0, response.quantidadeQuedasPJ)
    }

    @Test
    fun iniciarCombate_False() {
        service.criarPJs()
        val monstro = TestUtils.gerarMonstroExtremamentePoderoso()
        service.criarPDMs(mutableListOf(monstro))

        val response = service.iniciarCombate()

        assertFalse(response.pjsVenceram)
        assertEquals(4, response.quantidadeQuedasPJ)
    }
}