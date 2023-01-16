package com.project.banco.service

import com.project.banco.domains.Conta


interface BancoService {

    fun deleteAll()
    fun createAccount(conta: Conta): Conta?
    fun getByCpf(cpf:String): Conta
}
