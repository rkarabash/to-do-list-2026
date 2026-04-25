package ru.school57.todolist.service

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.school57.todolist.dto.request.RegisterUserRequest
import ru.school57.todolist.entity.User
import ru.school57.todolist.exception.BadRequestException
import ru.school57.todolist.repository.UserRepository

@Component
class AuthService(
    private val userRepository: UserRepository,
    meterRegistry: MeterRegistry
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val successfulRegisterMetric =
        meterRegistry
            .counter(
                "user.register",
                listOf(
                    Tag.of("result", "SUCCESS")
                )
            )

    private val errorRegisterMetric =
        meterRegistry
            .counter(
                "user.register",
                listOf(
                    Tag.of("result", "ERROR")
                )
            )

    fun registerUser(request: RegisterUserRequest): String {
        logger.debug("Пришел запрос на регистрацию пользователся: {}", request)
        val user = userRepository.findByEmail(request.email)
        if (user != null) {
            errorRegisterMetric.increment()
            throw BadRequestException("Пользователь c email = ${user.email} уже зарегистрирован")
        }
        userRepository.save(
            User(
                email = request.email,
                password = request.password,
                username = request.username
            )
        )

        val successMessage = "Пользователь успешно зарегистрирован c email = ${request.email}"
        logger.info(successMessage)
        successfulRegisterMetric.increment()
        return successMessage
    }
}
