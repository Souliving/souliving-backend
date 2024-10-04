package souliving.backend.mapper

import souliving.backend.dto.CreateAdminDto
import souliving.backend.dto.CreateUserDto
import souliving.backend.dto.FillUserDto
import souliving.backend.dto.UserDto
import souliving.backend.model.Gender
import souliving.backend.model.User
import souliving.backend.model.UserRole
import java.time.LocalDateTime

fun User.toDto(): UserDto {
    return UserDto(
        id = this.id!!,
        email = this.email,
        password = this.password,
        role = this.role,
        name = this.name.toString(),
        age = this.age,
        gender = this.gender.toString(),
        enabled = this.enabled,
        createDate = this.createDate,
        modifyDate = this.modifyDate
    )
}

fun CreateUserDto.toEntityUser(): User {
    val createDate = LocalDateTime.now()
    return User(
        email = this.email,
        password = this.password,
        role = UserRole.USER,
        name = this.name,
        age = this.age,
        gender = this.gender,
        enabled = true,
        createDate = createDate,
        modifyDate = createDate
    )
}

fun CreateAdminDto.toEntityAdmin(): User {
    val createDate = LocalDateTime.now()
    return User(
        email = this.email,
        password = this.password,
        role = UserRole.ADMIN,
        name = "",
        age = 0,
        gender = Gender.FEMALE,
        enabled = true,
        createDate = createDate,
        modifyDate = createDate
    )
}

fun FillUserDto.toUser(user: User): User {
    val modifyDate = LocalDateTime.now()
    return User(
        id = user.id,
        email = this.email,
        password = user.password,
        role = user.role,
        name = this.name,
        age = this.age,
        gender = this.gender,
        enabled = user.enabled,
        createDate = user.createDate,
        modifyDate = modifyDate
    )
}
