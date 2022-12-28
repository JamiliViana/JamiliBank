package com.project.banco.service.impl

import com.project.banco.domains.Cliente
import com.project.banco.domains.Conta
import com.project.banco.repository.BancoRepository
import com.project.banco.service.BancoService
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class BancoServiceImpl(val bancoRepository: BancoRepository) : BancoService {

    companion object {
        var bancoServiceArray = arrayOf(Conta(1, 0.0, cliente = Cliente("Abner", "1223")))
    }
    var contas = ConcurrentHashMap<Long,Conta>(bancoServiceArray.associateBy(Conta::idConta))

    override fun createAccount(conta: Conta) {
        this.bancoRepository.save(conta)
        return bancoRepository.findById(conta.idConta)
    }

    override fun getContaById(idConta: Long) {
        return this.bancoRepository.findById(idConta)
    }

    override fun adicionarSaldo(valor: Double, idConta: Long): Conta? {
        val conta = contas[idConta]
//        if (conta != null){
//          val setSaldoAumentado = this.bancoRepository.setSaldoAumentado(valor,idConta)
        conta!!.saldo = valor + conta.saldo
            return contas[idConta]
//        }else return throw ContaNotFoundException
    }

    override fun diminuirSaldo(valor: Double, idConta: Long): Conta? {
        val conta = contas[idConta]
//        if (conta != null){
//          val setSaldoReduzido = this.bancoRepository.setSaldoReduzido(valor,idConta)
//          if (conta?.saldo!! > valor) {
        conta!!.saldo = valor - conta.saldo
        return contas[idConta]
//          }else return throw SaldoNotEnoughException
//        }else return throw ContaNotFoundException
    }

    override fun transferencia(valor: Double, idContaOrigem: Long, idContaDestino: Long) {
        this.diminuirSaldo(valor,idContaOrigem)
        this.adicionarSaldo(valor,idContaDestino)
    }


    override fun getSaldoById(idConta: Long): String {
        val conta = bancoRepository.findById(idConta)
        val messageSucess = "Seu saldo é "+ conta
        return messageSucess
    }


}