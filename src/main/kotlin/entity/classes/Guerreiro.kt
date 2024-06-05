package com.coppolaop.entity.classes

import com.coppolaop.entity.Personagem
import com.coppolaop.service.SimuladorService

class Guerreiro(
    nome: String,
    ca: Int,
    hpMaximo: Int,
    acerto: Int,
    dadoDano: String,
    modDano: Int,
    iniciativa: Int,
) : Personagem(
    nome,
    ca,
    hpMaximo,
    3,
    acerto,
    dadoDano,
    modDano,
    iniciativa,
) {
    var incrementoAcerto = 4

    override fun recalcularHabilidadeDeClasse() {
        incrementoAcerto = (((SimuladorService.nivelDesejado - 1) / 4) * 4) + 4
    }

    override fun atacar(defensor: Personagem) {
        if (energiaAtual > 0 && penalidadesAcerto == 0) {
            energiaAtual--
            acerto += incrementoAcerto
            super.atacar(defensor)
            acerto -= incrementoAcerto
            return
        }
        super.atacar(defensor)
    }
}