package com.kamiz.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class Product(
    val id: Int = -1,
    val categoryId: Int = -1,
    val name: String,
    val photoUrl: String,
    val price: Int,
)

object ProductTable : IntIdTable() {
    val categoryId = reference("categoryId", CategoryTable)
    val name = varchar("name", 128)
    val photoUrl = varchar("photoUrl", 2048)
    val price = integer("price")
}

class ProductEntity(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, ProductEntity>(ProductTable)

    var category by CategoryEntity referencedOn ProductTable.categoryId
    var name by ProductTable.name
    var photoUrl by ProductTable.photoUrl
    var price by ProductTable.price

    fun toModel() = Product(
        id = id.value,
        categoryId = category.id.value,
        name = name,
        photoUrl = photoUrl,
        price = price,
    )
}

