package com.project.banco.controller

import com.project.banco.service.TransactionsService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/banco"])
class TransactionsController (val transactionsService: TransactionsService){

    @GetMapping("/extrato/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    fun getExtract(@PathVariable cpf: String) = this.transactionsService.searchTransactions(cpf)
}