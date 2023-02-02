package com.project.banco.service.impl

import com.project.banco.advice.exceptions.ContaAlreadyExistsException
import com.project.banco.advice.exceptions.ContaNotFoundException
import com.project.banco.persistence.AccountPersistenceAdapter
import com.project.banco.service.AccountService
import com.project.banco.service.TransactionsService
import com.project.banco.service.domains.Account
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(val accountPersistenceAdapter: AccountPersistenceAdapter, val transactionsService: TransactionsService) : AccountService {

    override fun deleteAll() {
        accountPersistenceAdapter.deleteAll()
    }

    override fun createAccount(account: Account): Account? {
        val verifyAccountExists = this.accountPersistenceAdapter.findByCpf(account.cpf)
        if (verifyAccountExists == null) {
            return this.accountPersistenceAdapter.save(account)
        }else throw ContaAlreadyExistsException()
    }

    override fun getByCpf(cpf: String): Account {
        val verifyAccountExists = this.accountPersistenceAdapter.findByCpf(cpf) ?: throw ContaNotFoundException()
        return verifyAccountExists
    }






}