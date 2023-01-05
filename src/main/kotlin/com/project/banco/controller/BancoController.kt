package com.project.banco.controller

import com.project.banco.controller.request.ContaRequest
import com.project.banco.domains.Conta
import com.project.banco.repository.jpa.entity.ContaEntity
import com.project.banco.service.BancoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/banco"])
class BancoController (val bancoService : BancoService){

    private fun mapContaDomain (contaRequest: ContaRequest) = Conta(
        saldo = contaRequest.saldo!!
    )

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid contaRequest: ContaRequest) = this.bancoService.createAccount(mapContaDomain(contaRequest))

    @GetMapping("/{idConta}")
    @ResponseStatus(HttpStatus.OK)
    fun getId(@PathVariable idConta: Long) = this.bancoService.getContaById(idConta)


}