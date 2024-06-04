package com.coppolaop

import com.coppolaop.service.SimuladorService

fun main() {
    SimuladorService("gnoll", 4)
        .ativarAcaoTripla()
        .ativarCriticoEmMais10()
        .definirNivel(1)
        .obterTaxasDeVitoriaPJ(10000000)
}