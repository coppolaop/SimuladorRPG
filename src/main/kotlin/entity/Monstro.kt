package com.coppolaop.entity

class Monstro(
    nome: String,
    ca: Int, hpMaximo: Int,
    acerto: Int,
    dadoDano: String,
    modDano: Int,
    iniciativa: Int
) :
    Personagem(nome, ca, hpMaximo, acerto, dadoDano, modDano, iniciativa) {
}