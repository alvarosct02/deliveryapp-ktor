package com.kamiz.features.auth

import com.kamiz.data.ProductDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.getProducts(
    productDataSource: ProductDataSource
) {
    get("products") {
        val categoryId = call.parameters["categoryId"]?.toIntOrNull()
        if (categoryId != null) {
            call.respond(
                status = HttpStatusCode.OK,
                message = productDataSource.getProductsByCategory(categoryId)
            )
            return@get
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = productDataSource.getAllProducts()
        )
    }
    get("search") {
        val name = call.parameters["name"]
        if (!name.isNullOrEmpty()) {
            call.respond(
                status = HttpStatusCode.OK,
                message = productDataSource.getProductsByName(name)
            )
            return@get
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = productDataSource.getAllProducts()
        )
    }
}