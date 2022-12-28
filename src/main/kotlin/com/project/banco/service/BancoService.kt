package com.project.banco.service

import com.project.banco.domains.Conta
import org.springframework.stereotype.Service

@Service
interface BancoService {

    fun createAccount(conta: Conta)
    fun getContaById(idConta:Long)
    fun adicionarSaldo(valor:Double, idConta: Long): Conta?
    fun diminuirSaldo(valor: Double, idConta: Long): Conta?
    fun transferencia(valor: Double,idContaOrigem:Long, idContaDestino: Long)
    fun getSaldoById(idConta: Long): String
}
