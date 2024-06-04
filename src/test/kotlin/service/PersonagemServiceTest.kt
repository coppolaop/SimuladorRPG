package service

import com.coppolaop.service.PersonagemService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PersonagemServiceTest {
    private val service = PersonagemService()

    @Test
    fun lerMonstro() {
        val response = service.lerMonstro("gnoll")

        assertEquals("Gnoll", response.nome)
        assertEquals(15, response.ca)
        assertEquals(15, response.hpMaximo)
        assertEquals(10, response.acerto)
        assertEquals("1d6", response.dadoDano)
        assertEquals(4, response.modDano)
        assertEquals(1, response.iniciativa)
    }
}