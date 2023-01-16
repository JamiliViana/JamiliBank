package com.project.banco.persistence.jpa.repository

import com.project.banco.persistence.jpa.entity.ContaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BancoJpaRepository : JpaRepository<ContaEntity,String> {
}