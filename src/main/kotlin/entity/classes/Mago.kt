package com.coppolaop.entity.classes

import com.coppolaop.entity.Personagem
import com.coppolaop.service.SimuladorService
import com.coppolaop.utils.RpgUtils

class Mago(
    nome: String,
    ca: Int,
    hpMaximo: Int,
    energiaMaxima: Int,
    acerto: Int,
    dadoDano: String,
    modDano: Int,
    iniciativa: Int,
) : Personagem(
    nome,
    ca,
    hpMaximo,
    energiaMaxima,
    acerto,
    dadoDano,
    modDano,
    iniciativa,
) {
    override fun recalcularHabilidadeDeClasse() {
        energiaMaxima += SimuladorService.nivelDesejado
        energiaAtual = energiaMaxima
    }

    override fun atacar(defensor: Personagem) {
        if (energiaAtual > 0) {
            energiaAtual--
            val danoMagia = RpgUtils.rollDice("${SimuladorService.nivelDesejado}d4")
            modDano += danoMagia
            super.atacar(defensor)
            modDano -= danoMagia
            return
        }
    }
}