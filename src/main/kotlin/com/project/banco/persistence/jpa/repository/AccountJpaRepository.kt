package com.project.banco.persistence.jpa.repository

import com.project.banco.persistence.jpa.entity.AccountEntity
import com.project.banco.persistence.jpa.entity.AccountTransactionsEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountJpaRepository : JpaRepository<AccountEntity,String> {

}