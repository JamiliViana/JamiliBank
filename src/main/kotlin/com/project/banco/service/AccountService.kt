package com.project.banco.service

import com.project.banco.service.domains.Account


interface AccountService {

    fun deleteAll()
    fun createAccount(account: Account): Account?
    fun getByCpf(cpf:String): Account
    fun deposit(cpf: String, value: Double): Account
    fun withdraw(cpf: String, value: Double): Account
    fun transfer(value: Double, cpfOrigem: String, cpfDestino: String): Account
}
