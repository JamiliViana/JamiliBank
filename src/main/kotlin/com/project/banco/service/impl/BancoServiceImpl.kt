package com.project.banco.service.impl

import com.project.banco.domains.Conta
import com.project.banco.controller.request.ContaRequest
import com.project.banco.exceptions.ContaNotFoundException
import com.project.banco.repository.BancoRepository
import com.project.banco.repository.jpa.entity.ContaEntity
import com.project.banco.service.BancoService
import org.springframework.stereotype.Component
import java.util.*

@Component
class BancoServiceImpl(val bancoRepository: BancoRepository) : BancoService {

    override fun createAccount(conta: ContaRequest): Optional<ContaEntity> {
        this.bancoRepository.save(conta)
        return bancoRepository.findById(conta.idConta)
    }

    override fun getContaById(idConta: Long): ContaEntity? {
        var conta = bancoRepository.findById(idConta).orElse(null)
        if (conta != null){
            return conta
        }else throw ContaNotFoundException()
    }

    override fun adicionarSaldo(valor: Double, idConta: Long): Conta? {
        TODO("Not yet implemented")
    }

    override fun diminuirSaldo(valor: Double, idConta: Long): Conta? {
        TODO("Not yet implemented")
    }

//    override fun adicionarSaldo(valor: Double, idConta: Long): Conta? {
//        val conta = contas[idConta]
////        if (conta != null){
////          val setSaldoAumentado = this.bancoRepository.setSaldoAumentado(valor,idConta)
//        conta!!.saldo = valor + conta.saldo
//            return contas[idConta]
////        }else return throw ContaNotFoundException
//    }

//    override fun diminuirSaldo(valor: Double, idConta: Long): Conta? {
//        val conta = contas[idConta]
////        if (conta != null){
////          val setSaldoReduzido = this.bancoRepository.setSaldoReduzido(valor,idConta)
////          if (conta?.saldo!! > valor) {
//        conta!!.saldo = valor - conta.saldo
//        return contas[idConta]
////          }else return throw SaldoNotEnoughException
////        }else return throw ContaNotFoundException
//    }

    override fun transferencia(valor: Double, idContaOrigem: Long, idContaDestino: Long) {
        this.diminuirSaldo(valor,idContaOrigem)
        this.adicionarSaldo(valor,idContaDestino)
    }


    override fun getSaldoById(idConta: Long): String {
        val conta = this.getContaById(idConta)
        val messageSucess = "Seu saldo é "+ conta
        return messageSucess
    }


}