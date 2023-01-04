package com.project.banco.domains

import com.project.banco.controller.request.ContaRequest

data class Conta(    val idConta: Long? = null,
                     val saldo: Double?) {
}