package com.project.banco.persistence

import com.project.banco.service.domains.Account


interface AccountPersistenceAdapter{
    fun deleteAll()
    fun findByCpf(cpf: String): Account?
    fun save(account: Account): Account
}