package com.kamiz.plugins

import com.kamiz.data.UserDataSource
import com.kamiz.data.local.DatabaseFactory
import com.kamiz.features.auth.signIn
import com.kamiz.security.hashing.HashingService
import com.kamiz.security.token.TokenConfig
import com.kamiz.security.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig,
) {

    routing {
        get("/") {
            val users = DatabaseFactory.userDao.all()
            call.respondText("Users: ${users.size}")
        }
        signIn(userDataSource, hashingService, tokenService, tokenConfig)

    }
}
