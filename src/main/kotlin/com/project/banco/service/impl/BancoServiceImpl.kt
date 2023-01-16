package com.project.banco.service.impl

import com.project.banco.domains.Conta
import com.project.banco.exceptions.ContaAlreadyExistsException
import com.project.banco.exceptions.ContaNotFoundException
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



}