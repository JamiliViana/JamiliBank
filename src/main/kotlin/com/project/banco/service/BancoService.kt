package com.project.banco.service

import com.project.banco.model.Conta

interface BancoService {

    fun createAccount(conta: Conta): Conta?
    fun getContaById(idConta:Long): Conta?
    fun adicionarSaldo(valor:Double, idConta: Long): Conta?
    fun diminuirSaldo(valor: Double, idConta: Long): Conta?
    fun transferencia(valor: Double,idContaOrigem:Long, idContaDestino: Long)
    fun getSaldoById(idConta: Long): String
}
