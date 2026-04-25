package ru.school57.todolist.controlleradvice

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.school57.todolist.dto.response.ErrorResponse
import ru.school57.todolist.exception.BadRequestException

@RestControllerAdvice
class ExceptionHandler {
private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ErrorResponse> {
        logger.warn("Пользователь передал невадидные данные:", ex)
        return ResponseEntity.badRequest().body(ErrorResponse(ex.message ?: ""))
    }
}