package com.project.banco.controller

import com.project.banco.controller.request.ContaRequest
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

}