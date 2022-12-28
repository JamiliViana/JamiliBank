package com.project.banco.repository.jpa.repository

import com.project.banco.repository.jpa.entity.ContaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BancoJpaRepository : JpaRepository<ContaEntity,Long> {
}