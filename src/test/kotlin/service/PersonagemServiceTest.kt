package service

import TestUtils
import com.coppolaop.entity.Personagem
import com.coppolaop.service.PersonagemService
import com.coppolaop.service.SimuladorService
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockkConstructor
import io.mockk.spyk
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.io.File
import java.io.IOException

class PersonagemServiceTest {
    private val service = PersonagemService()

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            PersonagemService.pdmDirectory = "src/test/resources/pdm/"
            PersonagemService.pjDirectory = "src/test/resources/pj/"
        }

        @JvmStatic
        @AfterAll
        fun setdown() {
            PersonagemService.pdmDirectory = "src/main/resources/pdm/"
            PersonagemService.pjDirectory = "src/main/resources/pj/"
        }
    }

    @Test
    fun lerMonstro() {
        val response = service.lerMonstro("goblin")

        assertEquals("Goblin", response.nome)
        assertEquals(13, response.ca)
        assertEquals(15, response.hpMaximo)
        assertEquals(7, response.acerto)
        assertEquals("1d4", response.dadoDano)
        assertEquals(3, response.modDano)
        assertEquals(2, response.iniciativa)
    }

    @Test
    fun lerAventureiro() {
        val response = service.lerAventureiro(TestUtils.gerarPJGuerreiro())

        validarGuerreiro(response)
    }

    @Test
    fun carregarAventureiros() {
        val response = service.carregarAventureiros()

        assertEquals(4, response.size)
        validarClerigoPadrao(response[0])
        validarGuerreiro(response[1])
        validarLadinoPadrao(response[2])
        validarMagoPadrao(response[3])

        val personagensSemFicha = listOf<String>("clerigo", "ladino", "mago")
        for (personagem in personagensSemFicha) {
            val file = File("${PersonagemService.pjDirectory}${personagem}.json")
            if (file.exists()) {
                file.delete()
            }
        }
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

    @Test
    fun listarMonstros() {
        val response = PersonagemService.listarMonstros()

        assertEquals(1, response.size)
        assertEquals("goblin", response[0])
    }

    @Test
    fun listarMonstros_diretorioVazio() {
        PersonagemService.pdmDirectory = "src/test/resources/pdm/novo-diretorio/"
        var diretorio = File(PersonagemService.pdmDirectory)
        diretorio.mkdir()

        val monstros = PersonagemService.listarMonstros()

        assertEquals(1, monstros.size)
        assertEquals("gnoll", monstros[0])

        diretorio.deleteRecursively()
        PersonagemService.pdmDirectory = "src/test/resources/pdm/"
    }

    @Test
    fun listarMonstros_erro() {
        PersonagemService.pdmDirectory = "src/test/resources/pdm/novo-diretorio/"
        var diretorio = File(PersonagemService.pdmDirectory)
        diretorio.mkdir()

        var wasThrown = false
        mockkConstructor(ObjectMapper::class)
        val exception = spyk<IOException>()
        every { anyConstructed<ObjectMapper>().writerWithDefaultPrettyPrinter() } throws exception
        every { exception.printStackTrace() } answers { wasThrown = true }

        PersonagemService.listarMonstros()

        assertTrue(wasThrown)

        unmockkAll()
        diretorio.deleteRecursively()
        PersonagemService.pdmDirectory = "src/test/resources/pdm/"
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

    private fun validarClerigoPadrao(response: Personagem) {
        assertEquals("Clerigo", response.nome)
        assertEquals(18, response.ca)
        assertEquals(18, response.hpMaximo)
        assertEquals(6, response.acerto)
        assertEquals("1d6", response.dadoDano)
        assertEquals(2, response.modDano)
        assertEquals(0, response.iniciativa)
    }

    private fun validarGuerreiro(response: Personagem) {
        assertEquals("Guerreiro", response.nome)
        assertEquals(16, response.ca)
        assertEquals(10, response.hpMaximo)
        assertEquals(8, response.acerto)
        assertEquals("1d8", response.dadoDano)
        assertEquals(2, response.modDano)
        assertEquals(0, response.iniciativa)
    }

    private fun validarLadinoPadrao(response: Personagem) {
        assertEquals("Ladino", response.nome)
        assertEquals(16, response.ca)
        assertEquals(15, response.hpMaximo)
        assertEquals(6, response.acerto)
        assertEquals("1d4", response.dadoDano)
        assertEquals(4, response.modDano)
        assertEquals(4, response.iniciativa)
    }

    private fun validarMagoPadrao(response: Personagem) {
        assertEquals("Mago", response.nome)
        assertEquals(12, response.ca)
        assertEquals(12, response.hpMaximo)
        assertEquals(6, response.acerto)
        assertEquals("1d6", response.dadoDano)
        assertEquals(3, response.modDano)
        assertEquals(2, response.iniciativa)
    }
}