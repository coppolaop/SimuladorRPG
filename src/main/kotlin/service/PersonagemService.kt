package com.coppolaop.service

import com.coppolaop.entity.Monstro
import com.coppolaop.entity.Personagem
import com.coppolaop.entity.classes.Clerigo
import com.coppolaop.entity.classes.Guerreiro
import com.coppolaop.entity.classes.Ladino
import com.coppolaop.entity.classes.Mago
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.nameWithoutExtension

/**
 * Reúne funcionalidades para tratamento de personagens,
 * desde a criação a partir de arquivo até tratativas internas do jogo
 */
class PersonagemService {
    fun lerMonstro(nomeArquivo: String): Monstro {
        val mapper = getMapper()
        val jsonString: String
        jsonString = File("$pdmDirectory${nomeArquivo}.json").readText(Charsets.UTF_8)
        return mapper.readValue<Monstro>(jsonString)
    }

    private fun getMapper(): ObjectMapper {
        val mapper = jacksonObjectMapper()
        mapper.registerKotlinModule()
        mapper.registerModule(JavaTimeModule())
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return mapper
    }

    fun lerAventureiro(personagem: Personagem): Personagem {
        val mapper = getMapper()
        val jsonString: String
        jsonString = File("$pjDirectory${personagem.nome.lowercase()}.json").readText(Charsets.UTF_8)
        return mapper.readValue<Personagem>(jsonString)
    }

    fun carregarAventureiros(): List<Personagem> {
        val personagensBasicos = mutableListOf(
            Guerreiro("Guerreiro", 18, 21, 6, "1d8", 4, 0),
            Mago("Mago", 12, 12, 10, 6, "1d6", 3, 2),
            Ladino("Ladino", 16, 15, 6, "1d4", 4, 4),
            Clerigo("Clerigo", 18, 18, 8, 6, "1d6", 2, 0, "1d8", 3)
        )
        val personagens = mutableListOf<Personagem>()

        for (personagemBasico in personagensBasicos) {
            val personagemArquivo: Personagem

            try {
                personagemArquivo = lerAventureiro(personagemBasico)
            } catch (e: FileNotFoundException) {
                personagens.add(personagemBasico)
                criarAventureiroBasico(personagemBasico)
                continue
            }
            personagens.add(personagemArquivo)
        }

        return personagens
    }

    private fun criarAventureiroBasico(aventureiro: Personagem) {
        val caminho = "${pjDirectory}${aventureiro.nome.lowercase()}.json"
        criarArquivoPersonagem(aventureiro, caminho)
        println("Arquivo criado: $caminho")
    }

    companion object {
        val pdmDirectory = "resources/pdm/"
        val pjDirectory = "resources/pj/"

        fun aumentarNivel(personagem: Personagem, nivelDesejado: Int) {
            val hpPorNivel = personagem.hpMaximo / 3
            personagem.hpMaximo += hpPorNivel * (nivelDesejado - 1)
            personagem.hpAtual = personagem.hpMaximo
            personagem.energiaMaxima *= nivelDesejado
            personagem.energiaAtual = personagem.energiaMaxima
            personagem.acerto += (nivelDesejado - 1) / 2
            personagem.recalcularHabilidadeDeClasse()
        }

        fun listarMonstros(): List<String> {
            val monstros = ArrayList<String>()
            val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
            val resourcesPath = Paths.get(projectDirAbsolutePath, pdmDirectory)
            Files.walk(resourcesPath)
                .filter { item -> Files.isRegularFile(item) }
                .filter { item -> item.toString().endsWith(".json") }
                .forEach { item -> monstros.add(item.nameWithoutExtension) }

            if (monstros.isEmpty()) monstros.add(criarMonstroBasico())

            return monstros
        }

        private fun criarMonstroBasico(): String {
            val monstro = Monstro("Gnoll", 15, 15, 0, 10, "1d6", 4, 1)
            val caminho = "${pdmDirectory}${monstro.nome.lowercase()}.json"
            return criarArquivoPersonagem(monstro, caminho)
        }

        private fun criarArquivoPersonagem(personagem: Personagem, caminho: String): String {
            try {
                val mapper = ObjectMapper()
                mapper.writerWithDefaultPrettyPrinter().writeValue(File(caminho), personagem)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return personagem.nome.lowercase()
        }
    }
}