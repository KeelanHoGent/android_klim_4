package com.klimaatmobiel.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM databaseproduct WHERE projectId = :projectkey AND productId = :productkey")
    fun getProduct(projectkey: Long, productkey: Long): DatabaseProduct

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<DatabaseProduct>)
}

@Database(entities = [DatabaseProduct::class], version = 2, exportSchema = false)
abstract class ProductsDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
}

private lateinit var INSTANCE: ProductsDatabase

fun getDatabase(context: Context): ProductsDatabase {
    synchronized(ProductsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ProductsDatabase::class.java,
                "products").fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}

