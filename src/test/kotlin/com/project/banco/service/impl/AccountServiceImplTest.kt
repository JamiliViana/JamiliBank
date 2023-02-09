//package com.project.banco.service.impl
//
//import com.project.banco.advice.exceptions.ContaAlreadyExistsException
//import com.project.banco.advice.exceptions.ContaNotFoundException
//import com.project.banco.persistence.AccountPersistenceAdapter
//import com.project.banco.service.domains.Account
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
//
//}
