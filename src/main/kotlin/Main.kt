package com.coppolaop

import com.coppolaop.service.PersonagemService
import com.coppolaop.service.SimuladorService
import java.io.File

fun main() {
    println("Bem vindo ao Simulador de combates de RPG")
    File(PersonagemService.pdmDirectory).mkdirs()
    val monstro = escolherMonstro()
    println("Insira a quantidade que você deseja de monstros desse tipo: ")
    var quantidade = readLine()
    quantidade = if (quantidade.isNullOrBlank()) null else quantidade
    if (quantidade == "0") {
        println("A taxa de vitória contra nenhum monstro é sempre 100%, se é que você pode chamar isso de vitória")
        return
    }
    println("Informe o nível desejado do grupo de aventureiros: ")
    var nivel = readLine()
    nivel = if (nivel.isNullOrBlank()) null else nivel
    if (nivel == "0") {
        println("Personagens de nivel 0 ainda não estão prontos para se aventurar. A taxa de vitória é de 0%.")
        return
    }

    SimuladorService(monstro ?: "gnoll", quantidade?.toInt() ?: 1)
        .ativarAcaoTripla()
        .ativarCriticoEmMais10()
        .definirNivel(nivel?.toInt() ?: 1)
        .obterTaxasDeVitoriaPJ(10000000)
}

fun escolherMonstro(): String? {
    val monstros = PersonagemService.listarMonstros()
    imprimirListaMonstros(monstros)
    println("Escolha um monstro (pasta ${PersonagemService.pdmDirectory}) da lista pelo número: ")
    var monstro = readLine()
    monstro = if (monstro.isNullOrBlank()) null else monstros[monstro.toInt()]
    return monstro
}

fun imprimirListaMonstros(monstros: List<String>) {
    for (linha in 0..monstros.size / 5) {
        for (i in 0..4) {
            val position = linha * 5 + i
            if (monstros.size <= position) {
                break
            }
            print("| $position - ${monstros[position]} |")
        }
        println()
    }
}

