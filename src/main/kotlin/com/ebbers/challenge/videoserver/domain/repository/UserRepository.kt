package com.ebbers.challenge.videoserver.domain.repository

import com.ebbers.challenge.videoserver.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findAllByUsername(username: String): List<User>
}
