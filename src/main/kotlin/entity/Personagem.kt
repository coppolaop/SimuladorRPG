package com.coppolaop.entity

import com.coppolaop.service.SimuladorService
import com.coppolaop.utils.RpgUtils.maxDice
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
    var penalidadesAcerto: Int = 0
    var penalidadeFalhaCritica: Int = 0


    fun estaVivo(): Boolean = this.hpAtual > 0
    fun estaFerido(): Boolean = estaVivo() && this.hpAtual < this.hpMaximo
    fun estaSemFoco(): Boolean = this.penalidadeFalhaCritica > 0

    fun rolarIniciativa() {
        this.iniciativa = this.modIniciativa + rollDice("d20")
    }

    fun atacar(defensor: Personagem) {
        val valorAtaque = rollDice("d20") + this.acerto - (this.penalidadesAcerto + this.penalidadeFalhaCritica)
        this.penalidadesAcerto = 5

        if (!SimuladorService.flagCriticoEmMais10) {
            if (valorAtaque - defensor.ca >= 0) causarDano(defensor)
            return
        }

        when {
            (valorAtaque - defensor.ca >= 10) -> causarDanoCritico(defensor)
            (valorAtaque - defensor.ca >= 0) -> causarDano(defensor)
            (valorAtaque - defensor.ca <= -10) -> falharCriticamente()
        }
    }

    private fun causarDanoCritico(defensor: Personagem) {
        val dano = maxDice(this.dadoDano) + rollDice(this.dadoDano) + this.modDano
        defensor.hpAtual -= dano
    }

    private fun causarDano(defensor: Personagem) {
        val dano = rollDice(this.dadoDano) + this.modDano
        defensor.hpAtual -= dano
    }

    private fun falharCriticamente() {
        this.penalidadeFalhaCritica += 5
    }

    fun focar() {
        this.penalidadeFalhaCritica = 0
    }

    override fun toString(): String {
        return nome
    }
}
