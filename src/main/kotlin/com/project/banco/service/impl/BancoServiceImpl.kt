package com.project.banco.service.impl

import com.project.banco.model.Cliente
import com.project.banco.model.Conta
import com.project.banco.repository.BancoRepository
import com.project.banco.service.BancoService
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class BancoServiceImpl : BancoService {
    //(val bancoRepository: BancoRepository)
    companion object {
        var bancoServiceArray = arrayOf(Conta(1, 0.0, cliente = Cliente("Abner", "1223")))
    }
    var contas = ConcurrentHashMap<Long,Conta>(bancoServiceArray.associateBy(Conta::idConta))

    override fun createAccount(conta: Conta): Conta? {
//        var verfificaConta = contas.get(conta.idConta)
//        if (verfificaConta == null)
//        this.bancoRepository.save(conta)
            return conta
//        }else return throw ContaAlreadyExistsException
    }

    override fun getContaById(idConta: Long): Conta? {
        val retornoConta = contas.get(idConta)
//        this.bancoRepository.findAll()
//        if (retornoConta != null) {
            return retornoConta
//        }else return throw ContaNotFoundException
    }

    override fun adicionarSaldo(valor: Double, idConta: Long): Conta? {
        val conta = contas.get(idConta)
//        if (conta != null){
//          val setSaldoAumentado = this.bancoRepository.setSaldoAumentado(valor,idConta)
            conta?.saldo?.plus(valor)
            return conta
//        }else return throw ContaNotFoundException
    }

    override fun diminuirSaldo(valor: Double, idConta: Long): Conta? {
        val conta = contas.get(idConta)
//        if (conta != null){
//          val setSaldoReduzido = this.bancoRepository.setSaldoReduzido(valor,idConta)
//          if (conta?.saldo!! > valor) {
                conta?.saldo?.minus(valor)
                return conta
//          }else return throw SaldoNotEnoughException
//
//        }else return throw ContaNotFoundException
//
    }

    override fun transferencia(valor: Double, idContaOrigem: Long, idContaDestino: Long) {
        this.diminuirSaldo(valor,idContaOrigem)
        this.adicionarSaldo(valor,idContaDestino)
    }


    override fun getSaldoById(idConta: Long): String {
        val conta = contas.get(idConta)
        val messageSucess = "Seu saldo é "+ conta!!.saldo
        return messageSucess
    }


}