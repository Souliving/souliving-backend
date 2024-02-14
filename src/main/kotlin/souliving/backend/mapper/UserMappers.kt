package souliving.backend.mapper

import souliving.backend.dto.CreateUserDto
import souliving.backend.dto.FillUserDto
import souliving.backend.dto.UserDto
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
        name = "",
        enabled = true,
        createDate = createDate,
        modifyDate = createDate
    )
}

fun CreateUserDto.toEntityAdmin(): User {
    val createDate = LocalDateTime.now()
    return User(
        email = this.email,
        password = this.password,
        role = UserRole.ADMIN,
        name = "",
        enabled = true,
        createDate = createDate,
        modifyDate = createDate
    )
}

fun FillUserDto.toUser(user: User): User {
    val modifyDate = LocalDateTime.now()
    return User(
        id = user.id,
        email = user.email,
        password = user.password,
        role = user.role,
        name = this.name,
        enabled = user.enabled,
        createDate = user.createDate,
        modifyDate = modifyDate
    )
}
