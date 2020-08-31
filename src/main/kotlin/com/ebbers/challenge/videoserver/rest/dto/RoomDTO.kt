package com.ebbers.challenge.videoserver.rest.dto

import com.ebbers.challenge.videoserver.domain.entity.Room
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RoomDTO(
        val guid: UUID,
        val name: String,
        val capacityLimit: Int,
        val hostUserDTO: UserDTO,
        val participants: List<UserDTO>
) {
    companion object {
        fun from(room: Room) = RoomDTO(
                guid = room.guid,
                name = room.name,
                capacityLimit = room.capacityLimit,
                hostUserDTO = UserDTO.from(room.hostUser),
                participants = UserDTO.from(room.participants)
        )

        fun from(rooms: Iterable<Room>) = rooms.map { from(it) }.toList()
    }
}
