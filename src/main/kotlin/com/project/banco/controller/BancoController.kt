package com.project.banco.controller

import com.project.banco.controller.request.ContaRequest
import com.project.banco.domains.Conta
import com.project.banco.service.BancoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/banco"])
class BancoController (val bancoService : BancoService){

    private fun mapContaDomain (contaRequest: ContaRequest) = Conta(
        cpf = contaRequest.cpf!!,
        saldo = contaRequest.saldo!!
    )

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid contaRequest: ContaRequest) = this.bancoService.createAccount(mapContaDomain(contaRequest))

    @GetMapping("/cpf/")
    @ResponseStatus(HttpStatus.OK)
    fun getByCpf(@RequestParam(required = true) cpf: String) = this.bancoService.getByCpf(cpf)

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    fun getAll() = this.bancoService.getAll()

}