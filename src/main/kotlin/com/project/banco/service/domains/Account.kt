package com.project.banco.service.domains


data class Account(
    val cpf: String,
    var balance: Double = 0.0,
    val accountTransactions: List<AccountTransactions> = listOf()
) {
}