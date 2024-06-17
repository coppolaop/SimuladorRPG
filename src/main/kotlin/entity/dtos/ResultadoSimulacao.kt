package com.coppolaop.entity.dtos

class ResultadoSimulacao {
    var vitorias = 0
    var vitoriasSemBaixa = 0
    var turnos = 0
    var acoes = 0
    var quedas = 0

    fun somarResultadoCombate(resultado: ResultadoCombate) {
        turnos += resultado.quantidadeTurnos
        acoes += resultado.quantidadeAcoes
        quedas += resultado.quantidadeQuedasPJ
        if (resultado.pjsVenceram) {
            vitorias++
            if (resultado.quantidadeQuedasPJ == 0) {
                vitoriasSemBaixa++
            }
        }
    }

    fun calcularPorcentagens(amostragem: Int): ResultadoSimulacao {
        vitorias /= amostragem / 100
        vitoriasSemBaixa /= amostragem / 100
        println("Porcentagem de vitória: $vitorias%")
        println("Porcentagem de vitória sem baixas: $vitoriasSemBaixa%")
        return this
    }

    fun calcularMedias(amostragem: Int): ResultadoSimulacao {
        turnos /= amostragem
        acoes /= amostragem
        quedas /= amostragem
        println("Quantidade média de turnos: $turnos")
        println("Quantidade média de ações: $acoes")
        println("Quantidade média de quedas: $quedas")
        return this
    }
}