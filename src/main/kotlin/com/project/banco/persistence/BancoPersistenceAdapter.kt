package com.project.banco.persistence

import com.project.banco.domains.Conta


interface BancoPersistenceAdapter{
    fun deleteAll()
    fun findByCpf(cpf: String): Conta?
    fun save(conta: Conta): Conta
}