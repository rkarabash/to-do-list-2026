package ru.school57.todolist.service

import org.springframework.stereotype.Component
import ru.school57.todolist.dto.request.RegisterUserRequest
import ru.school57.todolist.entity.User
import ru.school57.todolist.exception.BadRequestException
import ru.school57.todolist.repository.UserRepository

@Component
class AuthService(
    private val userRepository: UserRepository
) {
    fun registerUser(request: RegisterUserRequest): String {
        val user = userRepository.findByEmail(request.email)
        if (user != null) throw BadRequestException("Пользователь уже зарегистрирован")
        userRepository.save(
            User(
                email = request.email,
                password = request.password,
                username = request.username
            )
        )
        return "Пользователь успешно зарегистрирован"
    }
}
