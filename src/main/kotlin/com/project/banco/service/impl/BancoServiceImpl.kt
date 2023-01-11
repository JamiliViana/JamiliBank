package com.project.banco.service.impl

import com.project.banco.domains.Conta
import com.project.banco.exceptions.ContaNotFoundException
import com.project.banco.persistence.BancoPersistenceAdapter
import com.project.banco.persistence.jpa.entity.ContaEntity
import com.project.banco.service.BancoService
import org.springframework.stereotype.Service

@Service
class BancoServiceImpl(val bancoPersistenceAdapter: BancoPersistenceAdapter) : BancoService {
    override fun deleteAll() {
            this.bancoPersistenceAdapter.deleteAll()
    }

    override fun createAccount(conta: Conta): ContaEntity {
        return this.bancoPersistenceAdapter.save(conta)
    }

    override fun getAll(): MutableList<ContaEntity> {
        if (this.bancoPersistenceAdapter.findAll().isNotEmpty()) {
            return this.bancoPersistenceAdapter.findAll()
        }else throw ContaNotFoundException()
    }

    override fun getByCpf(cpf: String): ContaEntity? {
        val retorno = this.bancoPersistenceAdapter.findByCpf(cpf).orElse(null)
        if (retorno != null){
            return retorno
        }else throw ContaNotFoundException()
    }

    override fun setSaldo(cpf: String, valor: Double): ContaEntity? {
        val conta = Conta(cpf, valor)
        if (this.getByCpf(cpf) != null){
            createAccount(conta)
            return this.getByCpf(cpf)
        }else throw ContaNotFoundException()
    }


}