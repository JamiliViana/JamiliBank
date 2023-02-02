package com.project.banco.persistence.jpa.entity

import com.project.banco.emptyConstructorOfDataCls.NoArg
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
@NoArg
data class AccountEntity(
    @Id
    val cpf: String,
    val balance: Double = 0.0,
    @OneToMany
    val accountTransactions: List<AccountTransactionsEntity> = listOf()
)

