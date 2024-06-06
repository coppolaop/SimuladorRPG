package entity

import TestUtils
import com.coppolaop.entity.Personagem
import com.coppolaop.service.SimuladorService
import com.coppolaop.utils.RpgUtils
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class PersonagemTest {
    private lateinit var personagem: Personagem

    @BeforeEach
    fun setUp() {
        this.personagem = TestUtils.gerarPJSemClasse()
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun estaVivo_True_HPCheio() {
        assertTrue(personagem.estaVivo())
    }

    @Test
    fun estaVivo_True_HP1() {
        personagem.hpAtual = 1
        assertTrue(personagem.estaVivo())
    }

    @Test
    fun estaVivo_False_HP0() {
        personagem.hpAtual = 0
        assertFalse(personagem.estaVivo())
    }

    @Test
    fun estaVivo_False_HPNegativo() {
        personagem.hpAtual = -1
        assertFalse(personagem.estaVivo())
    }

    @Test
    fun estaFerido_False_HPCheio() {
        assertFalse(personagem.estaFerido())
    }

    @Test
    fun estaFerido_True_UmPontoAMenos() {
        personagem.hpAtual--
        assertTrue(personagem.estaFerido())
    }

    @Test
    fun estaFerido_True_HP1() {
        personagem.hpAtual = 1
        assertTrue(personagem.estaFerido())
    }

    @Test
    fun estaFerido_False_HP0() {
        personagem.hpAtual = 0
        assertFalse(personagem.estaFerido())
    }

    @Test
    fun estaFerido_False_HPNegativo() {
        personagem.hpAtual = -1
        assertFalse(personagem.estaFerido())
    }

    @Test
    fun estaSemFoco_False() {
        assertFalse(personagem.estaSemFoco())
    }

    @Test
    fun estaSemFoco_True() {
        personagem.penalidadeFalhaCritica = 5
        assertTrue(personagem.estaSemFoco())
    }

    @Test
    fun recalcularHabilidadeDeClasse() {
        assertDoesNotThrow { personagem.recalcularHabilidadeDeClasse() }
    }

    @Test
    fun rolarIniciativa() {
        for (x in 1..20) {
            mockkObject(RpgUtils)
            every { RpgUtils.rollDice("d20") } returns x

            personagem.rolarIniciativa()

            assertEquals(personagem.modIniciativa + x, personagem.iniciativa)
        }
    }

    @Test
    fun atacar_Acerto_FlagCriticoFalse() {
        mockkObject(RpgUtils)
        every { RpgUtils.rollDice("d20") } returns 20
        SimuladorService.flagCriticoEmMais10 = false
        val monstro = TestUtils.gerarMonstroComMuitoHP()

        personagem.atacar(monstro)

        assertNotEquals(monstro.hpMaximo, monstro.hpAtual)
    }

    @Test
    fun atacar_Erro_FlagCriticoFalse() {
        mockkObject(RpgUtils)
        every { RpgUtils.rollDice("d20") } returns 1
        SimuladorService.flagCriticoEmMais10 = false
        val monstro = TestUtils.gerarMonstroComMuitaCA()

        personagem.atacar(monstro)

        assertEquals(monstro.hpMaximo, monstro.hpAtual)
    }

    @Test
    fun atacar_Acerto_Critico() {
        mockkObject(RpgUtils)
        every { RpgUtils.rollDice("d20") } returns 20
        every { RpgUtils.rollDice(personagem.dadoDano) } returns 1
        SimuladorService.flagCriticoEmMais10 = true
        val monstro = TestUtils.gerarMonstroComMuitoHP()

        personagem.atacar(monstro)

        assertNotEquals(monstro.hpMaximo, monstro.hpAtual)
        assertEquals(monstro.hpMaximo - (9 + personagem.modDano), monstro.hpAtual)
    }

    @Test
    fun atacar_Acerto_Padrao() {
        mockkObject(RpgUtils)
        every { RpgUtils.rollDice("d20") } returns 10 - personagem.acerto
        every { RpgUtils.rollDice(personagem.dadoDano) } returns 1
        SimuladorService.flagCriticoEmMais10 = true
        val monstro = TestUtils.gerarMonstroComMuitoHP()
        monstro.ca = 10

        personagem.atacar(monstro)

        assertNotEquals(monstro.hpMaximo, monstro.hpAtual)
        assertEquals(monstro.hpMaximo - (1 + personagem.modDano), monstro.hpAtual)
    }

    @Test
    fun atacar_Erro_Critico() {
        mockkObject(RpgUtils)
        every { RpgUtils.rollDice("d20") } returns 1
        SimuladorService.flagCriticoEmMais10 = true
        val monstro = TestUtils.gerarMonstroComMuitoHP()
        monstro.ca = 11 + personagem.acerto

        personagem.atacar(monstro)

        assertEquals(monstro.hpMaximo, monstro.hpAtual)
        assertTrue(personagem.penalidadeFalhaCritica == 5)
    }

    @Test
    fun atacar_Erro_Padrao() {
        mockkObject(RpgUtils)
        every { RpgUtils.rollDice("d20") } returns 10 - (personagem.acerto + 1)
        SimuladorService.flagCriticoEmMais10 = true
        val monstro = TestUtils.gerarMonstroComMuitoHP()
        monstro.ca = 10

        personagem.atacar(monstro)

        assertEquals(monstro.hpMaximo, monstro.hpAtual)
    }

    @Test
    fun focar() {
        personagem.penalidadeFalhaCritica = 5
        assertEquals(5, personagem.penalidadeFalhaCritica)
        personagem.focar()
        assertEquals(0, personagem.penalidadeFalhaCritica)
    }

    @Test
    fun toString_Name() {
        assertEquals(personagem.nome, personagem.toString())
    }
}