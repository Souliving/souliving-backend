package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.springframework.stereotype.Service
import souliving.backend.model.User
import souliving.backend.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    suspend fun findUserByName(name: String): User? = userRepository.findByName(name).firstOrNull()

    fun findAll(): Flow<User> = userRepository.findAll()

    suspend fun findById(id: Long): User? = userRepository.findById(id)
}
