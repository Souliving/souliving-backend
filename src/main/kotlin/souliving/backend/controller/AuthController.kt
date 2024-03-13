package souliving.backend.controller


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import souliving.backend.dto.*
import souliving.backend.logger.logResponse
import souliving.backend.mapper.toLoginAnswerDto
import souliving.backend.service.SecurityService
import souliving.backend.service.UserService

@RequestMapping("api/v1/auth")
@CrossOrigin
@RestController
class AuthController(val userService: UserService, val securityService: SecurityService) {

    @PostMapping("register")
    suspend fun register(@RequestBody createUserDto: CreateUserDto): ResponseEntity<*> {
        logResponse("Create user: $createUserDto")
        val id = userService.createUser(createUserDto)
        return ResponseEntity.status(HttpStatus.OK).body(IdDto(id))
    }

    @PostMapping("registerAdmin")
    suspend fun registerAdmin(@RequestBody createAdminDto: CreateAdminDto): ResponseEntity<*> {
        logResponse("Create admin: $createAdminDto")
        val id = userService.createUserAdmin(createAdminDto)
        return ResponseEntity.status(HttpStatus.OK).body(IdDto(id))
    }

    @PostMapping("login")
    suspend fun authenticate(@RequestBody authUserDetails: AuthUserDetails): LoginAnswerDto {
        val jwt = withContext(Dispatchers.IO) {
            async {
                securityService.authenticate(authUserDetails.email, authUserDetails.password)
            }
        }

        val jwtAnswer = jwt.await()

        logResponse("Login with email: ${authUserDetails.email} and password: ${authUserDetails.password}")

        return jwtAnswer.toLoginAnswerDto()
    }
}
