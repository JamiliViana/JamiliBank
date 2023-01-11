package com.project.banco.persistence.impl

import com.project.banco.domains.Conta
import com.project.banco.persistence.BancoPersistenceAdapter
import com.project.banco.persistence.jpa.entity.ContaEntity
import com.project.banco.persistence.jpa.repository.BancoJpaRepository
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class BancoPersistenceAdapterImpl(val bancoJpaRepository: BancoJpaRepository): BancoPersistenceAdapter {

    private fun mapContaEntity (conta: Conta) = ContaEntity(
        cpf = conta.cpf,
        saldo = conta.saldo
    )

    override fun save(conta: Conta): ContaEntity {
        return bancoJpaRepository.save(mapContaEntity(conta))
    }

    override fun deleteAll() {
        bancoJpaRepository.deleteAll()
    }

    override fun deleteById(cpf: String) {
        bancoJpaRepository.deleteById(cpf)
    }

    override fun findByCpf(cpf: String): Optional<ContaEntity> {
        return bancoJpaRepository.findById(cpf)
    }
    override fun findAll() = bancoJpaRepository.findAll()

}