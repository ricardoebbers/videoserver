package com.ebbers.challenge.videoserver.domain.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
data class Room(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        val guid: UUID = UUID.randomUUID(),
        val name: String,
        val capacityLimit: Int,
        @ManyToOne
        var hostUser: User,
        @ManyToMany
        val participants: MutableSet<User> = mutableSetOf()
)
