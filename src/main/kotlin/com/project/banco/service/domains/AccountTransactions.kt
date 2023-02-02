package com.project.banco.service.domains

data class AccountTransactions(
    val idTransactions : Long,
    val account: Account,
    val balance:Double,
    val typeOfTransaction:String) {
}