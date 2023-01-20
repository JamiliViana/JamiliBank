package com.project.banco.service.impl

import com.project.banco.domains.Conta
import com.project.banco.exceptions.ContaAlreadyExistsException
import com.project.banco.exceptions.ContaNotFoundException
import com.project.banco.exceptions.SaldoNotEnoughException
import com.project.banco.persistence.BancoPersistenceAdapter
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions


class BancoServiceImplTest {

    @MockK
    lateinit var bancoPersistenceAdapter: BancoPersistenceAdapter

    @InjectMockKs
    lateinit var bancoService: BancoServiceImpl

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
    }
    @Test
    fun deveCriarConta(){
        val conta = Conta("1234",10.0)
        every { bancoPersistenceAdapter.findByCpf(any()) } returns null
        every { bancoPersistenceAdapter.save(conta) } returns conta
        val retorno = bancoService.createAccount(conta)

        Assertions.assertEquals("1234", retorno!!.cpf)
    }
    @Test
    fun deveAcharConta(){
        val conta = Conta("1234",10.0)
        every { bancoPersistenceAdapter.findByCpf(any()) } returns conta

        val retorno = bancoService.getByCpf(conta.cpf)
        Assertions.assertEquals("1234", retorno?.cpf)
    }
    @Test
    fun deveRetornarErroAoCriarContaDuplicada() {
        val conta = Conta("123",0.0)
        val contaDuplicada = Conta("123",0.0)
        every { bancoPersistenceAdapter.findByCpf("123") } returns null
        every { bancoPersistenceAdapter.save(conta) } returns conta
        bancoService.createAccount(conta)

        every { bancoPersistenceAdapter.findByCpf("123") } returns conta

        Assertions.assertThrows(ContaAlreadyExistsException::class.java )
        {bancoService.createAccount(contaDuplicada)}

    }
    @Test
    fun deveRetornarErroAoTentarAcharContaQueNaoExiste() {
        every { bancoPersistenceAdapter.findByCpf(any()) } returns null

        Assertions.assertThrows(ContaNotFoundException::class.java)
        { bancoService.getByCpf("1") }
    }
    @Test
    fun deveRealizarDeposito(){
        val conta = Conta("teste",0.0)
        every { bancoPersistenceAdapter.findByCpf(any()) } returns null
        every { bancoPersistenceAdapter.save(conta) } returns conta
        bancoService.createAccount(conta)

        val valorDeposito = 10.0

        every { bancoPersistenceAdapter.findByCpf("teste") } returns conta

        val recebeContaAtulizada =  bancoService.deposito(conta.cpf, valorDeposito)
        Assertions.assertEquals(valorDeposito, recebeContaAtulizada!!.saldo)
    }
    @Test
    fun deveRealizarSaque(){
        val cpf = "123.456.789-10"
        val valor = 100.0
        val conta = Conta("123.456.789-10",200.0)

        every { bancoPersistenceAdapter.findByCpf(cpf) } returns conta
        every { bancoPersistenceAdapter.save(conta) } returns conta

        val resultado = bancoService.saque(cpf, valor)

        Assertions.assertNotNull(resultado)
        Assertions.assertEquals(100.0, resultado?.saldo)
    }
    @Test
    fun deveRetornarException_QuandoChamarSaque() {
        val cpf = "123.456.789-10"
        val valor = 200.0
        val conta = Conta("123.456.789-10",100.0)

        every { bancoPersistenceAdapter.findByCpf(cpf) } returns conta
        Assertions.assertThrows(SaldoNotEnoughException::class.java) {
            bancoService.saque(cpf, valor)
        }
    }
    @Test
    fun deveRealizarTransferencia() {
        val cpfOrigem = "123.456.789-10"
        val cpfDestino = "987.654.321-00"
        val valorTransferencia = 100.0
        val contaOrigem = Conta("123.456.789-10", 200.0)
        val contaDestino = Conta("987.654.321-00", 300.0)

        every { bancoPersistenceAdapter.findByCpf(cpfOrigem) } returns contaOrigem
        every { bancoPersistenceAdapter.findByCpf(cpfDestino) } returns contaDestino
        every { bancoPersistenceAdapter.save(contaOrigem) } returns contaOrigem
        every { bancoPersistenceAdapter.save(contaDestino) } returns contaDestino

        val resultado = bancoService.transferencia(valorTransferencia, cpfOrigem, cpfDestino)

        Assertions.assertNotNull(resultado)
        Assertions.assertEquals(100.0, contaOrigem.saldo)
        Assertions.assertEquals(400.0, contaDestino.saldo)
    }
    @Test
    fun deveRetornarSaldoNotEnoughException_QuandoChamarTransferencia(){
        val cpfOrigem = "123.456.789-10"
        val cpfDestino = "987.654.321-00"
        val valor = 200.0
        val contaOrigem = Conta("123.456.789-10",100.0)
        val contaDestino = Conta("987.654.321-00",300.0)

        every { bancoPersistenceAdapter.findByCpf(cpfOrigem) } returns contaOrigem
        every { bancoPersistenceAdapter.findByCpf(cpfDestino) } returns contaDestino


        Assertions.assertThrows(SaldoNotEnoughException::class.java) {
            bancoService.transferencia(valor, cpfOrigem, cpfDestino)
        }
    }
}
