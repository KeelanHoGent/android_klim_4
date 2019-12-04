package com.klimaatmobiel.data.database

import android.content.Context
import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM databaseproduct WHERE projectId = :projectkey AND productId = :productkey")
    fun getProduct(projectkey: Long, productkey: Long): DatabaseProduct

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<DatabaseProduct>)
}

@Dao
interface ProjectDao {
    @Query("SELECT * FROM databaseproduct WHERE projectId = :projectkey")
    fun getProduct(projectkey: Long): DatabaseProject

}

@Database(entities = [DatabaseProduct::class, DatabaseProject::class], version = 3, exportSchema = false)
abstract class WebshopDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val projectDao: ProjectDao
}

private lateinit var INSTANCE: WebshopDatabase

fun getDatabase(context: Context): WebshopDatabase {
    synchronized(WebshopDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                WebshopDatabase::class.java,
                "webshop").fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}

