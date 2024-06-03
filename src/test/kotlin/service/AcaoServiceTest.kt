package service

import TestUtils
import com.coppolaop.entity.Personagem
import com.coppolaop.service.AcaoService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AcaoServiceTest {
    private var personagensDoJogador: MutableList<Personagem> = ArrayList()
    private var personagensDoMestre: MutableList<Personagem> = ArrayList()
    private var mortos: MutableList<Personagem> = ArrayList()
    private var service = AcaoService(personagensDoJogador, personagensDoMestre, mortos)

    @Test
    fun executarAcao_PJ_atacar() {
        personagensDoJogador = mutableListOf(TestUtils.gerarPJGuerreiro())
        personagensDoMestre = mutableListOf(TestUtils.gerarMonstroComMuitoHP())
        mortos = ArrayList()
        service = AcaoService(personagensDoJogador, personagensDoMestre, mortos)

        service.executarAcao(personagensDoJogador[0])

        assertEquals(personagensDoJogador[0].hpMaximo, personagensDoJogador[0].hpAtual)
        assertNotEquals(personagensDoMestre[0].hpMaximo, personagensDoMestre[0].hpAtual)
    }

    @Test
    fun executarAcao_PDM_atacar() {
        personagensDoJogador = mutableListOf(TestUtils.gerarPJGuerreiro())
        personagensDoMestre = mutableListOf(TestUtils.gerarMonstroComMuitoAcerto())
        mortos = ArrayList()
        service = AcaoService(personagensDoJogador, personagensDoMestre, mortos)

        service.executarAcao(personagensDoMestre[0])

        assertEquals(personagensDoMestre[0].hpMaximo, personagensDoMestre[0].hpAtual)
        assertNotEquals(personagensDoJogador[0].hpMaximo, personagensDoJogador[0].hpAtual)
    }

    @Test
    fun executarAcao_PJ_curar() {
        personagensDoJogador = mutableListOf(TestUtils.gerarPJGuerreiroFerido(), TestUtils.gerarPJClerigo())
        personagensDoMestre = mutableListOf(TestUtils.gerarMonstroComMuitoHP())
        mortos = ArrayList()
        service = AcaoService(personagensDoJogador, personagensDoMestre, mortos)

        service.executarAcao(personagensDoJogador[1])

        assertEquals(personagensDoJogador[0].hpMaximo, personagensDoJogador[0].hpAtual)
        assertEquals(personagensDoMestre[0].hpMaximo, personagensDoMestre[0].hpAtual)
    }

    @Test
    fun executarAcao_PJ_curarCaido() {
        personagensDoJogador = mutableListOf(TestUtils.gerarPJClerigo())
        personagensDoMestre = mutableListOf(TestUtils.gerarMonstroComMuitoHP())
        mortos = mutableListOf(TestUtils.gerarPJGuerreiroCaido())
        service = AcaoService(personagensDoJogador, personagensDoMestre, mortos)

        service.executarAcao(personagensDoJogador[0])

        assertEquals(1, personagensDoJogador.size)
        assertTrue(mortos.isNotEmpty())
        assertNotEquals(0, mortos[0].hpAtual)
        assertEquals(personagensDoMestre[0].hpMaximo, personagensDoMestre[0].hpAtual)
    }

    @Test
    fun executarAcao_PJ_ataquePorFaltaDeEnergia() {
        personagensDoJogador = mutableListOf(TestUtils.gerarPJGuerreiroFerido(), TestUtils.gerarPJClerigoSemEnergia())
        personagensDoMestre = mutableListOf(TestUtils.gerarMonstroComMuitoHP())
        mortos = ArrayList()
        service = AcaoService(personagensDoJogador, personagensDoMestre, mortos)

        service.executarAcao(personagensDoJogador[1])

        assertNotEquals(personagensDoJogador[0].hpMaximo, personagensDoJogador[0].hpAtual)
        assertNotEquals(personagensDoMestre[0].hpMaximo, personagensDoMestre[0].hpAtual)
    }

    @Test
    fun executarAcao_PJ_focar() {
        personagensDoJogador = mutableListOf(TestUtils.gerarPJGuerreiroSemFoco())
        personagensDoMestre = mutableListOf(TestUtils.gerarMonstroComMuitoHP())
        mortos = ArrayList()
        service = AcaoService(personagensDoJogador, personagensDoMestre, mortos)

        service.executarAcao(personagensDoJogador[0])

        assertEquals(0, personagensDoJogador[0].penalidadeFalhaCritica)
        assertEquals(personagensDoJogador[0].hpMaximo, personagensDoJogador[0].hpAtual)
        assertEquals(personagensDoMestre[0].hpMaximo, personagensDoMestre[0].hpAtual)
    }

    @Test
    fun executarAcao_PDM_focar() {
        personagensDoJogador = mutableListOf(TestUtils.gerarPJGuerreiro())
        personagensDoMestre = mutableListOf(TestUtils.gerarMonstroSemFoco())
        mortos = ArrayList()
        service = AcaoService(personagensDoJogador, personagensDoMestre, mortos)

        service.executarAcao(personagensDoMestre[0])

        assertEquals(0, personagensDoMestre[0].penalidadeFalhaCritica)
        assertEquals(personagensDoJogador[0].hpMaximo, personagensDoJogador[0].hpAtual)
        assertEquals(personagensDoMestre[0].hpMaximo, personagensDoMestre[0].hpAtual)
    }
}