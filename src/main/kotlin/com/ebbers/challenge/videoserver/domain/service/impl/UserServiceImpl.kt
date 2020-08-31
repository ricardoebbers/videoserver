package com.ebbers.challenge.videoserver.domain.service.impl

import com.ebbers.challenge.videoserver.domain.entity.User
import com.ebbers.challenge.videoserver.domain.repository.UserRepository
import com.ebbers.challenge.videoserver.domain.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
        private val repository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun loadUserByUsername(username: String): UserDetails {
        return repository
                .findByUsername(username)
                ?: throw UsernameNotFoundException("User $username cannot be found.")
    }

    override fun signUp(user: User): User {
        if (repository.findByUsername(user.username) != null) {
            throw IllegalArgumentException("User ${user.username} already exists!")
        }
        val encrypted = passwordEncoder.encode(user.password)
        return repository.save(user.copy(password = encrypted))
    }

    override fun findAll(): List<User> {
        return repository.findAll()
    }

    override fun findAllByUsername(username: String): List<User> {
        return repository.findAllByUsername(username)
    }

    override fun findByUsername(username: String): User {
        return repository.findByUsername(username)
                ?: throw IllegalArgumentException("User $username not found!")
    }

    override fun updateUser(user: User): User {
        val fromDB = repository.findByUsername(user.username)
                ?: throw IllegalArgumentException("User ${user.username} not found!")
        return repository.save(updateNonNull(user, fromDB))
    }

    override fun deleteUser(username: String) {
        val fromDB = repository.findByUsername(username)
                ?: throw IllegalArgumentException("User $username not found!")
        repository.delete(fromDB)
    }

    private fun updateNonNull(user: User, fromDB: User): User {
        val password = user.password?.let { passwordEncoder.encode(it) } ?: fromDB.password
        val mobileToken = user.mobileToken ?: fromDB.mobileToken
        return fromDB.copy(password = password, mobileToken = mobileToken)
    }
}
