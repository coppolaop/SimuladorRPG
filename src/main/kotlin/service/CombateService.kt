package com.coppolaop.service

import com.coppolaop.entity.Monstro
import com.coppolaop.entity.Personagem
import com.coppolaop.entity.classes.Clerigo
import com.coppolaop.entity.classes.Guerreiro
import com.coppolaop.entity.classes.Ladino
import com.coppolaop.entity.classes.Mago
import com.coppolaop.entity.dtos.ResultadoCombate

/**
 * Reúne as principais funcionalidades de combate,
 * separando Personagens do Jogador e Personagens do Mestre
 */
class CombateService() {
    private var personagensDosJogadores: List<Personagem> = ArrayList()
    private val personagensDoMestre: MutableList<Personagem> = ArrayList()
    private var ordemIniciativa: MutableList<Personagem> = ArrayList()
    var pjsMortos: MutableList<Personagem> = ArrayList()

    fun criarPJs() {
        personagensDosJogadores = listOf(
            Guerreiro("Guerreiro", 18, 23, 6, "1d8", 4, 0),
            Mago("Mago", 12, 11, 10, 6, "1d6", 3, 2),
            Ladino("Ladino", 16, 15, 6, "1d4", 4, 4),
            Clerigo("Clérigo", 18, 19, 8, 6, "1d6", 2, 0, "1d8", 3)
        )
    }

    fun ajustarNivelDosPJs() {
        for (personagem in personagensDosJogadores) {
            PersonagemService.aumentarNivel(personagem, SimuladorService.nivelDesejado)
        }
    }

    fun criarPDMs(monstros: MutableList<Personagem>) {
        personagensDoMestre.addAll(monstros)
    }

    fun rolarIniciativa() {
        for (personagem in ordemIniciativa) {
            personagem.rolarIniciativa()
        }
        ordemIniciativa = ordemIniciativa.sortedByDescending { it.iniciativa }.toMutableList()
    }

    fun iniciarCombate(): ResultadoCombate {
        val resultado = ResultadoCombate()
        val personagensDosJogadores: MutableList<Personagem> = ArrayList(this.personagensDosJogadores)
        val personagensDoMestre: MutableList<Personagem> = ArrayList(this.personagensDoMestre)

        ordemIniciativa = prepararInicioCombate(personagensDosJogadores, personagensDoMestre)
        rolarIniciativa()
        val acaoService = AcaoService(personagensDosJogadores, personagensDoMestre, pjsMortos)

        while (personagensDosJogadores.isNotEmpty() && personagensDoMestre.isNotEmpty()) {
            resultado.quantidadeTurnos++
            for (personagem in ordemIniciativa) {
                val quantidadeDeAcoes = if (SimuladorService.flagAcaoTripla) 3 else 1

                for (i in 1..quantidadeDeAcoes) {
                    resultado.quantidadeAcoes++
                    acaoService.executarAcao(personagem)

                    if (houveramBaixas(
                            personagensDoMestre,
                            resultado
                        ) && foramTodosMortos(personagensDoMestre)
                    ) {
                        return resultado
                    }
                    if (houveramBaixas(personagensDosJogadores, resultado) && foramTodosMortos(
                            personagensDosJogadores
                        )
                    ) {
                        resultado.pjsVenceram = false
                        return resultado
                    }
                    reestabilizarCaidos(personagensDosJogadores)
                }
                personagem.penalidadesAcerto = 0
            }
        }
        resultado.pjsVenceram = personagensDoMestre.isEmpty()
        return resultado
    }

    private fun prepararInicioCombate(
        personagensDosJogadores: List<Personagem>,
        personagensDoMestre: List<Personagem>
    ): MutableList<Personagem> {
        pjsMortos = ArrayList()
        for (personagem in personagensDosJogadores) {
            personagem.hpAtual = personagem.hpMaximo
            personagem.energiaAtual = personagem.energiaMaxima
        }
        for (personagem in personagensDoMestre) {
            personagem.hpAtual = personagem.hpMaximo
            personagem.energiaAtual = personagem.energiaMaxima
        }
        val listaDeIniciativa: MutableList<Personagem> = ArrayList(personagensDosJogadores)
        listaDeIniciativa.addAll(personagensDoMestre)
        return listaDeIniciativa
    }

    private fun houveramBaixas(personagens: MutableList<Personagem>, resultado: ResultadoCombate): Boolean {
        if (!personagens[0].estaVivo()) {
            if (personagens[0] !is Monstro) {
                pjsMortos.add(personagens[0])
                resultado.quantidadeQuedasPJ++
            }
            personagens.removeAt(0)
            return true
        }
        return false
    }

    private fun foramTodosMortos(personagens: MutableList<Personagem>): Boolean {
        if (personagens.isEmpty()) {
            return true
        }
        return false
    }

    private fun reestabilizarCaidos(personagens: MutableList<Personagem>) {
        if (pjsMortos.isNotEmpty() && pjsMortos[0].estaVivo()) {
            personagens.add(0, pjsMortos[0])
            pjsMortos.removeAt(0)
        }
    }
}