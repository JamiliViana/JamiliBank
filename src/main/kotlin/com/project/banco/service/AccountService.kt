package com.project.banco.service

import com.project.banco.service.domains.Account


interface AccountService {

    fun deleteAll()
    fun createAccount(account: Account): Account?
    fun getByCpf(cpf:String): Account

}
