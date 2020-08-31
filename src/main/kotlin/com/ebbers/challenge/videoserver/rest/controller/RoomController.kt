package com.ebbers.challenge.videoserver.rest.controller

import com.ebbers.challenge.videoserver.domain.entity.User
import com.ebbers.challenge.videoserver.domain.service.RoomService
import com.ebbers.challenge.videoserver.domain.service.UserService
import com.ebbers.challenge.videoserver.rest.command.CreateRoomCommand
import com.ebbers.challenge.videoserver.rest.dto.RoomDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Tag(name = "Room API")
@RestController
@RequestMapping("/room")
class RoomController(
        private val roomService: RoomService,
        private val userService: UserService
) {
    private val logger = LoggerFactory.getLogger("RoomController")


    @Operation(
            summary = "Get Room info",
            description = "Use this endpoint to get an existing Room detail by it's guid"
    )
    @GetMapping("/{roomGuid}")
    fun getInfo(
            @PathVariable roomGuid: UUID
    ): RoomDTO {
        return RoomDTO.from(roomService.getInfo(roomGuid))
    }

    @Operation(
            summary = "List Rooms by optional username",
            description = "Use this endpoint to list all Rooms, or list the Rooms that the User with username sent as a request parameter is on"
    )
    @GetMapping("/")
    fun listByUsername(
            @RequestParam username: String?
    ): List<RoomDTO> {
        return if (username == null) {
            RoomDTO.from(roomService.findAll())
        } else {
            RoomDTO.from(roomService.findByUsername(username))
        }
    }

    @Operation(
            summary = "Create Room",
            description = "Use this endpoint to create a Room and set yourself as the host."
    )
    @PostMapping("/")
    fun create(
            @RequestBody createRoomCommand: CreateRoomCommand,
            @AuthenticationPrincipal principal: User
    ): RoomDTO {
        return RoomDTO.from(roomService.create(principal, createRoomCommand))
    }

    @Operation(
            summary = "Change Room's host",
            description = "Use this endpoint to change the Room's host. You must be the current host."
    )
    @PatchMapping("/{roomGuid}/changeHost")
    fun changeHost(
            @PathVariable roomGuid: UUID,
            @RequestParam newHostUsername: String,
            @AuthenticationPrincipal principal: User
    ): RoomDTO {
        val newHost = userService.findByUsername(username = newHostUsername)
        return RoomDTO.from(roomService.changeHost(principal, newHost, roomGuid))
    }

    @Operation(
            summary = "Join Room",
            description = "Use this endpoint to join the Room"
    )
    @PatchMapping("/{roomGuid}/join")
    fun join(
            @PathVariable roomGuid: UUID,
            @AuthenticationPrincipal principal: User
    ): RoomDTO {
        return RoomDTO.from(roomService.join(principal, roomGuid))
    }

    @Operation(
            summary = "Leave Room",
            description = "Use this endpoint to leave the Room"
    )
    @PatchMapping("/{roomGuid}/leave")
    fun leave(
            @PathVariable roomGuid: UUID,
            @AuthenticationPrincipal principal: User
    ): RoomDTO {
        return RoomDTO.from(roomService.leave(principal, roomGuid))
    }
}
