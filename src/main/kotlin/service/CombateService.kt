package com.coppolaop.service

import com.coppolaop.entity.Monstro
import com.coppolaop.entity.Personagem
import com.coppolaop.entity.classes.Clerigo

/**
 * Reúne as principais funcionalidades de combate,
 * separando Personagens do Jogador e Personagens do Mestre
 */
class CombateService() {
    private var personagensDosJogadores: List<Personagem> = ArrayList()
    private val personagensDoMestre: MutableList<Personagem> = ArrayList()
    private var ordemIniciativa: MutableList<Personagem> = ArrayList()
    var pjsMortos = 0

    fun criarPJs() {
        personagensDosJogadores = listOf(
            Personagem("Guerreiro", 18, 23, 6, "1d8", 4, 0),
            Personagem("Mago", 12, 11, 6, "3d4", 3, 2),
            Personagem("Ladino", 16, 15, 6, "2d6", 4, 4),
            Clerigo("Clérigo", 18, 19, 6, "1d6", 2, 0, "1d8", 3)
        )
    }

    fun criarPDMs(vararg monstros: Personagem?) {
        personagensDoMestre.addAll(monstros.asList() as Collection<Personagem>)
    }

    fun rolarIniciativa() {
        for (personagem in ordemIniciativa) {
            personagem.rolarIniciativa()
        }
        ordemIniciativa = ordemIniciativa.sortedByDescending { it.iniciativa }.toMutableList()
    }

    fun PJsVencem(): Boolean {
        val personagensDosJogadores: MutableList<Personagem> = ArrayList(this.personagensDosJogadores)
        val personagensDoMestre: MutableList<Personagem> = ArrayList(this.personagensDoMestre)

        ordemIniciativa = prepararInicioCombate(personagensDosJogadores, personagensDoMestre)
        rolarIniciativa()
        val acaoService = AcaoService(personagensDosJogadores, personagensDoMestre)

        while (personagensDosJogadores.isNotEmpty() && personagensDoMestre.isNotEmpty()) {
            for (personagem in ordemIniciativa) {
                val quantidadeDeAcoes = if (SimuladorService.flagAcaoTripla) 3 else 1

                for (i in 1..quantidadeDeAcoes) {
                    acaoService.executarAcao(personagem)

                    if (houveramBaixas(personagensDoMestre) && foramTodosMortos(personagensDoMestre)) {
                        return true
                    }
                    if (houveramBaixas(personagensDosJogadores) && foramTodosMortos(personagensDosJogadores)) {
                        return false
                    }
                }
                personagem.penalidadesAcerto = 0
            }
        }
        return personagensDoMestre.isEmpty()
    }

    private fun prepararInicioCombate(
        personagensDosJogadores: List<Personagem>,
        personagensDoMestre: List<Personagem>
    ): MutableList<Personagem> {
        pjsMortos = 0
        for (personagem in personagensDosJogadores) {
            personagem.hpAtual = personagem.hpMaximo
        }
        for (personagem in personagensDoMestre) {
            personagem.hpAtual = personagem.hpMaximo
        }
        val listaDeIniciativa: MutableList<Personagem> = ArrayList(personagensDosJogadores)
        listaDeIniciativa.addAll(personagensDoMestre)
        return listaDeIniciativa
    }

    private fun houveramBaixas(personagens: MutableList<Personagem>): Boolean {
        if (!personagens[0].estaVivo()) {
            if (personagens[0] !is Monstro) {
                pjsMortos++
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
}