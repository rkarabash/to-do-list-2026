package ru.school57.todolist.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.school57.todolist.dto.request.RegisterUserRequest
import ru.school57.todolist.dto.response.ErrorResponse
import ru.school57.todolist.dto.response.ServiceResponse
import ru.school57.todolist.exception.BadRequestException
import ru.school57.todolist.service.AuthService

@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun registerUser(@RequestBody request: RegisterUserRequest): ResponseEntity<*> =
        ResponseEntity.ok(ServiceResponse(authService.registerUser(request)))
}