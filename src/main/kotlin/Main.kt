package com.coppolaop

import com.coppolaop.service.PersonagemService
import com.coppolaop.service.SimuladorService
import java.io.File

fun main() {
    println("Bem vindo ao Simulador de combates de RPG")
    File("resources/pdm").mkdirs()
    val monstro = escolherMonstro()
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

fun escolherMonstro(): String? {
    var monstros = PersonagemService.listarMonstros(false)
    imprimirListaMonstros(monstros, false)
    println("Escolha um monstro (pasta resources/pdm) da lista pelo número: ")
    var monstro = readLine()

    if (monstro.equals("x", true)) {
        monstros = PersonagemService.listarMonstros(true)
        imprimirListaMonstros(monstros, true)
        println("Escolha um monstro da lista pelo número: ")
        monstro = readLine()
    }

    monstro = if (monstro.isNullOrBlank()) null else monstros[monstro.toInt()]

    return monstro
}

fun imprimirListaMonstros(monstros: List<String>, isInternal: Boolean) {
    for (linha in 0..monstros.size / 5) {
        for (i in 0..4) {
            val position = linha * 5 + i
            if (monstros.size <= position) {
                if (!isInternal) {
                    print("| x - Listar monstros internos do sistema |")
                }
                break
            }
            print("| $position - ${monstros[position]} |")
        }
        println()
    }
}

