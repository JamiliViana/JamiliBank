package com.project.banco.controller.request


import jakarta.validation.constraints.NotBlank

data class ContaRequest(
    @field:NotBlank(message = "Escrever o cpf é obrigatório")
    val cpf: String? = null,
    var saldo:Double
)  {
}