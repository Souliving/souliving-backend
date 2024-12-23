package souliving.backend.jwt

import java.security.Principal

data class CustomPrincipal(val id: Long, val email: String) : Principal {
    override fun getName(): String {
        return email
    }
}
