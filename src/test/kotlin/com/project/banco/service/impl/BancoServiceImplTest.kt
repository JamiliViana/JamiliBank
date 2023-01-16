package com.project.banco.service.impl

import com.project.banco.domains.Conta
import com.project.banco.exceptions.ContaAlreadyExistsException
import com.project.banco.exceptions.ContaNotFoundException
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
}