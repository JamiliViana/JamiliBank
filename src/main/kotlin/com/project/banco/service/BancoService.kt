package com.project.banco.service

import com.project.banco.domains.Conta
import com.project.banco.persistence.jpa.entity.ContaEntity
import org.springframework.stereotype.Service
import java.util.*


interface BancoService {

    fun deleteAll()
    fun createAccount(conta: Conta): Conta?
    fun getByCpf(cpf:String): Conta
}
