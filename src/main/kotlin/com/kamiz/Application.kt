package com.kamiz

import com.kamiz.data.CategoryDataSource
import com.kamiz.data.ProductDataSource
import com.kamiz.data.UserDataSource
import com.kamiz.data.local.DatabaseFactory
import com.kamiz.plugins.configureRouting
import com.kamiz.plugins.configureSecurity
import com.kamiz.plugins.configureSerialization
import com.kamiz.security.hashing.SHA256HashingService
import com.kamiz.security.token.JwtTokenService
import com.kamiz.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.util.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@OptIn(KtorExperimentalAPI::class)
@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    val tokenConfig = TokenConfig(
        issuer = "https://kamiz.com",
        audience = "https://kamiz.com",
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )

//    DataSources
    val userDataSource = UserDataSource()
    val categoryDataSource = CategoryDataSource()
    val productDataSource = ProductDataSource()

    val tokenService = JwtTokenService()
    val hashingService = SHA256HashingService()

    DatabaseFactory.init()
    configureSerialization()
    configureSecurity(tokenConfig)
    configureRouting(userDataSource, categoryDataSource, productDataSource, hashingService, tokenService, tokenConfig)
}
