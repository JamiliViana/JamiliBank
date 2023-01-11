package com.project.banco.persistence.jpa.entity

import com.project.banco.emptyConstructorOfDataCls.NoArg
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
@NoArg
data class ContaEntity(
    @Id
    val cpf: String,
    val saldo: Double
)

