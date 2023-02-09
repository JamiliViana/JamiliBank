package com.project.banco.persistence.impl

import com.project.banco.persistence.TransactionsPersintenceAdapter
import com.project.banco.persistence.jpa.entity.AccountEntity
import com.project.banco.persistence.jpa.entity.AccountTransactionsEntity
import com.project.banco.persistence.jpa.repository.AccountJpaRepository
import com.project.banco.persistence.jpa.repository.TransactionJpaRepository
import com.project.banco.service.domains.Account
import com.project.banco.service.domains.AccountTransactions
import org.springframework.stereotype.Component
import java.util.*

@Component
class TransactionsPersistenceAdapterImpl(val transactionJpaRepository: TransactionJpaRepository, val accountJpaRepository: AccountJpaRepository) : TransactionsPersintenceAdapter{

    private fun mapContaEntity (account: Account) = AccountEntity(
        cpf = account.cpf
    )
    private fun parseEntityToDomain (accountEntity: AccountEntity) = Account(
        cpf = accountEntity.cpf
    )

    private fun mapTransactionEntity (accountTransactions: AccountTransactions) = AccountTransactionsEntity(
        account = this.mapContaEntity(accountTransactions.account),
        value = accountTransactions.balance,
        idTransactions = accountTransactions.idTransactions,
        typeOfTransaction = accountTransactions.typeOfTransaction
    )
    private fun parseTransactionEntityToDomain (accountTransactionsEntity: AccountTransactionsEntity) = AccountTransactions(
        account = this.parseEntityToDomain(accountTransactionsEntity.account),
        balance = accountTransactionsEntity.value,
        idTransactions = accountTransactionsEntity.idTransactions,
        typeOfTransaction = accountTransactionsEntity.typeOfTransaction
    )
    override fun save(accountTransactions: AccountTransactions){
        transactionJpaRepository.save(mapTransactionEntity(accountTransactions))
    }

    override fun findByAccount(cpf:String): Optional<List<AccountTransactionsEntity>> {
        return this.transactionJpaRepository.findByAccountCpf(cpf)
    }


}

