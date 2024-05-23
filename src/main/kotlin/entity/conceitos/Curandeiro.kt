package com.coppolaop.entity.conceitos

import com.coppolaop.entity.Personagem
import com.coppolaop.utils.RpgUtils

interface Curandeiro {
    var dadoCura: String
    var modCura: Int

    fun curar(ferido: Personagem) {
        val cura: Int = RpgUtils.rollDice(this.dadoCura) + this.modCura
        ferido.hpAtual = ferido.hpAtual + cura
        if (ferido.hpAtual.compareTo(ferido.hpMaximo) > 0) {
            ferido.hpAtual = ferido.hpMaximo
        }
    }
}