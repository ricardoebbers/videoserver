package com.ebbers.challenge.videoserver.rest.dto

import com.ebbers.challenge.videoserver.domain.entity.User
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDTO(
        val username: String,
        val password: String = "<redacted>",
        val mobileToken: String?
) {
    companion object {
        fun from(user: User) = UserDTO(
                username = user.username,
                mobileToken = user.mobileToken
        )

        fun from(users: Iterable<User>) = users.map { from(it) }.toList()
    }
}
