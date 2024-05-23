package com.coppolaop.entity

import com.coppolaop.utils.RpgUtils.rollDice

open class Personagem(
    val nome: String,
    var ca: Int,
    var hpMaximo: Int,
    var acerto: Int,
    var dadoDano: String,
    var modDano: Int,
    var modIniciativa: Int
) {
    var hpAtual: Int = this.hpMaximo
    var iniciativa: Int = this.modIniciativa

    fun estaVivo(): Boolean = this.hpAtual > 0
    fun estaFerido(): Boolean = estaVivo() && this.hpAtual < this.hpMaximo

    fun rolarIniciativa() {
        this.iniciativa = this.modIniciativa + rollDice("d20")
    }

    fun atacar(defensor: Personagem) {
        val valorAtaque = rollDice("d20") + this.acerto

        if (valorAtaque.compareTo(defensor.ca) > -1) {
            val dano = rollDice(this.dadoDano) + this.modDano
            defensor.hpAtual = defensor.hpAtual - dano
        }
    }
}