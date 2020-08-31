package com.ebbers.challenge.videoserver.domain.service.impl

import com.ebbers.challenge.videoserver.domain.entity.Room
import com.ebbers.challenge.videoserver.domain.entity.User
import com.ebbers.challenge.videoserver.domain.repository.RoomRepository
import com.ebbers.challenge.videoserver.domain.service.RoomService
import com.ebbers.challenge.videoserver.rest.command.CreateRoomCommand
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoomServiceImpl(
        private val repository: RoomRepository
) : RoomService {
    companion object {
        private const val DEFAULT_ROOM_CAPACITY = 5
    }

    override fun create(principal: User, createRoomCommand: CreateRoomCommand): Room {
        val room = Room(
                name = createRoomCommand.name,
                capacityLimit = createRoomCommand.capacity ?: DEFAULT_ROOM_CAPACITY,
                hostUser = principal,
                participants = mutableSetOf(principal)
        )
        return repository.save(room)
    }

    override fun changeHost(principal: User, newHost: User, roomGuid: UUID): Room {
        return actOnRoomIfExists(roomGuid) {
            if (it.hostUser.id != principal.id) {
                throw IllegalArgumentException("Logged in user is not host.")
            } else {
                repository.save(it.copy(hostUser = principal))
            }
        }
    }

    private fun actOnRoomIfExists(roomGuid: UUID, action: (Room) -> Room): Room {
        return repository.findByGuid(roomGuid)?.let(action)
                ?: throw IllegalArgumentException("Room with guid $roomGuid doesn't exists.")
    }

    override fun join(principal: User, roomGuid: UUID): Room {
        return actOnRoomIfExists(roomGuid) {
            it.participants.add(principal)
            repository.save(it)
        }
    }

    override fun leave(principal: User, roomGuid: UUID): Room {
        return actOnRoomIfExists(roomGuid) {
            if (it.participants.remove(principal)) {
                repository.save(it)
            } else {
                it
            }
        }
    }

    override fun getInfo(roomGuid: UUID): Room {
        return actOnRoomIfExists(roomGuid) { it }
    }

    override fun findByUsername(username: String): List<Room> {
        return repository.findAllByParticipants_Username(username)
    }

    override fun findAll(): List<Room> {
        return repository.findAll()
    }

}
