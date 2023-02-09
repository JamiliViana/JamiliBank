package com.project.banco.controller

import com.project.banco.controller.request.TransacaoRequest
import com.project.banco.service.TransactionsService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/banco"])
class TransactionsController (val transactionsService: TransactionsService){

    @GetMapping("/extrato/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    fun getExtract(@PathVariable cpf: String) = this.transactionsService.extract(cpf)

    @PutMapping("/deposito/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    fun deposit(@PathVariable cpf: String, @RequestBody @Valid transacaoRequest: TransacaoRequest) = this.transactionsService.deposit(cpf,
        transacaoRequest.valor!!
    )

    @PutMapping("/saque/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    fun withdraw(@PathVariable cpf: String, @RequestBody @Valid transacaoRequest: TransacaoRequest) = this.transactionsService.withdraw(cpf,
        transacaoRequest.valor!!
    )

    @PutMapping("/transferencia/{cpfOrigem}/{cpfDestino}")
    @ResponseStatus(HttpStatus.OK)
    fun transfer(@PathVariable cpfOrigem: String, @PathVariable cpfDestino: String, @RequestBody @Valid transacaoRequest: TransacaoRequest) =
        this.transactionsService.transfer(transacaoRequest.valor!!, cpfOrigem, cpfDestino)
}