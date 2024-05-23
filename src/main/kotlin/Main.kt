package com.coppolaop

import com.coppolaop.entity.Monstro
import com.coppolaop.service.CombateService

fun main() {
    val combate = CombateService()
    combate.criarPJs()
    val troll = Monstro("Troll", 20, 40, 3, "1d8", 7, 2)
    val troll2 = Monstro("Troll", 20, 40, 3, "1d8", 7, 2)
    combate.criarPDMs(troll, troll2)
    println("Porcentagem de vitória: " + combate.porcentagemDeVitoriaDePJ(100000) + "%")
}
