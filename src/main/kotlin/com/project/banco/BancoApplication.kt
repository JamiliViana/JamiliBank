package com.project.banco

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component

@SpringBootApplication
class BancoApplication

fun main(args: Array<String>) {
	runApplication<BancoApplication>(*args)
}
