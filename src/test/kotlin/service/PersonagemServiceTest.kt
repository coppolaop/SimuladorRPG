package service

import TestUtils
import com.coppolaop.entity.Personagem
import com.coppolaop.service.PersonagemService
import com.coppolaop.service.SimuladorService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

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

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20])
    fun aumentarNivel_Guerreiro(nivel: Int) {
        val guerreiro = TestUtils.gerarPJGuerreiro()
        aumentarNivel(guerreiro, nivel)
        assertEquals((((nivel - 1) / 4) * 4) + 4, guerreiro.incrementoAcerto)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20])
    fun aumentarNivel_Clerigo(nivel: Int) {
        val clerigo = TestUtils.gerarPJClerigo()
        aumentarNivel(clerigo, nivel)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20])
    fun aumentarNivel_Ladino(nivel: Int) {
        val ladino = TestUtils.gerarPJLadino()
        aumentarNivel(ladino, nivel)
        assertEquals("${((nivel + 1) / 2)}d6", ladino.ataqueFurtivo)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20])
    fun aumentarNivel_Mago(nivel: Int) {
        val mago = TestUtils.gerarPJMago()
        val hpBase = mago.hpMaximo / 3
        val energiaBase = mago.energiaMaxima
        val acertoBase = mago.acerto
        SimuladorService.nivelDesejado = nivel

        PersonagemService.aumentarNivel(mago, nivel)

        assertEquals(hpBase * (nivel + 2), mago.hpMaximo)
        assertEquals(hpBase * (nivel + 2), mago.hpAtual)
        assertEquals((energiaBase * nivel) + nivel, mago.energiaMaxima)
        assertEquals((energiaBase * nivel) + nivel, mago.energiaAtual)
        assertEquals(acertoBase + ((nivel - 1) / 2), mago.acerto)
    }

    private fun aumentarNivel(personagem: Personagem, nivel: Int) {
        val hpBase = personagem.hpMaximo / 3
        val energiaBase = personagem.energiaMaxima
        val acertoBase = personagem.acerto
        SimuladorService.nivelDesejado = nivel

        PersonagemService.aumentarNivel(personagem, nivel)

        assertEquals(hpBase * (nivel + 2), personagem.hpMaximo)
        assertEquals(hpBase * (nivel + 2), personagem.hpAtual)
        assertEquals(energiaBase * nivel, personagem.energiaMaxima)
        assertEquals(energiaBase * nivel, personagem.energiaAtual)
        assertEquals(acertoBase + ((nivel - 1) / 2), personagem.acerto)
    }
}