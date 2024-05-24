package com.coppolaop.service

import com.coppolaop.entity.Monstro

/**
 * Reúne funcionalidades relativas ao simulador em si,
 * funcionando de maneira similar a um padrão Builder para o CombateService
 */
class SimuladorService() {
    private val combateService = CombateService()

    init {
        combateService.criarPJs()
        val troll = Monstro("Troll", 20, 40, 3, "1d8", 7, 2)
        val troll2 = Monstro("Troll", 20, 40, 3, "1d8", 7, 2)
        combateService.criarPDMs(troll, troll2)
    }

    fun obterTaxasDeVitoriaPJ(amostragem: Int, acaoTriplaAtivada: Boolean) {
        combateService.acaoTriplaAtivada = acaoTriplaAtivada
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