package com.project.banco.repository.jpa.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "conta_entity")
data class ContaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conta", nullable = false)
    var idConta: Long? = null,
    val saldo: Double?,
//
//    @OneToOne
//    @JoinColumn(columnDefinition = "CPF")
//    @field:NotBlank(message = "Preencher todos os campos cliente é obrigatório")
//    val clienteEntity: ClienteEntity?

) {
    constructor() : this(null, null, )
}

