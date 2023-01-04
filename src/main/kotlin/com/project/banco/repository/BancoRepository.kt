package com.project.banco.repository

import com.project.banco.controller.request.ContaRequest
import com.project.banco.domains.Conta
import com.project.banco.repository.jpa.entity.ContaEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BancoRepository{
    fun findById(idConta:Long): Optional<ContaEntity>
    fun deleteById(idConta: Long)
    fun findAll(): MutableList<ContaEntity>
    fun setSaldoAumentado(valor:Double, idConta: Long)
    fun setSaldoReduzido(valor:Double, idConta: Long)
    fun getSaldoById(idConta: Long)
    fun save(conta: Conta): ContaEntity
}