package com.project.banco.repository.impl

import com.project.banco.controller.request.ContaRequest
import com.project.banco.domains.Conta
import com.project.banco.repository.BancoRepository
import com.project.banco.repository.jpa.entity.ClienteEntity
import com.project.banco.repository.jpa.entity.ContaEntity
import com.project.banco.repository.jpa.repository.BancoJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class BancoRepositoryImpl(val bancoJpaRepository: BancoJpaRepository): BancoRepository {

    private fun mapContaEntity (conta: ContaRequest) = ContaEntity(
        idConta = conta.idConta,
        saldo = conta.saldo
    )

    override fun save(conta: ContaRequest) {
        bancoJpaRepository.save(mapContaEntity(conta))
    }

    override fun findById(idConta: Long): Optional<ContaEntity> {
        return bancoJpaRepository.findById(idConta)
    }

    override fun deleteById(idConta: Long) {
        bancoJpaRepository.deleteById(idConta)
    }

    override fun findAll() {
        bancoJpaRepository.findAll()
    }

    override fun setSaldoAumentado(valor: Double, idConta: Long) {
        TODO("Not yet implemented")//Depois vou complementar com @Query
    }

    override fun setSaldoReduzido(valor: Double, idConta: Long) {
        TODO("Not yet implemented")//Depois vou complementar com @Query
    }

    override fun getSaldoById(idConta: Long) {
        TODO("Not yet implemented")//Depois vou complementar com @Query
    }
}