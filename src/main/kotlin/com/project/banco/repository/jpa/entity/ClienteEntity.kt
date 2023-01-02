package com.project.banco.repository.jpa.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class ClienteEntity(
    @field:NotBlank(message = "Escrever o CPF é obrigatório")
    @Id
    val cpf: String,
    @field:NotBlank (message = "Escrever o nome é obrigatório")
    val nome: String,

    @OneToOne
    val contaEntity: ContaEntity?
) {
    constructor() : this("1","null",null) {

    }
}