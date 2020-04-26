package me.alejandro.mtgspoileralert.domain.model.set

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SetDao {
    @get:Query("SELECT * FROM sets")
    val all: List<Set>

    @Insert
    fun insertAll(vararg sets: Set)
}