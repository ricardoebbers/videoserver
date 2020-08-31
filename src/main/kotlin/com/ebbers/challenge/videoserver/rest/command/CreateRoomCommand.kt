package com.ebbers.challenge.videoserver.rest.command

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateRoomCommand(
        val name: String,
        val capacity: Int?
)
