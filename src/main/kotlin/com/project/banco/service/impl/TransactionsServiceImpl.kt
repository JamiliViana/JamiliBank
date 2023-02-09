package com.project.banco.service.impl

import com.project.banco.advice.exceptions.*
import com.project.banco.persistence.AccountPersistenceAdapter
import com.project.banco.persistence.TransactionsPersintenceAdapter
import com.project.banco.persistence.jpa.entity.AccountTransactionsEntity
import com.project.banco.service.TransactionsService
import com.project.banco.service.domains.Account
import com.project.banco.service.domains.AccountTransactions
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionsServiceImpl(val accountPersistenceAdapter: AccountPersistenceAdapter,val transactionsPersintenceAdapter: TransactionsPersintenceAdapter): TransactionsService {

    private fun generateId(): Long {
        return UUID.randomUUID().mostSignificantBits
    }
    override fun saveTransactions(transactions: AccountTransactions) = this.transactionsPersintenceAdapter.save(transactions)

    override fun extract(cpf: String): Optional<List<AccountTransactionsEntity>> {
        return this.transactionsPersintenceAdapter.findByAccount(cpf)
    }
    override fun deposit(cpf: String, value: Double): Account {
        val getAccount = this.accountPersistenceAdapter.findByCpf(cpf) ?: throw ContaNotFoundException()
        if (value < 0){throw ValueNotAcceptedException() }

        val addDeposit = getAccount.balance + value
        getAccount.balance = addDeposit

        this.saveTransactions(AccountTransactions(generateId(),getAccount,value,"deposito"))
        return this.accountPersistenceAdapter.save(getAccount)
    }

    override fun withdraw(cpf: String, value: Double): Account {
        val getAccount = this.accountPersistenceAdapter.findByCpf(cpf) ?: throw ContaNotFoundException()
        if (value < 0){ throw ValueNotAcceptedException() }
        if (getAccount.balance < value){throw SaldoNotEnoughException() }


        val subtractBalanceWithdraw = getAccount.balance - value
        getAccount.balance = subtractBalanceWithdraw

        this.saveTransactions(AccountTransactions(generateId(),getAccount,value,"saque"))
        return this.accountPersistenceAdapter.save(getAccount)
    }

    override fun transfer(value: Double, cpfOrigem: String, cpfDestino: String): Account {
        val getAccountOrigin = this.accountPersistenceAdapter.findByCpf(cpfOrigem) ?: throw ContaNotFoundException()
        val getAccountDestination = this.accountPersistenceAdapter.findByCpf(cpfDestino) ?: throw ContaDestinoNotFoundException()
        if (value < 0){throw ValueNotAcceptedException() }
        if (getAccountOrigin.balance < value){throw SaldoNotEnoughException() }
        if (cpfDestino == cpfOrigem){throw SameAccountException() }

        val subtractBalanceAccountOrigin = getAccountOrigin.balance - value
        getAccountOrigin.balance = subtractBalanceAccountOrigin
        this.accountPersistenceAdapter.save(getAccountOrigin)

        val addBalanceAccountDestination = getAccountDestination.balance + value
        getAccountDestination.balance = addBalanceAccountDestination
        this.accountPersistenceAdapter.save(getAccountDestination)

        this.saveTransactions(AccountTransactions(generateId(),getAccountOrigin,value,"transferencia"))
        this.saveTransactions(AccountTransactions(generateId(),getAccountDestination,value,"transferencia"))

        return getAccountOrigin
    }

}