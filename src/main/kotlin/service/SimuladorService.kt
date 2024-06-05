package com.coppolaop.service

import com.coppolaop.entity.Personagem

/**
 * Reúne funcionalidades relativas ao simulador em si,
 * funcionando de maneira similar a um padrão Builder para o CombateService
 */
class SimuladorService(private val monstro: String, private val quantidade: Int) {
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
        var porcentagemVitoria = 0.0
        var porcentagemSemBaixas = 0.0
        val dezPorCentoDaAmostragem = amostragem / 10
        for (i in 1..10) {
            for (j in 1..<dezPorCentoDaAmostragem) {
                if (combateService.PJsVencem()) {
                    porcentagemVitoria++
                    if (combateService.pjsMortos.isEmpty()) {
                        porcentagemSemBaixas++
                    }
                }
            }
            println("Simulação em ${i}0%")
        }
        porcentagemVitoria /= amostragem / 100
        porcentagemSemBaixas /= amostragem / 100
        println("Resultado da simulação de combate de um grupo padrão nivel $nivelDesejado contra ${this.quantidade} monstro(s) do tipo ${this.monstro}")
        println("Porcentagem de vitória: $porcentagemVitoria%")
        println("Porcentagem de vitória sem baixas: $porcentagemSemBaixas%")
    }
}