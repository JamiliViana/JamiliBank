package com.project.banco.service.impl

import com.project.banco.persistence.AccountPersistenceAdapter
import com.project.banco.persistence.TransactionsPersintenceAdapter
import com.project.banco.persistence.jpa.entity.AccountTransactionsEntity
import com.project.banco.service.TransactionsService
import com.project.banco.service.domains.AccountTransactions
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionsServiceImpl(val accountPersistenceAdapter: AccountPersistenceAdapter,val transactionsPersintenceAdapter: TransactionsPersintenceAdapter): TransactionsService {

    override fun saveTransactions(transactions: AccountTransactions) = this.transactionsPersintenceAdapter.save(transactions)

    override fun searchTransactions(cpf: String): Optional<List<AccountTransactionsEntity>> {
        return this.transactionsPersintenceAdapter.findByAccount(cpf)
    }

}