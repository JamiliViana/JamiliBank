package com.project.banco.controller

import com.project.banco.model.Conta
import com.project.banco.service.BancoService
import com.project.banco.service.impl.BancoServiceImpl
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/banco"])
class BancoController {

    @Autowired
    lateinit var bancoService : BancoService

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid conta: Conta)= this.bancoService.createAccount(conta)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll()= listOf<Conta>()
}