package com.project.banco.persistence.impl

import com.project.banco.persistence.AccountPersistenceAdapter
import com.project.banco.persistence.jpa.entity.AccountEntity
import com.project.banco.persistence.jpa.repository.AccountJpaRepository
import com.project.banco.service.domains.Account
import org.springframework.stereotype.Component


@Component
class AccountPersistenceAdapterImpl(val accountJpaRepository: AccountJpaRepository): AccountPersistenceAdapter {

    private fun mapContaEntity (account: Account) = AccountEntity(
        cpf = account.cpf,
        balance = account.balance
    )
    private fun parseEntityToDomain (accountEntity: AccountEntity) = Account(
        cpf = accountEntity.cpf,
        balance = accountEntity.balance
    )
    override fun save(account: Account): Account {
        var saveEntity =  accountJpaRepository.save(mapContaEntity(account))
        return parseEntityToDomain(saveEntity)
    }

    override fun deleteAll() {
        accountJpaRepository.deleteAll()
    }
    override fun findByCpf(cpf: String): Account? {
        var findEntity = accountJpaRepository.findById(cpf).orElse(null)
        return if (findEntity != null) {parseEntityToDomain(findEntity)} else null
    }

}