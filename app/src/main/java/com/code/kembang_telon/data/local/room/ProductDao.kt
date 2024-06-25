package com.code.kembang_telon.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.code.kembang_telon.data.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM cart_items order by id desc")
    fun getAll(): LiveData<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg product: ProductEntity)

    @Delete
     fun delete(product: ProductEntity)

    @Update
     fun update(vararg product: ProductEntity)
}