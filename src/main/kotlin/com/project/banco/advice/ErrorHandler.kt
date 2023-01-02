package com.project.banco.advice

import com.project.banco.domains.ErrorMessage
import com.project.banco.exceptions.ContaNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
@ControllerAdvice
class ErrorHandler() : ResponseEntityExceptionHandler() {
    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException,
                                              headers: HttpHeaders,
                                              status: HttpStatusCode,
                                              request: WebRequest): ResponseEntity<Any>? {
        val message = mutableListOf<String>()
        ex.bindingResult.allErrors.forEach {message.add("${it.defaultMessage}") }
        return handleExceptionInternal(ex, message, headers, HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(ContaNotFoundException::class)
    fun PromocaoNotFoundExceptionHandler(exception:Exception): ResponseEntity<ErrorMessage>{
        return ResponseEntity(ErrorMessage("Conta não localizada"),
            HttpStatus.NOT_FOUND)
    }

}