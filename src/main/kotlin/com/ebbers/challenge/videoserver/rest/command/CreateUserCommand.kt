package com.ebbers.challenge.videoserver.rest.command

import com.ebbers.challenge.videoserver.domain.entity.User
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateUserCommand(
        @field:NotBlank
        private val username: String,
        @field:NotBlank
        @field:Size(min = 8, max = 32)
        private val password: String,
        private val mobileToken: String?
) {
    fun toEntity(): User = User(
            username = username,
            password = password,
            mobileToken = mobileToken
    )
}
