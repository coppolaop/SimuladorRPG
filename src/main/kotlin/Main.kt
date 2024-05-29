package com.coppolaop

import com.coppolaop.service.SimuladorService

fun main() {
    SimuladorService("goblin", 8)
        .ativarAcaoTripla()
        .ativarCriticoEmMais10()
        .obterTaxasDeVitoriaPJ(10000000)
}