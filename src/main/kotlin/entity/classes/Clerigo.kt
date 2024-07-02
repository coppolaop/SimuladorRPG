package com.coppolaop.entity.classes

import com.coppolaop.entity.Personagem
import com.coppolaop.entity.conceitos.Curandeiro
import com.coppolaop.service.SimuladorService

class Clerigo(
    nome: String,
    ca: Int,
    hpMaximo: Int,
    energiaMaxima: Int,
    acerto: Int,
    dadoDano: String,
    modDano: Int,
    iniciativa: Int,
    override var dadoCura: String,
    override var modCura: Int,
) : Personagem(
    nome,
    ca,
    hpMaximo,
    energiaMaxima,
    acerto,
    dadoDano,
    modDano,
    iniciativa,
), Curandeiro {
    override fun recalcularHabilidadeDeClasse() {
        dadoCura = "${(SimuladorService.nivelDesejado + 1) / 2}d8"
    }
}