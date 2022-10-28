package com.kamiz.features.auth

import com.kamiz.data.UserDataSource
import com.kamiz.models.User
import com.kamiz.requests.*
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
    post("login") {
        val request = call.receiveNullable<LoginRequest>() ?: run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val user = userDataSource.getUserByUsername(request.email)
        if (user == null) {
            call.respond(HttpStatusCode.Conflict, ApiError("Incorrect username or password"))
            return@post
        }

        val password = request.password
        val isValidPassword = hashingService.verify(
            value = password,
            user.passwordHash
        )
        if (!isValidPassword) {
            println("Entered hash: ${hashingService.generateHash(password)}, Hashed PW: ${user.passwordHash}")
            call.respond(HttpStatusCode.Conflict, ApiError("Incorrect username or password"))
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

    post("sign_up") {
        val request = call.receiveNullable<SignUpRequest>() ?: run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val user = userDataSource.getUserByUsername(request.email)
        if (user != null) {
            call.respond(HttpStatusCode.Conflict, ApiError("Email is already in use"))
            return@post
        }

        val passwordHash = hashingService.generateHash(request.password)

        val newUser = userDataSource.createUser(
            User(
                firstName = request.firstName,
                lastName = request.lastName,
                email = request.email,
                passwordHash = passwordHash,
            )
        )

        if (newUser == null) {
            call.respond(HttpStatusCode.Conflict, ApiError("Cannot create new user"))
        } else {
            call.respond(
                status = HttpStatusCode.OK,
                message = UserResponse.fromUser(newUser)
            )
        }
    }
}