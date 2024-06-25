package com.code.kembang_telon.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.code.kembang_telon.data.local.entity.CardEntity

@Dao
interface CardDao {

    @Query("SELECT * FROM card_list order by id asc")
    fun getAll(): LiveData<List<CardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(vararg card: CardEntity)

    @Delete
     fun delete(card: CardEntity)

    @Update
     fun update(vararg card: CardEntity)
}