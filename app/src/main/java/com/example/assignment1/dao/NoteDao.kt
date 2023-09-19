package com.example.assignment1.dao

import androidx.room.*
import com.example.assignment1.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note ORDER BY date DESC" )
    fun getNoteList() : Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(note: Note): Long
}