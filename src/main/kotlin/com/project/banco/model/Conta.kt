package com.project.banco.model

import jakarta.validation.constraints.NotBlank

class Conta(val idConta:Long,
            val saldo:Double = 0.0,
            @field:NotBlank(message = "Associar um cliente é obrigatório")
            val cliente: Cliente) {
}