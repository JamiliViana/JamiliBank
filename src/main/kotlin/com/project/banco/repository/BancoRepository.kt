package com.project.banco.repository

import com.project.banco.domains.Conta
import com.project.banco.repository.jpa.entity.ContaEntity
import org.springframework.stereotype.Repository

@Repository
interface BancoRepository{
    fun save(conta: Conta )
    fun findById(idConta:Long)
    fun deleteById(idConta: Long)
    fun findAll()
    fun setSaldoAumentado(valor:Double, idConta: Long)
    fun setSaldoReduzido(valor:Double, idConta: Long)
    fun getSaldoById(idConta: Long)

}