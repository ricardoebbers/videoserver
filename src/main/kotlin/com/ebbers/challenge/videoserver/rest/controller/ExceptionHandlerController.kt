package com.ebbers.challenge.videoserver.rest.controller

import com.ebbers.challenge.videoserver.rest.dto.ErrorDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.ServletException


@ControllerAdvice
class ExceptionHandlerController : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [
        ServletException::class
    ])
    fun handleServletException(
            ex: ServletException, request: WebRequest): ResponseEntity<ErrorDTO> {
        val code = HttpStatus.BAD_REQUEST
        return ResponseEntity.status(code).body(ErrorDTO(code.name, ex.message ?: ""))
    }
}
