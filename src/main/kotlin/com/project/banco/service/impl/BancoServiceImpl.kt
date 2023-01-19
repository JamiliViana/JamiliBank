package com.project.banco.service.impl

import com.project.banco.domains.Conta
import com.project.banco.exceptions.ContaAlreadyExistsException
import com.project.banco.exceptions.ContaNotFoundException
import com.project.banco.exceptions.SaldoNotEnoughException
import com.project.banco.persistence.BancoPersistenceAdapter
import com.project.banco.service.BancoService
import org.springframework.stereotype.Service

@Service
class BancoServiceImpl(val bancoPersistenceAdapter: BancoPersistenceAdapter) : BancoService {
    override fun deleteAll() {
        bancoPersistenceAdapter.deleteAll()
    }

    override fun createAccount(conta: Conta): Conta? {
        val verificaContaExiste = this.bancoPersistenceAdapter.findByCpf(conta.cpf)
        if (verificaContaExiste == null) {
            return this.bancoPersistenceAdapter.save(conta)
        }else throw ContaAlreadyExistsException()
    }

    override fun getByCpf(cpf: String): Conta {
        val verificaContaExiste = this.bancoPersistenceAdapter.findByCpf(cpf)
        if (verificaContaExiste != null) {
            return verificaContaExiste
        }else throw ContaNotFoundException()
    }

    override fun deposito(cpf: String, valor: Double): Conta? {
        val receberDadosConta = this.bancoPersistenceAdapter.findByCpf(cpf)
        if(receberDadosConta != null){
            val somarDeposito = receberDadosConta.saldo + valor
            receberDadosConta.saldo = somarDeposito
            return this.bancoPersistenceAdapter.save(receberDadosConta)
        }else throw ContaNotFoundException()
    }

    override fun saque(cpf: String, valor: Double): Conta? {
        val receberDadosConta = this.bancoPersistenceAdapter.findByCpf(cpf)
        if (receberDadosConta != null){
            if (receberDadosConta.saldo >= valor){
                val diminuirSaldoSaque = receberDadosConta.saldo - valor
                receberDadosConta.saldo = diminuirSaldoSaque
                return this.bancoPersistenceAdapter.save(receberDadosConta)
            }else throw SaldoNotEnoughException()
        }else throw ContaNotFoundException()
    }


}