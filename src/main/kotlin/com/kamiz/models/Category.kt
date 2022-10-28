package com.kamiz.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class Category(
    val id: Int = -1,
    val name: String,
    val photoUrl: String,
)

object CategoryTable : IntIdTable() {
    val name = varchar("firstName", 128)
    val photoUrl = varchar("lastName", 2048)
}

class CategoryEntity(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, CategoryEntity>(CategoryTable)

    var name by CategoryTable.name
    var photoUrl by CategoryTable.photoUrl
    val products by ProductEntity referrersOn ProductTable.categoryId

    fun toModel() = Category(
        id = id.value,
        name = name,
        photoUrl = photoUrl,
    )
}