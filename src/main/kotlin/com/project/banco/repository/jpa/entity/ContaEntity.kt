package com.project.banco.repository.jpa.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
data class ContaEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idConta: Long? = null,
    val saldo: Double? = null,
)

