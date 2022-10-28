package com.kamiz.data

import com.kamiz.data.local.DatabaseFactory.dbQuery
import com.kamiz.models.Category
import com.kamiz.models.CategoryEntity

class CategoryDataSource() {
    suspend fun getAllCategories(): List<Category> = dbQuery {
        CategoryEntity.all().map { it.toModel() }
    }
}
