package com.project.banco.service

import com.project.banco.domains.Conta
import com.project.banco.persistence.jpa.entity.ContaEntity
import org.springframework.stereotype.Service
import java.util.*


interface BancoService {

    fun deleteAll()
    fun createAccount(conta: Conta): ContaEntity
    fun getAll(): MutableList<ContaEntity>
    fun getByCpf(cpf:String): ContaEntity?
    fun setSaldo(cpf: String, valor:Double): ContaEntity?
}
