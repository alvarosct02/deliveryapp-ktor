package com.kamiz.models

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert

data class CartLine(
    val id: Int = -1,
    val productId: Int,
    val quantity: Int,
)
