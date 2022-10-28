package com.kamiz.models

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert

data class Product(
    val id: Int = -1,
    val categoryId: Int = -1,
    val name: String,
    val photoUrl: String,
    val price: Int,
)

