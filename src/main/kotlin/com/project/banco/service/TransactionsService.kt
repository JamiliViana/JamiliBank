package com.project.banco.service

import com.project.banco.persistence.jpa.entity.AccountTransactionsEntity
import com.project.banco.service.domains.Account
import com.project.banco.service.domains.AccountTransactions
import java.util.*

interface TransactionsService {
    fun extract(cpf: String): Optional<List<AccountTransactionsEntity>>
    fun saveTransactions(transactions: AccountTransactions)
    fun deposit(cpf: String, value: Double): Account
    fun withdraw(cpf: String, value: Double): Account
    fun transfer(value: Double, cpfOrigem: String, cpfDestino: String): Account
}