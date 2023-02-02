package com.project.banco.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.banco.advice.exceptions.*
import com.project.banco.controller.request.TransacaoRequest
import com.project.banco.service.AccountService
import com.project.banco.service.TransactionsService
import com.project.banco.service.domains.Account
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

@AutoConfigureMockMvc
@SpringBootTest
class TransactionsControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var transactionsService: TransactionsService

    @Autowired
    lateinit var accountService: AccountService

    @Test
    fun deveRetornarOk_QuandoChamarDeposit(){

        val cpf = "123.456.789-10das"
        accountService.createAccount(Account(cpf,0.0))
        val transacaoRequest = TransacaoRequest(valor = 100.0)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/banco/deposito/{cpf}", cpf)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk)

        val retornoContaDeposito = accountService.getByCpf(cpf)
        Assertions.assertEquals(100.0, retornoContaDeposito.balance)
    }
    @Test
    fun deveLancarContaNotFoundException_QuandoChamarDeposito() {
        val cpf = "123.456.789-10c"
        val transacaoRequest = TransacaoRequest(valor = 10.0)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/banco/deposito/{cpf}", cpf)
            .param("cpf","123.456.789-10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            ?.andExpect(MockMvcResultMatchers.status().isNotFound)
            ?.andExpect { ContaNotFoundException::class.java }
    }
    @Test
    fun deveLancarValueNotAcceptedException_QuandoChamarDeposito() {

        val cpf = "123.456.789-10b"
        accountService.createAccount(Account(cpf, 0.0))
        val transacaoRequest = TransacaoRequest(valor = -100.0)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/banco/deposito/{cpf}", cpf)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(transacaoRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect { ValueNotAcceptedException::class.java }
    }
    @Test
    fun deveRetornarOk_QuandoChamarSaque(){
        val cpf = "123.456.789-10u"
        accountService.createAccount(Account(cpf,100.0))
        val transacaoRequest = TransacaoRequest(valor = 10.0)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/banco/saque/{cpf}", cpf)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk)

        val retornoContaSaque = accountService.getByCpf(cpf)
        Assertions.assertEquals(90.0, retornoContaSaque.balance)
    }
    @Test
    fun deveLancarContaNotFoundException_QuandoChamarSaque() {

        val cpf = "123.456.789-10a"
        val transacaoRequest = TransacaoRequest(valor = 10.0)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/banco/saque/{cpf}", cpf)
            .param("cpf","123.456.789-10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            ?.andExpect(MockMvcResultMatchers.status().isNotFound)
            ?.andExpect { ContaNotFoundException::class.java }
    }
    @Test
    fun deveLancarSaldoNotEnoughException_QuandoChamarSaque(){
        val cpf = "123.456.789-0000s"
        accountService.createAccount(Account(cpf,0.0))
        val transacaoRequest = TransacaoRequest(valor = 10.0)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/banco/saque/{cpf}", cpf)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            ?.andExpect{ SaldoNotEnoughException::class.java }
    }
    @Test
    fun deveLancarValueNotAcceptedException_QuandoChamarSaque(){
        val cpf = "123.456.789-0k"
        accountService.createAccount(Account(cpf,110.0))
        val transacaoRequest = TransacaoRequest(valor = -10.0)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/banco/saque/{cpf}", cpf)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            ?.andExpect{ ValueNotAcceptedException::class.java }
    }
    @Test
    fun deveRetornarOk_QuandoChamarTransferencia(){

        val cpfOrigem = "123.456.789-10j"
        val cpfDestino = "987.654.321-01g"
        accountService.createAccount(Account(cpfOrigem,100.0))
        accountService.createAccount(Account(cpfDestino,0.0))
        val transacaoRequest = TransacaoRequest(valor = 50.0)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/banco/transferencia/{cpfOrigem}/{cpfDestino}", cpfOrigem, cpfDestino)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk)

        val retornoContaOrigem = accountService.getByCpf(cpfOrigem)
        val retornoContaDestino = accountService.getByCpf(cpfDestino)
        Assertions.assertEquals(50.0, retornoContaOrigem.balance)
        Assertions.assertEquals(50.0, retornoContaDestino.balance)
    }
    @Test
    fun deveLancarContaNotFoundException_QuandoChamarTransferencia() {

        val cpfOrigem = "123.456.789-10p"
        val cpfDestino = "123.4589-10l"
        accountService.createAccount(Account(cpfDestino,0.0))
        val transacaoRequest = TransacaoRequest(valor = 10.0)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/banco/transferencia/{cpfOrigem}/{cpfDestino}", cpfOrigem, cpfDestino)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            ?.andExpect(MockMvcResultMatchers.status().isNotFound)
            ?.andExpect { ContaNotFoundException::class.java }
    }
    @Test
    fun deveRetornarSaldoNotEnough_QuandoChamarTransferencia(){

        val cpfOrigem = "123.456.789-10bd"
        val cpfDestino = "987.654.321-01cs"
        accountService.createAccount(Account(cpfOrigem,10.0))
        accountService.createAccount(Account(cpfDestino,0.0))
        val transacaoRequest = TransacaoRequest(valor = 50.0)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/banco/transferencia/{cpfOrigem}/{cpfDestino}", cpfOrigem, cpfDestino)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            ?.andExpect(ResultMatcher { SaldoNotEnoughException::class.java })
    }
    @Test
    fun deveRetornarValueNotAcceptedException_QuandoChamarTransferencia(){
        val cpfOrigem = "123.456.789-10f"
        val cpfDestino = "987.654.321-01h"
        accountService.createAccount(Account(cpfOrigem,10.0))
        accountService.createAccount(Account(cpfDestino,0.0))
        val transacaoRequest = TransacaoRequest(valor = -50.0)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/banco/transferencia/{cpfOrigem}/{cpfDestino}", cpfOrigem, cpfDestino)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            ?.andExpect{ ValueNotAcceptedException::class.java }
    }
    @Test
    fun deveRetornarContaDestinoNotFound_QuandoChamarTransferencia(){
        val cpfOrigem = "123.456.789-10z"
        val cpfDestino = "987.654.321-01y"
        accountService.createAccount(Account(cpfOrigem,100.0))
        val transacaoRequest = TransacaoRequest(valor = 50.0)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/banco/transferencia/{cpfOrigem}/{cpfDestino}", cpfOrigem, cpfDestino)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            ?.andExpect { ContaDestinoNotFoundException::class.java }
    }
    @Test
    fun deveRetornarSameAccountException_QuandoChamarTransferencia() {
        val cpfOrigem = "asdy123.456.789-10"
        val cpfDestino = "asdy123.456.789-10"
        accountService.createAccount(Account(cpfOrigem, 100.0))
        val transacaoRequest = TransacaoRequest(valor = 50.0)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/banco/transferencia/{cpfOrigem}/{cpfDestino}", cpfOrigem, cpfDestino)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(transacaoRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            ?.andExpect{ SameAccountException::class.java }
    }
}