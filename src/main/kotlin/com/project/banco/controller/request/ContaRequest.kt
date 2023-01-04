package com.project.banco.controller.request

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.constraints.NotBlank

data class ContaRequest(
    val idConta:Long =1,
    var saldo:Double
)  {
}