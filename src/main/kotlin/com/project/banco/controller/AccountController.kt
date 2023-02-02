package com.project.banco.controller

import com.project.banco.controller.request.ContaRequest
import com.project.banco.controller.request.TransacaoRequest
import com.project.banco.service.AccountService
import com.project.banco.service.domains.Account
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/banco"])
class AccountController (val accountService : AccountService){

    private fun mapContaDomain (contaRequest: ContaRequest) = Account(
        cpf = contaRequest.cpf!!
    )

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid contaRequest: ContaRequest) = this.accountService.createAccount(mapContaDomain(contaRequest))

    @GetMapping("/cpf/")
    @ResponseStatus(HttpStatus.OK)
    fun getByCpf(@RequestParam(required = true) cpf: String) = this.accountService.getByCpf(cpf)

    @PutMapping("/deposito/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    fun deposit(@PathVariable cpf: String, @RequestBody @Valid transacaoRequest: TransacaoRequest) = this.accountService.deposit(cpf,
        transacaoRequest.valor!!
    )

    @PutMapping("/saque/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    fun withdraw(@PathVariable cpf: String, @RequestBody @Valid transacaoRequest: TransacaoRequest) = this.accountService.withdraw(cpf,
        transacaoRequest.valor!!
    )

    @PutMapping("/transferencia/{cpfOrigem}/{cpfDestino}")
    @ResponseStatus(HttpStatus.OK)
    fun transfer(@PathVariable cpfOrigem: String, @PathVariable cpfDestino: String, @RequestBody @Valid transacaoRequest: TransacaoRequest) =
        this.accountService.transfer(transacaoRequest.valor!!, cpfOrigem, cpfDestino)
}