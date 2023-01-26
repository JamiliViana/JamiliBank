package com.project.banco.service

import com.project.banco.domains.Conta


interface BancoService {

    fun deleteAll()
    fun createAccount(conta: Conta): Conta?
    fun getByCpf(cpf:String): Conta
    fun deposito(cpf: String, valor:Double): Conta?
    fun saque(cpf: String,valor: Double): Conta?
    fun transferencia(valor:Double, cpfOrigem: String, cpfDestino: String): Conta?
}
