package souliving.backend.mapper

import souliving.backend.dto.JwtDTO
import souliving.backend.dto.LoginAnswerDto
import souliving.backend.jwt.JwtTokenDetails

fun JwtTokenDetails.toLoginAnswerDto(): LoginAnswerDto {
    return LoginAnswerDto(
        JwtDTO(
            this.userId,
            this.token,
            this.refreshToken,
            this.issueDate,
            this.expirationDate
        ),
        this.email,
        this.name
    )
}
