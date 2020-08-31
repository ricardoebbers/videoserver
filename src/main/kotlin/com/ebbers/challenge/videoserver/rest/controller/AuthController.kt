package com.ebbers.challenge.videoserver.rest.controller

import com.ebbers.challenge.videoserver.domain.service.UserService
import com.ebbers.challenge.videoserver.rest.command.CreateUserCommand
import com.ebbers.challenge.videoserver.rest.command.LoginCommand
import com.ebbers.challenge.videoserver.rest.dto.UserDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@Tag(name ="Authentication API")
@RestController
@RequestMapping("/auth")
class AuthController(
        private val userService: UserService
) {
    private val logger = LoggerFactory.getLogger("AuthController")

    @Operation(
            summary = "Sign-up",
            description = "Use this endpoint to register and login a new User"
    )
    @PostMapping("/sign-up")
    fun signUp(
            @RequestBody @Valid createUserCommand: CreateUserCommand,
            request: HttpServletRequest
    ): UserDTO? {
        return try {
            val user = createUserCommand.toEntity()
            val createdUser = userService.signUp(user)
            request.login(user.username, user.password)
            UserDTO.from(createdUser)
        } catch (ex: RuntimeException) {
            logger.error(ex.message)
            null
        }
    }

    @Operation(
            summary = "Sign-in",
            description = "Use this endpoint to sign-in the previously created User"
    )
    @PostMapping("/sign-in")
    fun signIn(
            @RequestBody loginCommand: LoginCommand,
            request: HttpServletRequest
    ) {
        return try {
            request.logout()
            request.login(loginCommand.username, loginCommand.password)
        } catch (ex: RuntimeException) {
            logger.error(ex.message)
        }
    }
}
