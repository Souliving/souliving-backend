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
        val authUser = AuthUserDetails(createUserDto.email, createUserDto.password)
        val id = userService.createUser(createUserDto)
        logResponse("Create user: $createUserDto")
        id?.let {
            val answer = loginUser(authUser)
            return ResponseEntity.status(HttpStatus.OK)
                .body(
                    answer
                )
        } ?: return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Problem with register user with email: ${createUserDto.email}")
    }

    @PostMapping("registerAdmin")
    suspend fun registerAdmin(@RequestBody createAdminDto: CreateAdminDto): ResponseEntity<*> {
        logResponse("Create admin: $createAdminDto")
        val id = userService.createUserAdmin(createAdminDto)
        return ResponseEntity.status(HttpStatus.OK).body(IdDto(id))
    }

    @PostMapping("login")
    suspend fun authenticate(@RequestBody authUserDetails: AuthUserDetails): LoginAnswerDto {
        return loginUser(authUserDetails)
    }

    private suspend fun loginUser(authUserDetails: AuthUserDetails): LoginAnswerDto {
        val jwt = withContext(Dispatchers.IO) {
            async {
                securityService.authenticate(authUserDetails.email, authUserDetails.password)
            }
        }

        val jwtAnswer = jwt.await()

        logResponse("Login with email: ${authUserDetails.email}")

        return jwtAnswer.toLoginAnswerDto()
    }
}
