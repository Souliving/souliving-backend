package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import souliving.backend.dto.CreateUserDto
import souliving.backend.dto.UserDto
import souliving.backend.exception.UserNotFoundException
import souliving.backend.mapper.toDto
import souliving.backend.mapper.toEntityAdmin
import souliving.backend.mapper.toEntityUser
import souliving.backend.model.User
import souliving.backend.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder
) {
    suspend fun findUserByName(name: String): User? = userRepository.findByName(name)

    fun findAll(): Flow<User> = userRepository.findAll()

    suspend fun findById(id: Long): User? = userRepository.findById(id)

    suspend fun createUser(createUserDto: CreateUserDto) {
        val userEntity = createUserDto.apply { password = passwordEncoder.encode(password) }.toEntityUser()
        userRepository.save(userEntity)
    }

    suspend fun createUserAdmin(createUserDto: CreateUserDto) {
        val userEntity = createUserDto.apply { password = passwordEncoder.encode(password) }.toEntityAdmin()
        userRepository.save(userEntity)
    }

    suspend fun findByEmail(email: String): UserDto? =
        userRepository.findByEmail(email)?.toDto()
            ?: throw UserNotFoundException("User for email $email doesn't exist")
}
