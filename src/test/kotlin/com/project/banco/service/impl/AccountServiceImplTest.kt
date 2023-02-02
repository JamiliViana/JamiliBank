//package com.project.banco.service.impl
//
//import com.project.banco.domains.Account
//import com.project.banco.exceptions.ContaDestinoNotFoundException
//import com.project.banco.exceptions.ContaNotFoundException
//import com.project.banco.exceptions.SaldoNotEnoughException
//import com.project.banco.exceptions.ValueNotAcceptedException
//import com.project.banco.exceptions.SameAccountException
//import com.project.banco.exceptions.ContaAlreadyExistsException
//import com.project.banco.persistence.AccountPersistenceAdapter
//import io.mockk.MockKAnnotations
//import io.mockk.every
//import io.mockk.impl.annotations.InjectMockKs
//import io.mockk.impl.annotations.MockK
//import org.junit.Before
//import org.junit.Test
//import org.junit.jupiter.api.Assertions
//
//
//class AccountServiceImplTest {
//
//    @MockK
//    lateinit var accountPersistenceAdapter: AccountPersistenceAdapter
//
//    @InjectMockKs
//    lateinit var bancoService: AccountServiceImpl
//
//    @Before
//    fun setUp(){
//        MockKAnnotations.init(this)
//    }
//    @Test
//    fun deveCriarConta(){
//        val account = Account("1234",10.0)
//        every { accountPersistenceAdapter.findByCpf(any()) } returns null
//        every { accountPersistenceAdapter.save(account) } returns account
//        val retorno = bancoService.createAccount(account)
//
//        Assertions.assertEquals("1234", retorno!!.cpf)
//    }
//    @Test
//    fun deveAcharConta(){
//        val account = Account("1234",10.0)
//        every { accountPersistenceAdapter.findByCpf(any()) } returns account
//
//        val retorno = bancoService.getByCpf(account.cpf)
//        Assertions.assertEquals("1234", retorno?.cpf)
//    }
//    @Test
//    fun deveRetornarErroAoCriarContaDuplicada() {
//        val account = Account("123",0.0)
//        val accountDuplicada = Account("123",0.0)
//        every { accountPersistenceAdapter.findByCpf("123") } returns null
//        every { accountPersistenceAdapter.save(account) } returns account
//        bancoService.createAccount(account)
//
//        every { accountPersistenceAdapter.findByCpf("123") } returns account
//
//        Assertions.assertThrows(ContaAlreadyExistsException::class.java )
//        {bancoService.createAccount(accountDuplicada)}
//
//    }
//    @Test
//    fun deveRetornarErroAoTentarAcharContaQueNaoExiste() {
//        every { accountPersistenceAdapter.findByCpf(any()) } returns null
//
//        Assertions.assertThrows(ContaNotFoundException::class.java)
//        { bancoService.getByCpf("1") }
//    }
//    @Test
//    fun deveRealizarDeposito(){
//        val account = Account("teste",0.0)
//        every { accountPersistenceAdapter.findByCpf(any()) } returns null
//        every { accountPersistenceAdapter.save(account) } returns account
//        bancoService.createAccount(account)
//
//        val valorDeposito = 10.0
//
//        every { accountPersistenceAdapter.findByCpf("teste") } returns account
//
//        val recebeContaAtulizada =  bancoService.deposito(account.cpf, valorDeposito)
//        Assertions.assertEquals(valorDeposito, recebeContaAtulizada!!.saldo)
//    }
//    @Test
//    fun deveLancarContaNotFoundException_QuandoChamarDeposito(){
//        val cpf = "123.456.789-10"
//        val valor = 10.0
//        every { accountPersistenceAdapter.findByCpf(any()) } returns null
//        Assertions.assertThrows(ContaNotFoundException::class.java,{bancoService.deposito(cpf,valor)})
//    }
//    @Test
//    fun deveLancarValueNotAcceptedException_QuandoChamarDeposito() {
//        val cpf = "123.456.789-10"
//        val valor = -10.0
//        val account = Account(cpf = cpf, saldo = 100.0)
//
//        every { accountPersistenceAdapter.findByCpf(cpf) } returns account
//
//        Assertions.assertThrows(ValueNotAcceptedException::class.java,{bancoService.deposito(cpf,valor)})
//    }
//    @Test
//    fun deveRealizarSaque(){
//        val cpf = "123.456.789-10"
//        val valor = 100.0
//        val account = Account("123.456.789-10",200.0)
//
//        every { accountPersistenceAdapter.findByCpf(cpf) } returns account
//        every { accountPersistenceAdapter.save(account) } returns account
//
//        val resultado = bancoService.saque(cpf, valor)
//
//        Assertions.assertNotNull(resultado)
//        Assertions.assertEquals(100.0, resultado?.saldo)
//    }
//    @Test
//    fun deveLancarContaNotFoundException_QuandoChamarSaque(){
//        val cpf = "123.456.789-10"
//        val valor = 10.0
//        every { accountPersistenceAdapter.findByCpf(any()) } returns null
//        Assertions.assertThrows(ContaNotFoundException::class.java,{bancoService.saque(cpf,valor)})
//    }
//    @Test
//    fun deveRetornarSaldoNotEnoughException_QuandoChamarSaque() {
//        val cpf = "123.456.789-10"
//        val valor = 200.0
//        val account = Account("123.456.789-10",100.0)
//
//        every { accountPersistenceAdapter.findByCpf(cpf) } returns account
//        Assertions.assertThrows(SaldoNotEnoughException::class.java) {
//            bancoService.saque(cpf, valor)
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
//            bancoService.saque(cpf, valor)
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
//        val resultado = bancoService.transferencia(valorTransferencia, cpfOrigem, cpfDestino)
//
//        Assertions.assertNotNull(resultado)
//        Assertions.assertEquals(100.0, accountOrigem.saldo)
//        Assertions.assertEquals(400.0, accountDestino.saldo)
//    }
//    @Test
//    fun deveLancarContaNotFoundException_QuandoChamarTransferencia(){
//        val cpfOrigem = "123.456.789-10"
//        val cpfDestino = "987.654.321-00"
//        val valorTransferencia = 100.0
//        every { accountPersistenceAdapter.findByCpf(any()) } returns null
//        Assertions.assertThrows(ContaNotFoundException::class.java,{bancoService.transferencia(valorTransferencia,cpfOrigem,cpfDestino)})
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
//        Assertions.assertThrows(ContaDestinoNotFoundException::class.java,{bancoService.transferencia(valorTransferencia,cpfOrigem,cpfDestino)})
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
//            bancoService.transferencia(valor, cpfOrigem, cpfDestino)
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
//            bancoService.transferencia(valor, cpfOrigem, cpfDestino)
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
//            bancoService.transferencia(valor, cpfOrigem, cpfDestino)
//        }
//    }
//}
