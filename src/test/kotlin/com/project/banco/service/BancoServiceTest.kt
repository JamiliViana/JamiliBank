package com.project.banco.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.banco.controller.request.ContaRequest
import com.project.banco.domains.Cliente
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
class BancoServiceTest () {

    @Autowired
    private val mockMvc: MockMvc? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @Autowired
    private val service: BancoService ? = null

    @org.junit.jupiter.api.Test
    @Throws(Exception::class)
    fun deveRetornarCreated_QuandoChamarCreate() {

        val conta = ContaRequest (
            1,
            0.1,
            cliente = Cliente("2123","Jamili")
        )
        val resultado = mockMvc?.perform(
            MockMvcRequestBuilders.post("/bank")
            .contentType("application/json")
            .content(objectMapper!!.writeValueAsString(conta)))
            ?.andExpect(MockMvcResultMatchers.status().isCreated)
            ?.andReturn()?.response?.contentAsString
        val contaResultado = objectMapper?.readValue(resultado,ContaRequest::class.java)


        val contaReturn = service?.getContaById(contaResultado!!.idConta)
        Assertions.assertEquals(conta.saldo,contaReturn?.saldo)
        Assertions.assertEquals(conta.cliente.cpf,contaReturn?.clienteEntity?.cpf)
        Assertions.assertEquals(conta.cliente.nome, contaReturn?.clienteEntity?.nome)
    }
//
//    @Test
//    fun getContaById(){
//
//        var retorno = bancoService.getContaById(2)
//    }

//    @Test
//    fun adicionarSaldo(){
//        bancoService.adicionarSaldo(200.0,2)
//        var recebeSaldoConta = bancoService.getContaById(2)
//    }
//
//    @Test
//    fun diminuirSaldo(){
//        bancoService.diminuirSaldo(200.0,2)
//        var recebeSaldoConta = bancoService.getContaById(2)
//    }
//
//    @Test
//    fun transferencia(){
//        bancoService.createAccount(
//            ContaRequest(3,
//                0.0,
//                Cliente(
//                    "ContaDestino",
//                    "12324"))
//        )
//        bancoService.transferencia(1000.0,2,3)
//        var recebeSaldoContaOrigem = bancoService.getContaById(2)
//        var recebeSaldoContaDestino = bancoService.getContaById(3)
//    }
//
//    @Test
//    fun getSaldoById(){
//        val pegarSaldo = bancoService.getSaldoById(3)
//        Assertions.assertEquals("Seu saldo é 400.0",pegarSaldo)
//    }

}