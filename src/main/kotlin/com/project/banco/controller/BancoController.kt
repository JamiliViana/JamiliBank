package com.project.banco.controller

import com.project.banco.controller.request.ContaRequest
import com.project.banco.controller.request.TransacaoRequest
import com.project.banco.domains.Conta
import com.project.banco.service.BancoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@RestController
@RequestMapping(value = ["/banco"])
class BancoController (val bancoService : BancoService){

    private fun mapContaDomain (contaRequest: ContaRequest) = Conta(
        cpf = contaRequest.cpf!!
    )

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid contaRequest: ContaRequest) = this.bancoService.createAccount(mapContaDomain(contaRequest))

    @GetMapping("/cpf/")
    @ResponseStatus(HttpStatus.OK)
    fun getByCpf(@RequestParam(required = true) cpf: String) = this.bancoService.getByCpf(cpf)

    @PutMapping("/deposito/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    fun deposit(@PathVariable cpf: String, @RequestBody transacaoRequest: TransacaoRequest) = this.bancoService.deposito(cpf,transacaoRequest.valor)

    @PutMapping("/saque/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    fun withdraw(@PathVariable cpf: String, @RequestBody transacaoRequest: TransacaoRequest) = this.bancoService.saque(cpf,transacaoRequest.valor)

    @PutMapping("/transferencia/{cpfOrigem}/{cpfDestino}")
    @ResponseStatus(HttpStatus.OK)
    fun transfer(@PathVariable cpfOrigem: String, @PathVariable cpfDestino: String, @RequestBody transacaoRequest: TransacaoRequest) =
        this.bancoService.transferencia(transacaoRequest.valor, cpfOrigem, cpfDestino)
}