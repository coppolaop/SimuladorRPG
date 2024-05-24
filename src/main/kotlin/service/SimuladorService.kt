package com.coppolaop.service

import com.coppolaop.entity.Monstro

/**
 * Reúne funcionalidades relativas ao simulador em si,
 * funcionando de maneira similar a um padrão Builder para o CombateService
 */
class SimuladorService() {
    private val combateService = CombateService()

    companion object {
        var flagAcaoTripla = false
        var flagCriticoEmMais10 = false
    }

    init {
        combateService.criarPJs()
        val troll = Monstro("Troll", 20, 40, 3, "1d8", 7, 1)
        val troll2 = Monstro("Troll", 20, 40, 3, "1d8", 7, 2)
        combateService.criarPDMs(troll, troll2)
    }

    fun ativarAcaoTripla(): SimuladorService {
        flagAcaoTripla = true
        return this
    }

    fun ativarCriticoEmMais10(): SimuladorService {
        flagCriticoEmMais10 = true
        return this
    }

    fun obterTaxasDeVitoriaPJ(amostragem: Int) {
        var porcentagemVitoria = 0.0
        var porcentagemSemBaixas = 0.0
        for (i in 0..<amostragem) {
            if (combateService.PJsVencem()) {
                porcentagemVitoria++
                if (combateService.pjsMortos == 0) {
                    porcentagemSemBaixas++
                }
            }
        }
        porcentagemVitoria /= amostragem / 100
        porcentagemSemBaixas /= amostragem / 100
        println("Porcentagem de vitória: $porcentagemVitoria%")
        println("Porcentagem de vitória sem baixas: $porcentagemSemBaixas%")
    }
}