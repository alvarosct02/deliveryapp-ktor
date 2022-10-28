package com.kamiz.features.auth

import com.kamiz.data.CategoryDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.getCategories(
    categoryDataSource: CategoryDataSource
) {
    get("categories") {
        call.respond(
            status = HttpStatusCode.OK,
            message = categoryDataSource.getAllCategories()
        )
    }
}