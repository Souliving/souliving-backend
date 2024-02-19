package souliving.backend.unit.service

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.crypto.password.PasswordEncoder
import souliving.backend.dto.CreateUserDto
import souliving.backend.mapper.toDto
import souliving.backend.model.User
import souliving.backend.model.UserRole
import souliving.backend.repository.UserRepository
import souliving.backend.service.UserService
import java.time.LocalDateTime

@SpringBootTest(classes = [UserService::class])
class UserServiceTest(
    @MockBean
    private var userRepository: UserRepository,
    @MockBean
    private var passwordEncoder: PasswordEncoder,
    @Autowired
    private var userService: UserService
) : ShouldSpec({

    extension(SpringExtension)

    should("Get user by id should return the user") {
        val userId = 1L
        val expectedUser = User(
            userId, "",
            "", UserRole.USER,
            "", true,
            LocalDateTime.now(), LocalDateTime.now()
        )

        given(userRepository.findById(1)).willReturn(expectedUser)

        val result = userService.findById(userId)

        result shouldBe expectedUser
    }

    should("Get user by email should return the user") {
        val email = "email@gmail.com"
        val expectedUser = User(
            0, email,
            "", UserRole.USER,
            "", true,
            LocalDateTime.now(), LocalDateTime.now()
        )

        given(userRepository.findByEmail(email)).willReturn(expectedUser)

        val result = userService.findByEmail(email)

        result shouldBe  expectedUser.toDto()
    }

    /*should("Create user should return id of new user and make new user") {
        val expectedUser = User(
            0, "",
            "pass", UserRole.USER,
            "", true,
            LocalDateTime.now(), LocalDateTime.now()
        )

        given(userRepository.save(expectedUser)).willReturn(expectedUser)

        val result = userService.createUser(CreateUserDto(expectedUser.email, expectedUser.password))

        result shouldBe expectedUser.id
    }*/

   /* should("Fill user by id should return and save fill user") {
        val expectedUser = User(
            0, "email",
            "pass", UserRole.USER,
            "", true,
            LocalDateTime.now(), LocalDateTime.now()
        )
    }*/

})
