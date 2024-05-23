package com.coppolaop.service

import com.coppolaop.entity.Monstro
import com.coppolaop.entity.Personagem
import com.coppolaop.entity.classes.Clerigo
import com.coppolaop.entity.conceitos.Curandeiro

class CombateService {
    private val personagensDosJogadores: MutableList<Personagem> = ArrayList()
    private val personagensDoMestre: MutableList<Personagem> = ArrayList()
    private var ordemIniciativa: MutableList<Personagem> = ArrayList()

    fun criarPJs() {
        val guerreiro = Personagem("Guerreiro", 18, 23, 6, "1d8", 4, 0)
        val mago = Personagem("Mago", 12, 11, 6, "3d4", 3, 2)
        val ladino = Personagem("Ladino", 16, 15, 6, "2d6", 4, 4)
        val clerigo: Personagem = Clerigo("Clérigo", 18, 19, 6, "1d6", 2, 0, "1d8", 3)
        personagensDosJogadores.add(guerreiro)
        personagensDosJogadores.add(mago)
        personagensDosJogadores.add(ladino)
        personagensDosJogadores.add(clerigo)
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

    fun porcentagemDeVitoriaDePJ(amostragem: Int): Double {
        var porcentagemVitoria = 0.0
        for (i in 0..<amostragem) {
            if (PJsVencem()) {
                porcentagemVitoria++
            }
        }
        return porcentagemVitoria / (amostragem / 100)
    }

    private fun PJsVencem(): Boolean {
        val personagensDosJogadores: MutableList<Personagem> = ArrayList(this.personagensDosJogadores)
        val personagensDoMestre: MutableList<Personagem> = ArrayList(this.personagensDoMestre)

        ordemIniciativa = prepararInicioCombate(personagensDosJogadores, personagensDoMestre)
        rolarIniciativa()

        while (personagensDosJogadores.isNotEmpty() && personagensDoMestre.isNotEmpty()) {
            for (personagem in ordemIniciativa) {
                if (!personagem.estaVivo()) {
                    continue
                }
                if (personagem is Curandeiro && personagensDosJogadores[0].estaFerido()
                ) {
                    personagem.curar(personagensDosJogadores[0])
                } else {
                    if (personagem is Monstro) {
                        personagem.atacar(personagensDosJogadores[0])
                        if (isCombateFinalizado(personagensDosJogadores)) {
                            return false
                        }
                        continue
                    }
                    personagem.atacar(personagensDoMestre[0])
                    if (isCombateFinalizado(personagensDoMestre)) {
                        return true
                    }
                }
            }
        }
        return personagensDoMestre.isEmpty()
    }

    private fun prepararInicioCombate(
        personagensDosJogadores: List<Personagem>,
        personagensDoMestre: List<Personagem>
    ): MutableList<Personagem> {
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

    private fun isCombateFinalizado(personagens: MutableList<Personagem>): Boolean {
        if (!personagens[0].estaVivo()) {
            personagens.removeAt(0)
            if (personagens.isEmpty()) {
                return true
            }
        }
        return false
    }
}