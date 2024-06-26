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
import java.io.FileNotFoundException
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
        var jsonString: String
        try {
            jsonString = File("resources/pdm/${nomeArquivo}.json").readText(Charsets.UTF_8)
        } catch (e: FileNotFoundException) {
            println("Arquivo ${nomeArquivo}.json não encontrado. Buscando arquivo interno do sistema.")
            jsonString = this.javaClass.classLoader.getResource("pdm/${nomeArquivo}.json")!!.readText(Charsets.UTF_8)
        }
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
        fun aumentarNivel(personagem: Personagem, nivelDesejado: Int) {
            val hpPorNivel = personagem.hpMaximo / 3
            personagem.hpMaximo += hpPorNivel * (nivelDesejado - 1)
            personagem.hpAtual = personagem.hpMaximo
            personagem.energiaMaxima *= nivelDesejado
            personagem.energiaAtual = personagem.energiaMaxima
            personagem.acerto += (nivelDesejado - 1) / 2
            personagem.recalcularHabilidadeDeClasse()
        }

        fun listarMonstros(isInternal: Boolean): List<String> {
            val monstros = ArrayList<String>()
            val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
            val path = if (isInternal) "src/main/resources/pdm" else "/resources/pdm"
            val resourcesPath = Paths.get(projectDirAbsolutePath, path)
            Files.walk(resourcesPath)
                .filter { item -> Files.isRegularFile(item) }
                .filter { item -> item.toString().endsWith(".json") }
                .forEach { item -> monstros.add(item.nameWithoutExtension) }
            return monstros
        }
    }
}