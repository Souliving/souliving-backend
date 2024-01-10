package souliving.backend.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.logger.logResponse
import souliving.backend.model.User
import souliving.backend.response.UserResponse
import souliving.backend.service.UserService

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
class UserController(private var userService: UserService) {
    @GetMapping("/name/{name}")
    suspend fun getUserByName(@PathVariable name: String): UserResponse? =
        userService.findUserByName(name)?.let {
            logResponse("get user by name", it.toResponse().toString())
            it.toResponse()
        }
            ?: throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "User with this $name name doesn't exist"
            )

    @GetMapping("/")
    fun getAllUsers(): Flow<UserResponse?> =
        userService.findAll().map {
            it.toResponse()
        }.also { logResponse("Find all users") }

    @GetMapping("/{id}")
    suspend fun getUserById(@PathVariable id: Long): UserResponse? =
        userService.findById(id)?.let {
            logResponse("get user by id", it.toResponse().toString())
            it.toResponse()
        }
            ?: throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "User with this $id name doesn't exist"
            )
}

fun User.toResponse(): UserResponse =
    UserResponse(
        id = this.id!!,
        email = this.email,
        name = this.name,
        surname = this.surname,
    )
