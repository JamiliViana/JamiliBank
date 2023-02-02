//package com.project.banco.service.impl
//
//import com.project.banco.advice.exceptions.*
//import com.project.banco.persistence.AccountPersistenceAdapter
//import com.project.banco.persistence.TransactionsPersintenceAdapter
//import com.project.banco.service.domains.Account
//import io.mockk.MockKAnnotations
//import io.mockk.every
//import io.mockk.impl.annotations.InjectMockKs
//import io.mockk.impl.annotations.MockK
//import org.junit.Before
//import org.junit.Test
//import org.junit.jupiter.api.Assertions
//
//class TransactionsServiceImplTest {
//
//    @MockK
//    lateinit var accountPersistenceAdapter: AccountPersistenceAdapter
//
//    @MockK
//    lateinit var transactionsPersintenceAdapter: TransactionsPersintenceAdapter
//
//    @InjectMockKs
//    lateinit var accountServiceImpl: AccountServiceImpl
//
//    @InjectMockKs
//    lateinit var transactionsServiceImpl: TransactionsServiceImpl
//
//    @Before
//    fun setUp(){
//        MockKAnnotations.init(this)
//    }
//
//    @Test
//    fun deveRealizarDeposito(){
//        val account = Account("teste",0.0)
//        every { accountPersistenceAdapter.findByCpf(any()) } returns null
//        every { accountPersistenceAdapter.save(account) } returns account
//        accountServiceImpl.createAccount(account)
//
//        val valorDeposito = 10.0
//
//        every { accountPersistenceAdapter.findByCpf("teste") } returns account
//
//        val recebeContaAtulizada =  transactionsServiceImpl.deposit(account.cpf, valorDeposito)
//        Assertions.assertEquals(valorDeposito, recebeContaAtulizada!!.balance)
//    }
//    @Test
//    fun deveLancarContaNotFoundException_QuandoChamarDeposito(){
//        val cpf = "123.456.789-10"
//        val valor = 10.0
//        every { accountPersistenceAdapter.findByCpf(any()) } returns null
//        Assertions.assertThrows(ContaNotFoundException::class.java,{transactionsServiceImpl.deposit(cpf,valor)})
//    }
//    @Test
//    fun deveLancarValueNotAcceptedException_QuandoChamarDeposito() {
//        val cpf = "123.456.789-10"
//        val valor = -10.0
//        val account = Account(cpf = cpf, balance = 100.0)
//
//        every { accountPersistenceAdapter.findByCpf(cpf) } returns account
//
//        Assertions.assertThrows(ValueNotAcceptedException::class.java,{transactionsServiceImpl.deposit(cpf,valor)})
//    }
//
//    @Test
//    fun deveRealizarSaque(){
//        val cpf = "123.456.789-10"
//        val valor = 100.0
//        val account = Account("123.456.789-10",200.0)
//
//        every { accountPersistenceAdapter.findByCpf(cpf) } returns account
//        every { accountPersistenceAdapter.save(account) } returns account
//
//        val resultado = transactionsServiceImpl.withdraw(cpf, valor)
//
//        Assertions.assertNotNull(resultado)
//        Assertions.assertEquals(100.0, resultado?.balance)
//    }
//    @Test
//    fun deveLancarContaNotFoundException_QuandoChamarSaque(){
//        val cpf = "123.456.789-10"
//        val valor = 10.0
//        every { accountPersistenceAdapter.findByCpf(any()) } returns null
//        Assertions.assertThrows(ContaNotFoundException::class.java,{transactionsServiceImpl.withdraw(cpf,valor)})
//    }
//    @Test
//    fun deveRetornarSaldoNotEnoughException_QuandoChamarSaque() {
//        val cpf = "123.456.789-10"
//        val valor = 200.0
//        val account = Account("123.456.789-10",100.0)
//
//        every { accountPersistenceAdapter.findByCpf(cpf) } returns account
//        Assertions.assertThrows(SaldoNotEnoughException::class.java) {
//            transactionsServiceImpl.withdraw(cpf, valor)
//        }
//    }
//    @Test
//    fun deveRetornarValueNotAcceptedException_QuandoChamarSaque() {
//        val cpf = "123.456.789-10"
//        val valor = -200.0
//        val account = Account("123.456.789-10",100.0)
//
//        every { accountPersistenceAdapter.findByCpf(cpf) } returns account
//        Assertions.assertThrows(ValueNotAcceptedException::class.java) {
//            transactionsServiceImpl.withdraw(cpf, valor)
//        }
//    }
//    @Test
//    fun deveRealizarTransferencia() {
//        val cpfOrigem = "123.456.789-10"
//        val cpfDestino = "987.654.321-00"
//        val valorTransferencia = 100.0
//        val accountOrigem = Account("123.456.789-10", 200.0)
//        val accountDestino = Account("987.654.321-00", 300.0)
//
//        every { accountPersistenceAdapter.findByCpf(cpfOrigem) } returns accountOrigem
//        every { accountPersistenceAdapter.findByCpf(cpfDestino) } returns accountDestino
//        every { accountPersistenceAdapter.save(accountOrigem) } returns accountOrigem
//        every { accountPersistenceAdapter.save(accountDestino) } returns accountDestino
//
//        val resultado = transactionsServiceImpl.transfer(valorTransferencia, cpfOrigem, cpfDestino)
//
//        Assertions.assertNotNull(resultado)
//        Assertions.assertEquals(100.0, accountOrigem.balance)
//        Assertions.assertEquals(400.0, accountDestino.balance)
//    }
//    @Test
//    fun deveLancarContaNotFoundException_QuandoChamarTransferencia(){
//        val cpfOrigem = "123.456.789-10"
//        val cpfDestino = "987.654.321-00"
//        val valorTransferencia = 100.0
//        every { accountPersistenceAdapter.findByCpf(any()) } returns null
//        Assertions.assertThrows(ContaNotFoundException::class.java,{transactionsServiceImpl.transfer(valorTransferencia,cpfOrigem,cpfDestino)})
//    }
//    @Test
//    fun deveLancarContaDestinoNotFoundException_QuandoChamarTransferencia(){
//        val cpfOrigem = "123.456.789-10"
//        val cpfDestino = "987.654.321-00"
//        val valorTransferencia = 100.0
//        val accountOrigem = Account("123.456.789-10", 200.0)
//
//        every { accountPersistenceAdapter.findByCpf(cpfOrigem) } returns accountOrigem
//        every { accountPersistenceAdapter.findByCpf(cpfDestino) } returns null
//        Assertions.assertThrows(ContaDestinoNotFoundException::class.java,{transactionsServiceImpl.transfer(valorTransferencia,cpfOrigem,cpfDestino)})
//    }
//    @Test
//    fun deveRetornarValueNotAcceptedException_QuandoChamarTransferencia(){
//        val cpfOrigem = "123.456.789-10"
//        val cpfDestino = "987.654.321-00"
//        val valor = -200.0
//        val accountOrigem = Account("123.456.789-10",100.0)
//        val accountDestino = Account("987.654.321-00",300.0)
//
//        every { accountPersistenceAdapter.findByCpf(cpfOrigem) } returns accountOrigem
//        every { accountPersistenceAdapter.findByCpf(cpfDestino) } returns accountDestino
//
//
//        Assertions.assertThrows(ValueNotAcceptedException::class.java) {
//            transactionsServiceImpl.transfer(valor, cpfOrigem, cpfDestino)
//        }
//    }
//    @Test
//    fun deveRetornarSaldoNotEnoughException_QuandoChamarTransferencia(){
//        val cpfOrigem = "123.456.789-10"
//        val cpfDestino = "987.654.321-00"
//        val valor = 200.0
//        val accountOrigem = Account("123.456.789-10",100.0)
//        val accountDestino = Account("987.654.321-00",300.0)
//
//        every { accountPersistenceAdapter.findByCpf(cpfOrigem) } returns accountOrigem
//        every { accountPersistenceAdapter.findByCpf(cpfDestino) } returns accountDestino
//
//
//        Assertions.assertThrows(SaldoNotEnoughException::class.java) {
//            transactionsServiceImpl.transfer(valor, cpfOrigem, cpfDestino)
//        }
//    }
//    @Test
//    fun deveRetornarSameAccountException_QuandoChamarTransferencia(){
//        val cpfOrigem = "123.456.789-10"
//        val cpfDestino = "123.456.789-10"
//        val valor = 10.0
//        val accountOrigem = Account("123.456.789-10",100.0)
//
//        every { accountPersistenceAdapter.findByCpf(cpfOrigem) } returns accountOrigem
//        every { accountPersistenceAdapter.findByCpf(cpfDestino) } returns accountOrigem
//
//
//        Assertions.assertThrows(SameAccountException::class.java) {
//            transactionsServiceImpl.transfer(valor, cpfOrigem, cpfDestino)
//        }
//    }
//}