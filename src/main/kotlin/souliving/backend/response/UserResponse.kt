package souliving.backend.response

data class UserResponse(
    val id: Long,
    val email: String,
    val name: String,
    val surname: String,
)
