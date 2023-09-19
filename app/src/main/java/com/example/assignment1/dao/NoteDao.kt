package com.example.assignment1.dao

import androidx.room.*
import com.example.assignment1.models.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(note: Note): Long
}