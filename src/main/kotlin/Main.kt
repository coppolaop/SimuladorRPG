package com.coppolaop

import com.coppolaop.service.SimuladorService

fun main() {
    SimuladorService()
        .ativarAcaoTripla()
        .ativarCriticoEmMais10()
        .obterTaxasDeVitoriaPJ(100000)
}
