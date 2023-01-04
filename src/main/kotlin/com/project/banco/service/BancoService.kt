package com.project.banco.service

import com.project.banco.controller.request.ContaRequest
import com.project.banco.domains.Conta
import com.project.banco.repository.jpa.entity.ContaEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
interface BancoService {

    fun getContaById(idConta:Long): ContaEntity?
    fun adicionarSaldo(valor:Double, idConta: Long): ContaEntity?
    fun diminuirSaldo(valor: Double, idConta: Long): ContaEntity?
    fun transferencia(valor: Double,idContaOrigem:Long, idContaDestino: Long)
    fun getSaldoById(idConta: Long): String
    fun createAccount(conta: Conta): ContaEntity
    fun getAll(): MutableList<ContaEntity>
}
