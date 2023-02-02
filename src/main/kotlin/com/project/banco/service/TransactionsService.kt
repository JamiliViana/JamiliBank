package com.project.banco.service

import com.project.banco.persistence.jpa.entity.AccountTransactionsEntity
import com.project.banco.service.domains.AccountTransactions
import java.util.*

interface TransactionsService {
    fun searchTransactions(cpf: String): Optional<List<AccountTransactionsEntity>>
    fun saveTransactions(transactions: AccountTransactions)
}