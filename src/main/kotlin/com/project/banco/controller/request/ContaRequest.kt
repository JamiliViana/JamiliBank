package com.project.banco.controller.request

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ContaRequest(
    val idConta:Long =1,
    @field:NotNull(message = "Escrever o saldo é obrigatório")
    var saldo:Double? = null
)  {
}