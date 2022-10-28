package com.kamiz.plugins

import com.kamiz.data.CategoryDataSource
import com.kamiz.data.UserDataSource
import com.kamiz.data.local.DatabaseFactory
import com.kamiz.data.local.DatabaseFactory.dbQuery
import com.kamiz.features.auth.getCategories
import com.kamiz.features.auth.signIn
import com.kamiz.models.CategoryEntity
import com.kamiz.security.hashing.HashingService
import com.kamiz.security.token.TokenConfig
import com.kamiz.security.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    categoryDataSource: CategoryDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig,
) {

    routing {
        get("/") {
            val users = DatabaseFactory.userDao.all()
            call.respond(
                mapOf(
                    "Users" to users.size,
                    "Categories" to dbQuery{  CategoryEntity.all().count() }.toInt()
                )
            )
        }
//        Auth(User)
        signIn(userDataSource, hashingService, tokenService, tokenConfig)

//        Categories
        getCategories(categoryDataSource)
    }
}
