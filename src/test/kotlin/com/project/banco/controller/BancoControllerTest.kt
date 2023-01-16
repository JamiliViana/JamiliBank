package com.project.banco.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.banco.controller.request.ContaRequest
import com.project.banco.domains.Conta
import com.project.banco.service.BancoService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.bind.MethodArgumentNotValidException

@AutoConfigureMockMvc
@SpringBootTest
class BancoControllerTest{

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var service: BancoService

    @Test
    fun deveRetornarCreated_QuandoChamarCreate() {
        val conta = ContaRequest (
            "1234", 10.0
        )
        mockMvc!!.perform(MockMvcRequestBuilders.post("/banco")
            .contentType("application/json")
            .content(objectMapper!!.writeValueAsString(conta)))
            ?.andExpect(MockMvcResultMatchers.status().isCreated)

        var buscaConta = service?.getByCpf(conta.cpf!!)
        Assertions.assertNotNull(buscaConta)
    }
    @Test
    fun deveRetornarOK_QuandoChamarGetByCpf() {
        service?.deleteAll()
        val conta = service?.createAccount(
            Conta(
                "12345",
                0.1)
        )
        mockMvc?.perform(
            MockMvcRequestBuilders.get("/banco/cpf/")
            .accept(MediaType.APPLICATION_JSON)
            .param("cpf",conta!!.cpf))
            ?.andExpect(MockMvcResultMatchers.status().isOk)

        var buscaConta = service?.getByCpf(conta!!.cpf!!)
        Assertions.assertEquals(conta,buscaConta)
    }
    @Test
    fun deveRetornarNotFound_QuandoChamarGetByCpf() {
        service?.deleteAll()
        mockMvc?.perform(
            MockMvcRequestBuilders.get("/banco/cpf/")
            .param("cpf","1"))
            ?.andExpect(MockMvcResultMatchers.status().isNotFound)
    }
    @Test
    fun deveRetornarException_QuandoChamarCreate() {
        service?.deleteAll()
        var conta = ContaRequest(null,null)

        mockMvc?.perform(
            MockMvcRequestBuilders.get("/banco/cpf/")
                .accept(MediaType.APPLICATION_JSON)
                .param("cpf",conta!!.cpf))
            ?.andExpect(MockMvcResultMatchers.status().isBadRequest)
            ?.andExpect(ResultMatcher { MethodArgumentNotValidException::class.java })

    }



}