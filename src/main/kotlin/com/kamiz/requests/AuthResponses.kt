package com.kamiz.requests

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
)