package com.project.banco.controller.request


import com.project.banco.service.domains.AccountTransactions
import jakarta.validation.constraints.NotBlank

data class ContaRequest(
    @field:NotBlank(message = "Escrever o cpf é obrigatório")
    val cpf: String? = null,
    var saldo: Double? = 0.0,
    val accountTransactions: List<AccountTransactions>? = null,
)  {
}