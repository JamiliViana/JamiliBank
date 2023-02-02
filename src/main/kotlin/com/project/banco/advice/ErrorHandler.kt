package com.project.banco.advice

import com.project.banco.advice.exceptions.*
import com.project.banco.service.domains.ErrorMessage
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ContaNotFoundException::class)
    fun ContaNotFoundExceptionHandler(exception:Exception): ResponseEntity<ErrorMessage>{
        return ResponseEntity(ErrorMessage("Conta não localizada"),HttpStatus.NOT_FOUND)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ContaAlreadyExistsException::class)
    fun ContaAlreadyExistsExceptionHandler(exception:Exception): ResponseEntity<ErrorMessage>{
        return ResponseEntity(ErrorMessage("Conta ja está cadastrada"),HttpStatus.BAD_REQUEST)
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SaldoNotEnoughException::class)
    fun SaldoNotEnoughExceptionHandler(exception:Exception): ResponseEntity<ErrorMessage>{
        return ResponseEntity(ErrorMessage("Saldo insuficiente"),HttpStatus.UNAUTHORIZED)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ContaDestinoNotFoundException::class)
    fun ContaDestinoNotFoundExceptionHandler(exception:Exception): ResponseEntity<ErrorMessage>{
        return ResponseEntity(ErrorMessage("Conta Destino não localizada"),HttpStatus.NOT_FOUND)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValueNotAcceptedException::class)
    fun ValueNotAcceptedExceptionHandler(exception:Exception): ResponseEntity<ErrorMessage>{
        return ResponseEntity(ErrorMessage("Valor é negativo, escreva um valor maior que 0"),HttpStatus.BAD_REQUEST)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SameAccountException::class)
    fun SameAccountExceptionHandler(exception:Exception): ResponseEntity<ErrorMessage>{
        return ResponseEntity(ErrorMessage("A Conta origem e destino são as mesmas"),HttpStatus.BAD_REQUEST)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TransactionsNotFoundException::class)
    fun TransactionsNotFoundExceptionHandler(exception:Exception): ResponseEntity<ErrorMessage>{
        return ResponseEntity(ErrorMessage("Nenhuma transação foi encontrada"),HttpStatus.NOT_FOUND)
    }
}