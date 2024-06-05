package com.coppolaop.entity.classes

import com.coppolaop.entity.Personagem
import com.coppolaop.service.SimuladorService
import com.coppolaop.utils.RpgUtils

class Ladino(
    nome: String,
    ca: Int,
    hpMaximo: Int,
    acerto: Int,
    dadoDano: String,
    modDano: Int,
    iniciativa: Int,
) : Personagem(
    nome,
    ca,
    hpMaximo,
    4,
    acerto,
    dadoDano,
    modDano,
    iniciativa,
) {
    var ataqueFurtivo = "1d6"

    override fun recalcularHabilidadeDeClasse() {
        ataqueFurtivo = "${(SimuladorService.nivelDesejado + 1) / 2}d6"
    }

    override fun atacar(defensor: Personagem) {
        val danoFurtivo = RpgUtils.rollDice(ataqueFurtivo)
        modDano += danoFurtivo
        super.atacar(defensor)
        modDano -= danoFurtivo
        return
    }
}