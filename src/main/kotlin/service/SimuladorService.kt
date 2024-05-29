package com.coppolaop.service

import com.coppolaop.entity.Personagem

/**
 * Reúne funcionalidades relativas ao simulador em si,
 * funcionando de maneira similar a um padrão Builder para o CombateService
 */
class SimuladorService(monstro: String, quantidade: Int) {
    private val combateService = CombateService()
    private val personagemService = PersonagemService()

    companion object {
        var flagAcaoTripla = false
        var flagCriticoEmMais10 = false
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