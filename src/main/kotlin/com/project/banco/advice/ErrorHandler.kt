package com.project.banco.advice

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

class ErrorHandler() : ResponseEntityExceptionHandler() {
    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException,
                                              headers: HttpHeaders,
                                              status: HttpStatusCode,
                                              request: WebRequest): ResponseEntity<Any>? {
        val message = mutableListOf<String>()
        ex.bindingResult.allErrors.forEach {message.add("${it.defaultMessage}") }
        return handleExceptionInternal(ex, message, headers, HttpStatus.BAD_REQUEST, request)
    }

}