package com.ebbers.challenge.videoserver.rest.command

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class LoginCommand(
        val username: String,
        val password: String
)
