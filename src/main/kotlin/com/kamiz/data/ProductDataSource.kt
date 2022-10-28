package com.kamiz.data

import com.kamiz.data.local.DatabaseFactory.dbQuery
import com.kamiz.models.CategoryEntity
import com.kamiz.models.Product
import com.kamiz.models.ProductEntity

class ProductDataSource() {
    suspend fun getAllProducts(): List<Product> = dbQuery {
        ProductEntity.all().map { it.toModel() }
    }

    suspend fun getProductsByCategory(id: Int): List<Product> = dbQuery {
        CategoryEntity.findById(id)?.products?.map { it.toModel() }.orEmpty()
    }
}
