package com.ebbers.challenge.videoserver.domain.service

import com.ebbers.challenge.videoserver.domain.entity.Room
import com.ebbers.challenge.videoserver.domain.entity.User
import com.ebbers.challenge.videoserver.rest.command.CreateRoomCommand
import java.util.*

interface RoomService {
    fun create(principal: User, createRoomCommand: CreateRoomCommand): Room
    fun changeHost(principal: User, newHost: User, roomGuid: UUID): Room
    fun join(principal: User, roomGuid: UUID): Room
    fun leave(principal: User, roomGuid: UUID): Room
    fun getInfo(roomGuid: UUID): Room
    fun findByUsername(username: String): List<Room>
    fun findAll(): List<Room>
}
