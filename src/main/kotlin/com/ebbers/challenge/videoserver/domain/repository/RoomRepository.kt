package com.ebbers.challenge.videoserver.domain.repository

import com.ebbers.challenge.videoserver.domain.entity.Room
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoomRepository : JpaRepository<Room, Long> {
    fun findByGuid(roomGuid: UUID): Room?

    fun findAllByParticipants_Username(username: String): List<Room>

}
