package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import souliving.backend.dto.CreateAdminDto
import souliving.backend.dto.CreateUserDto
import souliving.backend.dto.FillUserDto
import souliving.backend.dto.UserDto
import souliving.backend.exception.UserNotFoundException
import souliving.backend.logger.log
import souliving.backend.mapper.toDto
import souliving.backend.mapper.toEntityAdmin
import souliving.backend.mapper.toEntityUser
import souliving.backend.mapper.toUser
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

    suspend fun createUser(createUserDto: CreateUserDto): Long? {
        val userEntity = createUserDto.apply { password = passwordEncoder.encode(password) }.toEntityUser()
        userRepository.save(userEntity)
        return userEntity.id
    }

    suspend fun createUserAdmin(createAdminDto: CreateAdminDto): Long {
        val adminEntity = createAdminDto.apply { password = passwordEncoder.encode(password) }.toEntityAdmin()
        userRepository.save(adminEntity)
        return adminEntity.id!!
    }

    suspend fun findByEmail(email: String): UserDto? =
        userRepository.findByEmail(email)?.toDto()
            ?: throw UserNotFoundException("User with email $email doesn't exist")

    suspend fun fillUserById(id: Long, fillUserDto: FillUserDto): Boolean {
        val user = userRepository.findById(id)?.let {
            log("Get user by id for filling ${it.toDto()}")
            it
        } ?: return false

        val fillingUser = fillUserDto.toUser(user)

        userRepository.save(fillingUser)
        return true
    }
}
