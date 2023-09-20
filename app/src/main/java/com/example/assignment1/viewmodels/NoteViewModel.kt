package com.example.assignment1.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Query
import com.example.assignment1.models.Note
import com.example.assignment1.repository.NoteRepository
import com.example.assignment1.utils.Resource

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application)

    val noteStateFlow get() = noteRepository.noteStateFlow
    val statusLiveData get() = noteRepository.statusLiveData

    fun getNoteList() {
        noteRepository.getNoteList()
    }
    fun insertNote(note: Note) {
        noteRepository.insertNote(note)
    }

    fun deleteNote(note: Note) {
         noteRepository.deleteNote(note)
    }

    fun updateNote(note: Note) {
         noteRepository.updateNote(note)
    }

    fun updateNoteSpecificField(noteId:String, title:String, description:String){
         noteRepository.updateNoteSpecificField(noteId, title, description)
    }

    fun searchNoteList(query: String){
        noteRepository.searchNoteList(query)
    }
}