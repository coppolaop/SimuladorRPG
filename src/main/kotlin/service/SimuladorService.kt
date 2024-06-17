package com.coppolaop.service

import com.coppolaop.entity.Personagem
import com.coppolaop.entity.dtos.ResultadoSimulacao

/**
 * Reúne funcionalidades relativas ao simulador em si,
 * funcionando de maneira similar a um padrão Builder para o CombateService
 */
class SimuladorService(private var monstro: String, private val quantidade: Int) {
    private val combateService: CombateService = CombateService()
    private val personagemService: PersonagemService = PersonagemService()

    companion object {
        var flagAcaoTripla = false
        var flagCriticoEmMais10 = false
        var nivelDesejado = 1
    }

    init {
        combateService.criarPJs()
        val monstros: MutableList<Personagem> = ArrayList()
        for (i in 1..quantidade) {
            monstros.add(personagemService.lerMonstro(monstro))
        }
        combateService.criarPDMs(monstros)
        this.monstro = monstros[0].nome
    }

    fun ativarAcaoTripla(): SimuladorService {
        flagAcaoTripla = true
        return this
    }

    fun ativarCriticoEmMais10(): SimuladorService {
        flagCriticoEmMais10 = true
        return this
    }

    fun definirNivel(nivel: Int): SimuladorService {
        nivelDesejado = nivel
        combateService.ajustarNivelDosPJs()
        return this
    }

    fun obterTaxasDeVitoriaPJ(amostragem: Int) {
        println("Iniciando simulação de combate de um grupo padrão nivel $nivelDesejado contra ${this.quantidade} monstro(s) do tipo ${this.monstro}")
        val resultado = ResultadoSimulacao()
        val dezPorCentoDaAmostragem = amostragem / 10
        for (i in 1..10) {
            for (j in 1..<dezPorCentoDaAmostragem) {
                resultado.somarResultadoCombate(combateService.iniciarCombate())
            }
            println("Simulação em ${i}0%")
        }
        println("Resultado da simulação de combate de um grupo padrão nivel $nivelDesejado contra ${this.quantidade} monstro(s) do tipo ${this.monstro}")
        resultado.calcularPorcentagens(amostragem).calcularMedias(amostragem)
    }
}