package com.ebbers.challenge.videoserver.domain.service

import com.ebbers.challenge.videoserver.domain.entity.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {
    fun signUp(user: User): User
    fun findAll(): List<User>
    fun findAllByUsername(username: String): List<User>
    fun findByUsername(username: String): User
    fun updateUser(user: User): User
    fun deleteUser(username: String)
}
