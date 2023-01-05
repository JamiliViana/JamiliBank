package com.project.banco.repository.impl

import com.project.banco.domains.Conta
import com.project.banco.repository.BancoRepository
import com.project.banco.repository.jpa.entity.ContaEntity
import com.project.banco.repository.jpa.repository.BancoJpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Component
import java.util.*

@Component
class BancoRepositoryImpl(val bancoJpaRepository: BancoJpaRepository): BancoRepository {

    private fun mapContaEntity (conta: Conta) = ContaEntity(
        saldo = conta.saldo
    )

    override fun save(conta: Conta): ContaEntity {
        return bancoJpaRepository.save(mapContaEntity(conta))
    }

    override fun findById(idConta: Long) = bancoJpaRepository.findById(idConta)


    override fun deleteById(idConta: Long) {
        bancoJpaRepository.deleteById(idConta)
    }

    override fun findAll() = bancoJpaRepository.findAll()

    @Modifying
    @Query(value = "update conta c set c.saldo = c.saldo + ?1 where c.id = ?2")
    override fun setSaldoAumentado(valor: Double, idConta: Long){}
    @Modifying
    @Query(value = "update conta c set c.saldo = c.saldo - ?1 where c.id = ?2")
    override fun setSaldoReduzido(valor: Double, idConta: Long){}

    override fun getSaldoById(idConta: Long) {
        TODO("Not yet implemented")//Depois vou complementar com @Query
    }
}