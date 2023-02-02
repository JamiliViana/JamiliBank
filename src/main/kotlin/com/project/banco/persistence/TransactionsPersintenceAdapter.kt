package com.project.banco.persistence

import com.project.banco.persistence.jpa.entity.AccountTransactionsEntity
import com.project.banco.service.domains.AccountTransactions
import java.util.*

interface TransactionsPersintenceAdapter {
    fun save(accountTransactions: AccountTransactions)
    fun findByAccount(cpf: String): Optional<List<AccountTransactionsEntity>>


}