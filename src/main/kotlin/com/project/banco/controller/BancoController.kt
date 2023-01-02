package com.project.banco.controller

import com.project.banco.domains.Conta
import com.project.banco.controller.request.ContaRequest
import com.project.banco.repository.jpa.entity.ContaEntity
import com.project.banco.service.BancoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/banco"])
class BancoController (val bancoService : BancoService){

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid contaRequest: ContaRequest) {
        val conta =bancoService.getContaById(contaRequest.idConta)
        this.bancoService.createAccount(contaRequest)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll()= listOf<Conta>()
}