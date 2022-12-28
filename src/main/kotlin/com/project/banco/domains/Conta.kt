package com.project.banco.domains

class Conta(val idConta:Long,
            var saldo:Double = 0.0,
            val cliente: Cliente) {
}