package com.coppolaop.service

import com.coppolaop.entity.Monstro
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

class PersonagemService {
    fun lerMonstro(nomeArquivo: String): Monstro {

        val mapper = jacksonObjectMapper()
        mapper.registerKotlinModule()
        mapper.registerModule(JavaTimeModule())

        val jsonString: String = File("./src/main/resources/pdm/${nomeArquivo}.json").readText(Charsets.UTF_8)
        return mapper.readValue<Monstro>(jsonString)
    }
}