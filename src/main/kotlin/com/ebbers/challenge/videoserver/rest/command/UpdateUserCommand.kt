package com.ebbers.challenge.videoserver.rest.command

import com.ebbers.challenge.videoserver.domain.entity.User
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.Size

@JsonIgnoreProperties(ignoreUnknown = true)
data class UpdateUserCommand(
        @field:Size(min = 8, max = 32)
        val password: String?,
        val mobileToken: String?
) {
    @JsonIgnore
    val valid = password != null || mobileToken != null

    fun toEntity(username: String) = User(
            username = username,
            password = password,
            mobileToken = mobileToken
    )
}
