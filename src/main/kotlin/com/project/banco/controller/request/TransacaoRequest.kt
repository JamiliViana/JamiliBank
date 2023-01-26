package com.project.banco.controller.request

import jakarta.validation.constraints.NotNull

data class TransacaoRequest(
    @field:NotNull(message = "Escrever o valor é obrigatório")
    val valor:Double?) {
}
