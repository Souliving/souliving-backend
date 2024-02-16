package souliving.backend.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.dto.FillUserDto
import souliving.backend.dto.UserDto
import souliving.backend.logger.logResponse
import souliving.backend.mapper.toDto
import souliving.backend.service.UserService

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
class UserController(private var userService: UserService) {
    @GetMapping("/name/{name}")
    suspend fun getUserByName(@PathVariable name: String): UserDto? =
        userService.findUserByName(name)?.let {
            logResponse("get user by name", it.toDto().toString())
            it.toDto()
        }
            ?: throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "User with this $name name doesn't exist"
            )

    @GetMapping("/")
    fun getAllUsers(): Flow<UserDto?> =
        userService.findAll().map {
            it.toDto()
        }.also { logResponse("Find all users") }

    @GetMapping("/{id}")
    suspend fun getUserById(@PathVariable id: Long): UserDto? =
        userService.findById(id)?.let {
            logResponse("get user by id", it.toDto().toString())
            it.toDto()
        }
            ?: throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "User with this $id name doesn't exist"
            )

    @PostMapping("/fillUser/{id}")
    suspend fun fillUserById(@PathVariable id: Long, @RequestBody userDto: FillUserDto): ResponseEntity<*> {
        val res = userService.fillUserById(id, userDto)
        if (!res) {
            throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Problem with user filling"
            )
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successful fill user with id: $id")
    }
}


