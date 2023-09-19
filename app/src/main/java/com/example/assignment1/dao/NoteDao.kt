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

    @Delete
    suspend fun deleteNote(note: Note) : Int

    @Update
    suspend fun updateNote(note: Note) : Int

    @Query("UPDATE Note SET noteTitle= :title, description= :description WHERE noteId = :noteId")
    suspend fun updateNoteSpecificField(noteId:String, title:String, description:String) : Int


}