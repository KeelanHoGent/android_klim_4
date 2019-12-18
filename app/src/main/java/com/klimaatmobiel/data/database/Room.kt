package com.klimaatmobiel.data.database

import android.content.Context
import androidx.room.*
import com.klimaatmobiel.data.converter.Converters

@Dao
interface ProductDao {
    @Query("SELECT * FROM databaseproduct WHERE projectId = :projectkey AND productId = :productkey")
    fun getProduct(projectkey: Long, productkey: Long): DatabaseProduct

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<DatabaseProduct>)
}

@Dao
interface ProjectDao {
    @Query("SELECT * FROM databaseproject WHERE projectId = :projectkey")
    fun getProject(projectkey: Long): DatabaseProject

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(project: DatabaseProject)

}

@Database(entities = [DatabaseProduct::class, DatabaseProject::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WebshopDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val projectDao: ProjectDao
}



