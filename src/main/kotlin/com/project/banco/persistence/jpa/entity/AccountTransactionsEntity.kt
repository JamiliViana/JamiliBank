package com.project.banco.persistence.jpa.entity

import com.project.banco.emptyConstructorOfDataCls.NoArg
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
@NoArg
data class AccountTransactionsEntity(
    @Id
    val idTransactions :Long,
    @ManyToOne
    @JoinColumn(name = "account_cpf")
    val account: AccountEntity,
    val value:Double,
    val typeOfTransaction:String
) {
}