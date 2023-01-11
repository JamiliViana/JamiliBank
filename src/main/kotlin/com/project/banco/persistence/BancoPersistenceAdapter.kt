package com.project.banco.persistence

import com.project.banco.domains.Conta
import com.project.banco.persistence.jpa.entity.ContaEntity
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface BancoPersistenceAdapter{
    fun deleteAll()
    fun deleteById(cpf: String)
    fun findByCpf(cpf: String): Optional<ContaEntity>
    fun findAll(): MutableList<ContaEntity>
    fun save(conta: Conta): ContaEntity
}