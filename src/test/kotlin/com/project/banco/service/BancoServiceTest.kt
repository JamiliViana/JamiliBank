//package com.project.banco.service
//
//import com.project.banco.domains.Cliente
//import com.project.banco.domains.Conta
//import com.project.banco.service.impl.BancoServiceImpl
//import org.junit.jupiter.api.Assertions
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.InjectMocks
//import org.mockito.junit.MockitoJUnitRunner
//import org.springframework.beans.factory.annotation.Autowired
//
//@RunWith(MockitoJUnitRunner::class)
//class BancoServiceTest () {
//
//    @InjectMocks
//    @Autowired
//    lateinit var bancoService: BancoServiceImpl
//
//    @Test
//    fun create(){
//        bancoService.createAccount(
//            Conta(2,
//                0.0,
//                Cliente(
//                    "Jamili",
//                    "1232")))
//        val pegarResultado = bancoService.getContaById(2)
//        Assertions.assertEquals(2,pegarResultado?.idConta)
//        Assertions.assertEquals(0.0,pegarResultado?.saldo)
//        Assertions.assertNotNull(pegarResultado?.cliente)
//    }
//
//    @Test
//    fun getContaById(){
//        bancoService.createAccount(
//            Conta(2,
//                0.0,
//                Cliente(
//                    "Jamili",
//                    "1232")))
//        var retorno = bancoService.getContaById(2)
//        Assertions.assertEquals(2,retorno?.idConta)
//    }
//
//    @Test
//    fun adicionarSaldo(){
//        bancoService.createAccount(
//            Conta(2,
//                0.0,
//                Cliente(
//                    "Jamili",
//                    "1232")))
//        bancoService.adicionarSaldo(200.0,2)
//        var recebeSaldoConta = bancoService.getContaById(2)
//        Assertions.assertEquals(200.0,recebeSaldoConta?.saldo)
//    }
//
//    @Test
//    fun diminuirSaldo(){
//        bancoService.createAccount(
//            Conta(2,
//                200.0,
//                Cliente(
//                    "Jamili",
//                    "1232")))
//        bancoService.diminuirSaldo(200.0,2)
//        var recebeSaldoConta = bancoService.getContaById(2)
//        Assertions.assertEquals(0.0,recebeSaldoConta?.saldo)
//    }
//
//    @Test
//    fun transferencia(){
//        bancoService.createAccount(
//            Conta(2,
//                1000.0,
//                Cliente(
//                    "Jamili",
//                    "1232")))
//        bancoService.createAccount(
//            Conta(3,
//                0.0,
//                Cliente(
//                    "ContaDestino",
//                    "12324")))
//        bancoService.transferencia(1000.0,2,3)
//        var recebeSaldoContaOrigem = bancoService.getContaById(2)
//        var recebeSaldoContaDestino = bancoService.getContaById(3)
//        Assertions.assertEquals(0.0,recebeSaldoContaOrigem?.saldo)
//        Assertions.assertEquals(1000.0,recebeSaldoContaDestino?.saldo)
//    }
//
//    @Test
//    fun getSaldoById(){
//        bancoService.createAccount(
//            Conta(3,
//                400.0,
//                Cliente(
//                    "Conta Teste",
//                    "12324")))
//        val pegarSaldo = bancoService.getSaldoById(3)
//        Assertions.assertEquals("Seu saldo é 400.0",pegarSaldo)
//    }
//
//}