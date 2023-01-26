package com.project.banco.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.banco.controller.request.ContaRequest
import com.project.banco.controller.request.TransacaoRequest
import com.project.banco.domains.Conta
import com.project.banco.exceptions.ContaDestinoNotFoundException
import com.project.banco.exceptions.ContaNotFoundException
import com.project.banco.exceptions.SaldoNotEnoughException
import com.project.banco.exceptions.ValueNotAcceptedException
import com.project.banco.exceptions.SameAccountException
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
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


    @Test
    fun deveRetornarOk_QuandoChamarDeposit(){
        service.deleteAll()
        val cpf = "123.456.789-10"
        service.createAccount(Conta("123.456.789-10",0.0))
        val transacaoRequest = TransacaoRequest(valor = 100.0)

        mockMvc.perform(put("/banco/deposito/{cpf}", cpf)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk)

        val retornoContaDeposito = service.getByCpf("123.456.789-10")
        Assertions.assertEquals(100.0, retornoContaDeposito.saldo)
    }
    @Test
    fun deveLancarContaNotFoundException_QuandoChamarDeposito() {
        service?.deleteAll()
        val cpf = "123.456.789-10"
        val transacaoRequest = TransacaoRequest(valor = 10.0)

        mockMvc.perform(put("/banco/deposito/{cpf}", cpf)
            .param("cpf","123.456.789-10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            ?.andExpect(MockMvcResultMatchers.status().isNotFound)
            ?.andExpect { ContaNotFoundException::class.java }
    }
    @Test
    fun deveLancarValueNotAcceptedException_QuandoChamarDeposito() {
        service.deleteAll()
        val cpf = "123.456.789-10"
        service.createAccount(Conta("123.456.789-10", 0.0))
        val transacaoRequest = TransacaoRequest(valor = -100.0)

        mockMvc.perform(
            put("/banco/deposito/{cpf}", cpf)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(transacaoRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect { ValueNotAcceptedException::class.java }
    }


    @Test
    fun deveRetornarOk_QuandoChamarSaque(){
        val cpf = "123.456.789-10"
        service.createAccount(Conta(cpf,100.0))
        val transacaoRequest = TransacaoRequest(valor = 10.0)

        mockMvc.perform(put("/banco/saque/{cpf}", cpf)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk)

        val retornoContaSaque = service.getByCpf(cpf)
        Assertions.assertEquals(90.0, retornoContaSaque.saldo)
    }
    @Test
    fun deveLancarContaNotFoundException_QuandoChamarSaque() {
        service?.deleteAll()
        val cpf = "123.456.789-10"
        val transacaoRequest = TransacaoRequest(valor = 10.0)

        mockMvc.perform(put("/banco/saque/{cpf}", cpf)
            .param("cpf","123.456.789-10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            ?.andExpect(MockMvcResultMatchers.status().isNotFound)
            ?.andExpect { ContaNotFoundException::class.java }
    }
    @Test
    fun deveLancarSaldoNotEnoughException_QuandoChamarSaque(){
        val cpf = "123.456.789-0"
        service.createAccount(Conta(cpf,0.0))
        val transacaoRequest = TransacaoRequest(valor = 10.0)

        mockMvc.perform(put("/banco/saque/{cpf}", cpf)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            ?.andExpect{ SaldoNotEnoughException::class.java }
    }
    @Test
    fun deveLancarValueNotAcceptedException_QuandoChamarSaque(){
        val cpf = "123.456.789-0"
        service.createAccount(Conta(cpf,110.0))
        val transacaoRequest = TransacaoRequest(valor = -10.0)

        mockMvc.perform(put("/banco/saque/{cpf}", cpf)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            ?.andExpect{ ValueNotAcceptedException::class.java }
    }



    @Test
    fun deveRetornarOk_QuandoChamarTransferencia(){
        service.deleteAll()
        val cpfOrigem = "123.456.789-10"
        val cpfDestino = "987.654.321-01"
        service.createAccount(Conta(cpfOrigem,100.0))
        service.createAccount(Conta(cpfDestino,0.0))
        val transacaoRequest = TransacaoRequest(valor = 50.0)

        mockMvc.perform(put("/banco/transferencia/{cpfOrigem}/{cpfDestino}", cpfOrigem, cpfDestino)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk)

        val retornoContaOrigem = service.getByCpf(cpfOrigem)
        val retornoContaDestino = service.getByCpf(cpfDestino)
        Assertions.assertEquals(50.0, retornoContaOrigem.saldo)
        Assertions.assertEquals(50.0, retornoContaDestino.saldo)
    }
    @Test
    fun deveLancarContaNotFoundException_QuandoChamarTransferencia() {
        service?.deleteAll()
        val cpfOrigem = "123.456.789-10"
        val cpfDestino = "123.4589-10"
        service.createAccount(Conta(cpfDestino,0.0))
        val transacaoRequest = TransacaoRequest(valor = 10.0)

        mockMvc.perform(put("/banco/transferencia/{cpfOrigem}/{cpfDestino}", cpfOrigem, cpfDestino)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            ?.andExpect(MockMvcResultMatchers.status().isNotFound)
            ?.andExpect { ContaNotFoundException::class.java }
    }
    @Test
    fun deveRetornarSaldoNotEnough_QuandoChamarTransferencia(){
        service.deleteAll()
        val cpfOrigem = "123.456.789-10"
        val cpfDestino = "987.654.321-01"
        service.createAccount(Conta(cpfOrigem,10.0))
        service.createAccount(Conta(cpfDestino,0.0))
        val transacaoRequest = TransacaoRequest(valor = 50.0)

        mockMvc.perform(put("/banco/transferencia/{cpfOrigem}/{cpfDestino}", cpfOrigem, cpfDestino)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            ?.andExpect(ResultMatcher { SaldoNotEnoughException::class.java })
    }
    @Test
    fun deveRetornarValueNotAcceptedException_QuandoChamarTransferencia(){
        service.deleteAll()
        val cpfOrigem = "123.456.789-10"
        val cpfDestino = "987.654.321-01"
        service.createAccount(Conta(cpfOrigem,10.0))
        service.createAccount(Conta(cpfDestino,0.0))
        val transacaoRequest = TransacaoRequest(valor = -50.0)

        mockMvc.perform(put("/banco/transferencia/{cpfOrigem}/{cpfDestino}", cpfOrigem, cpfDestino)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            ?.andExpect{ ValueNotAcceptedException::class.java }
    }
    @Test
    fun deveRetornarContaDestinoNotFound_QuandoChamarTransferencia(){
        service.deleteAll()
        val cpfOrigem = "123.456.789-10"
        val cpfDestino = "987.654.321-01"
        service.createAccount(Conta(cpfOrigem,100.0))
        val transacaoRequest = TransacaoRequest(valor = 50.0)

        mockMvc.perform(put("/banco/transferencia/{cpfOrigem}/{cpfDestino}", cpfOrigem, cpfDestino)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            ?.andExpect { ContaDestinoNotFoundException::class.java }
    }
    @Test
    fun deveRetornarSameAccountException_QuandoChamarTransferencia() {
        service.deleteAll()
        val cpfOrigem = "123.456.789-10"
        val cpfDestino = "123.456.789-10"
        service.createAccount(Conta(cpfOrigem, 100.0))
        val transacaoRequest = TransacaoRequest(valor = 50.0)

        mockMvc.perform(
            put("/banco/transferencia/{cpfOrigem}/{cpfDestino}", cpfOrigem, cpfDestino)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            ?.andExpect{ SameAccountException::class.java }
    }
}