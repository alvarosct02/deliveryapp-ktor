package com.kamiz.features.auth

import com.kamiz.data.UserDataSource
import com.kamiz.requests.LoginRequest
import com.kamiz.requests.LoginResponse
import com.kamiz.security.hashing.HashingService
import com.kamiz.security.token.TokenClaim
import com.kamiz.security.token.TokenConfig
import com.kamiz.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.signIn(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    post("signin") {
        val request = call.receiveOrNull<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val user = userDataSource.getUserByUsername(request.email.orEmpty())
        if (user == null) {
            call.respond(HttpStatusCode.Conflict, "Incorrect username or password")
            return@post
        }

        val password = request.password.orEmpty()
        val isValidPassword = hashingService.verify(
            value = password,
            user.passwordHash
        )
        if (!isValidPassword) {
            println("Entered hash: ${hashingService.generateHash(password)}, Hashed PW: ${user.passwordHash}")
            call.respond(HttpStatusCode.Conflict, "Incorrect username or password")
            return@post
        }

        val token = tokenService.generate(
            config = tokenConfig,
            TokenClaim(
                name = "userId",
                value = user.id.toString()
            )
        )

        call.respond(
            status = HttpStatusCode.OK,
            message = LoginResponse(
                token = token
            )
        )
    }
}