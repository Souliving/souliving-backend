package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.flow
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import souliving.backend.dto.*
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
    private val databaseClient: DatabaseClient,
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

    suspend fun getInfoForLKByUserId(userId: Long): List<LKInfo>? {
        val result =
            databaseClient.sql("select * from get_lk_info_for_user_id(:userId)").bind("userId", userId).fetch()
                .flow().toList()
        val info = result.map { it.parseToLKInfo() }
        return info

    }

    suspend fun Map<String, Any>.parseToLKInfo(): LKInfo {
        return LKInfo(
            formId = this["formId"] as Long,
            cityName = this["cityName"] as String,
            metroNames = this["metroNames"].toString().parseMetroNames(),
        )
    }

    private fun String.parseMetroNames(): List<String> {
        var tmp = this.drop(2)
        tmp = tmp.dropLast(2)
        return tmp.filter { it != '"' }.split(",").map { it.trim() }
    }
}
