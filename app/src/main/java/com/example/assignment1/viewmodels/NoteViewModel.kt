package com.example.assignment1.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.assignment1.models.Note
import com.example.assignment1.repository.NoteRepository
import com.example.assignment1.utils.Resource

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application)

    fun getNoteList() = noteRepository.getNoteList()
    fun insertTask(note: Note): MutableLiveData<Resource<Long>> {
        return noteRepository.insertTask(note)
    }
}