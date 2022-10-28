package com.kamiz.requests

import com.kamiz.models.User
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
)

@Serializable
data class UserResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
) {
    companion object {
        fun fromUser(user: User) = UserResponse(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
        )
    }
}