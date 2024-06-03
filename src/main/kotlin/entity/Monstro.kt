package com.coppolaop.entity

class Monstro(
    nome: String,
    ca: Int,
    hpMaximo: Int,
    energiaMaxima: Int,
    acerto: Int,
    dadoDano: String,
    modDano: Int,
    iniciativa: Int
) :
    Personagem(nome, ca, hpMaximo, energiaMaxima, acerto, dadoDano, modDano, iniciativa) {
}