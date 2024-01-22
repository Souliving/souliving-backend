package souliving.backend.controller


import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import souliving.backend.dto.AuthUserDetails
import souliving.backend.dto.CreateUserDto
import souliving.backend.jwt.JwtTokenDetails
import souliving.backend.service.SecurityService
import souliving.backend.service.UserService

@RequestMapping("api/v1/auth")
@RestController
class AuthController(val userService: UserService, val securityService: SecurityService) {

    @PostMapping("register")
    suspend fun register(@RequestBody createUserDto: CreateUserDto) {
        userService.createUser(createUserDto)
    }

    @PostMapping("registerAdmin")
    suspend fun registerAdmin(@RequestBody createUserDto: CreateUserDto) {
        userService.createUserAdmin(createUserDto)
    }

    @PostMapping("login")
    suspend fun authenticate(@RequestBody authUserDetails: AuthUserDetails): JwtTokenDetails {
        return securityService.authenticate(authUserDetails.email, authUserDetails.password)
    }
}
