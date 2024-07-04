package com.coppolaop.service

import com.coppolaop.entity.Monstro
import com.coppolaop.entity.Personagem
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
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

    companion object {
        val pdmDirectory = "resources/pdm/"

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

            if (monstros.isEmpty()) monstros.add(criarPersonagemBasico())

            return monstros
        }

        private fun criarPersonagemBasico(): String {
            val monstro = Monstro("Gnoll", 15, 15, 0, 10, "1d6", 4, 1)
            val path = "${pdmDirectory}${monstro.nome.lowercase()}.json"
            try {
                val mapper = ObjectMapper()
                mapper.writerWithDefaultPrettyPrinter().writeValue(File(path), monstro)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return monstro.nome.lowercase()
        }
    }
}