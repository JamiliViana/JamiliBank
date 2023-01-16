package com.project.banco.persistence.impl

import com.project.banco.domains.Conta
import com.project.banco.persistence.BancoPersistenceAdapter
import com.project.banco.persistence.jpa.entity.ContaEntity
import com.project.banco.persistence.jpa.repository.BancoJpaRepository
import org.springframework.stereotype.Component


@Component
class BancoPersistenceAdapterImpl(val bancoJpaRepository: BancoJpaRepository): BancoPersistenceAdapter {

    private fun mapContaEntity (conta: Conta) = ContaEntity(
        cpf = conta.cpf,
        saldo = conta.saldo
    )
    private fun parseEntityToDomain (contaEntity: ContaEntity) = Conta(
        cpf = contaEntity.cpf,
        saldo = contaEntity.saldo
    )

    override fun save(conta: Conta): Conta {
        var salvandoEntity =  bancoJpaRepository.save(mapContaEntity(conta))
        var recebeDomain = parseEntityToDomain(salvandoEntity)
        return recebeDomain
    }

    override fun deleteAll() {
        bancoJpaRepository.deleteAll()
    }

    override fun findByCpf(cpf: String): Conta? {
        var findEntity = bancoJpaRepository.findById(cpf).orElse(null)
        if (findEntity != null) {
            return parseEntityToDomain(findEntity)
        }  else return null
    }

}