package com.coppolaop.service

import com.coppolaop.entity.Monstro
import com.coppolaop.entity.Personagem
import com.coppolaop.entity.conceitos.Curandeiro

/**
 * Define direcionamentos relacionados às ações dos personagens em seus respectivos turnos
 */
class AcaoService(
    private var personagensDosJogadores: List<Personagem>,
    private val personagensDoMestre: MutableList<Personagem>,
) {
    fun executarAcao(personagem: Personagem) {
        if (!personagem.estaVivo()) {
            return
        }
        if (personagem is Monstro) {
            if (personagem is Curandeiro && personagensDoMestre[0].estaFerido()) {
                return personagem.curar(personagensDoMestre[0])
            }
            if (personagem.estaSemFoco()) {
                return personagem.focar()
            }
            return personagem.atacar(personagensDosJogadores[0])
        }
        if (personagem is Curandeiro && personagensDosJogadores[0].estaFerido()) {
            return personagem.curar(personagensDosJogadores[0])
        }
        if (personagem.estaSemFoco()) {
            return personagem.focar()
        }
        return personagem.atacar(personagensDoMestre[0])
    }
}