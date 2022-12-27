package com.project.banco.service

import com.project.banco.model.Cliente
import com.project.banco.model.Conta
import com.project.banco.service.impl.BancoServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.beans.factory.annotation.Autowired

@RunWith(MockitoJUnitRunner::class)
class BancoServiceTest () {

    @InjectMocks
    @Autowired
    lateinit var bancoService: BancoServiceImpl

    @Test
    fun create(){
        var conta = bancoService.createAccount(
            Conta(1,
                0.0,
                Cliente(
                    "Jamili",
                    "1232")))
        Assertions.assertNotNull(conta)
    }

}