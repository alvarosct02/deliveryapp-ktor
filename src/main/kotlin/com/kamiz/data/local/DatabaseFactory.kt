package com.kamiz.data.local

import com.kamiz.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db3"
        val database = Database.connect(jdbcURL, driverClassName)
        transaction(database) {
            SchemaUtils.create(UserTable, CategoryTable, ProductTable)
            seedDb()
        }
    }

    private fun seedDb() {
        userDao.apply {
            runBlocking {
                if (all().isEmpty()) {
                    add(User(-1, "Admin", "Admin", "admin", "045342a1353736ebd40791e91c0e97502fec589e"))
                    add(User(-1, "Admin", "Admin", "maryam", "296edbed74404a3b6045cf8c50732ae6da330829"))
                }
            }
        }
        CategoryEntity.apply {
            if (all().count() != 0L) return@apply
            new (1){
                name = "Lacteos"
                photoUrl = "https://cdn-icons-png.flaticon.com/512/911/911172.png"
            }
            new (2){
                name = "Abarrotes"
                photoUrl = "https://cdn0.iconfinder.com/data/icons/food-icons-rounded/110/Cookies-512.png"
            }
            new (3){
                name = "Frutas"
                photoUrl = "https://freeiconshop.com/wp-content/uploads/edd/pear-flat.png"
            }
            new (4){
                name = "Verduras"
                photoUrl = "https://cdn-icons-png.flaticon.com/512/3058/3058995.png"
            }
        }
        ProductEntity.apply {
            if (all().count() != 0L) return@apply
            new (1){
                category = CategoryEntity.findById(1)!!
                name = "Lechita pa ti"
                photoUrl = "https://e39a9f00db6c5bc097f9-75bc5dce1d64f93372e7c97ed35869cb.ssl.cf1.rackcdn.com/img-VL4OOhGb.jpg?wid=1500&hei=1500&qlt=70"
                price = 1500
            }
            new (2){
                category = CategoryEntity.findById(1)!!
                name = "Helado deatras"
                photoUrl = "https://plazavea.vteximg.com.br/arquivos/ids/512750-1000-1000/20196120.jpg"
                price = 2000
            }
            new (3){
                category = CategoryEntity.findById(3)!!
                name = "Tu Papayita (1 Kg)"
                photoUrl = "https://portal.andina.pe/EDPfotografia3/Thumbnail/2022/02/22/000848026W.jpg"
                price = 600
            }
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    val userDao by lazy { UserDao() }
}