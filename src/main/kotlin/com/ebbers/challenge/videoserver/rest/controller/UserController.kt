package com.ebbers.challenge.videoserver.rest.controller

import com.ebbers.challenge.videoserver.domain.entity.User
import com.ebbers.challenge.videoserver.domain.service.UserService
import com.ebbers.challenge.videoserver.rest.command.UpdateUserCommand
import com.ebbers.challenge.videoserver.rest.dto.UserDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User API")
@RestController
@RequestMapping("/user")
class UserController(
        private val service: UserService
) {
    private val logger = LoggerFactory.getLogger("UserController")

    @Operation(
            summary = "List Users by optional username",
            description = "Use this endpoint to list all Users or filter the list by Username"
    )
    @GetMapping("/")
    fun list(@RequestParam username: String?): List<UserDTO> {
        return if (username != null) {
            UserDTO.from(service.findAllByUsername(username))
        } else {
            UserDTO.from(service.findAll())
        }
    }

    @Operation(
            summary = "Update User",
            description = "Use this endpoint to update the logged in User details."
    )
    @PatchMapping("/{username}")
    fun updateUser(
            @PathVariable username: String,
            @RequestBody userCommand: UpdateUserCommand,
            @AuthenticationPrincipal principal: User
    ): ResponseEntity<UserDTO> {
        return if (userCommand.valid && principal.username == username) {
            val user = userCommand.toEntity(username)
            ResponseEntity.ok(UserDTO.from(service.updateUser(user)))
        } else {
            ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build()
        }
    }

    @Operation(
            summary = "Delete User",
            description = "Use this endpoint to delete the logged in User."
    )
    @DeleteMapping("/{username}")
    fun deleteUser(
            @PathVariable username: String,
            @AuthenticationPrincipal principal: User
    ): ResponseEntity<Unit> {
        return if (principal.username == username) {
            service.deleteUser(username)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build()
        }
    }
}
