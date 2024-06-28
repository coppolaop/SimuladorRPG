package com.coppolaop

import com.coppolaop.service.SimuladorService

fun main() {
    println("Bem vindo ao Simulador de combates de RPG")
    println("Digite o nome do arquivo onde está o monstro (pasta resources/pdm):")
    var monstro = readLine()
    monstro = if (monstro.isNullOrBlank()) null else monstro
    println("Insira a quantidade que você deseja de monstros desse tipo: ")
    var quantidade = readLine()
    quantidade = if (quantidade.isNullOrBlank()) null else quantidade
    println("Informe o nível desejado do grupo de aventureiros: ")
    var nivel = readLine()
    nivel = if (nivel.isNullOrBlank()) null else nivel

    SimuladorService(monstro ?: "gnoll", quantidade?.toInt() ?: 1)
        .ativarAcaoTripla()
        .ativarCriticoEmMais10()
        .definirNivel(nivel?.toInt() ?: 1)
        .obterTaxasDeVitoriaPJ(10000000)
}