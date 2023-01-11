package com.project.banco

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.banco.domains.Conta
import com.project.banco.service.BancoService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class BancoApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Autowired
	lateinit var mockMvc: MockMvc

	@Autowired
	lateinit var objectMapper: ObjectMapper

	@Autowired
	lateinit var service: BancoService

	@org.junit.jupiter.api.Test
	fun deveRetornarCreated_QuandoChamarCreate() {
		service?.deleteAll()
		val conta = Conta (
			"1234",
			0.1
		)
		val resultado = mockMvc?.perform(
			MockMvcRequestBuilders.post("/banco")
				.contentType("application/json")
				.content(objectMapper!!.writeValueAsString(conta)))
			?.andExpect(MockMvcResultMatchers.status().isCreated)
			?.andReturn()?.response?.contentAsString

		var buscaConta = service?.getByCpf("1234")
		Assertions.assertNotNull(buscaConta)
	}

//////////////////////////////////////////////////////////////////
	@org.junit.jupiter.api.Test
	fun deveRetornarOK_QuandoChamarGetByCpf() {
		service?.deleteAll()
		val conta = service?.createAccount(
			Conta(
			"12345",
			0.1)
		)
		mockMvc?.perform(MockMvcRequestBuilders.get("/banco/cpf/")
				.accept(MediaType.APPLICATION_JSON)
				.param("cpf",conta!!.cpf))
			?.andExpect(MockMvcResultMatchers.status().isOk)

		var buscaConta = service?.getByCpf(conta!!.cpf!!)
		Assertions.assertEquals(conta,buscaConta)
	}
	@Test
	fun deveRetornarNotFound_QuandoChamarGetById() {
		service?.deleteAll()
		mockMvc?.perform(MockMvcRequestBuilders.get("/banco/cpf/")
			.param("cpf","1"))
			?.andExpect(MockMvcResultMatchers.status().isNotFound)
	}
	//////////////////////////////////////////////////////////////////
	@Test
	fun deveRetornarOK_QuandoChamarGetAll(){
		service?.deleteAll()

		service?.createAccount(Conta(
			"12345",
			0.1)
		)
		service?.createAccount(Conta(
			"123456789",
			10.0)
		)
		mockMvc?.perform(
			MockMvcRequestBuilders.get("/banco")
			.accept(MediaType.APPLICATION_JSON))
			?.andExpect(MockMvcResultMatchers.status().isOk)

		val promocaoReturn = (service.getAll())
		Assertions.assertEquals(2,promocaoReturn?.size)
	}
	@Test
	fun deveRetornarNotFound_QuandoChamarGetAll(){
		service?.deleteAll()
		mockMvc?.perform(MockMvcRequestBuilders.get("/banco/")
			.accept(MediaType.APPLICATION_JSON))
			?.andExpect(MockMvcResultMatchers.status().isNotFound)
	}

}
