package com.example.assignment1.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Query
import com.example.assignment1.database.NoteDatabase
import com.example.assignment1.models.Note
import com.example.assignment1.utils.Resource
import com.example.assignment1.utils.Resource.*
import com.example.assignment1.utils.StatusResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.Exception

class NoteRepository(application: Application) {

    private val noteDao = NoteDatabase.getInstance(application).noteDao

    private val _noteStateFlow = MutableStateFlow<Resource<Flow<List<Note>>>>(Loading())
    val noteStateFlow : StateFlow<Resource<Flow<List<Note>>>>
        get() = _noteStateFlow

    private  val _statusLiveData = MutableLiveData<Resource<StatusResult>>()
    val statusLiveData : LiveData<Resource<StatusResult>>
        get() = _statusLiveData

    fun getNoteList() {
        CoroutineScope(Dispatchers.IO).launch{

            _noteStateFlow.emit(Loading())
        try {
            val result = noteDao.getNoteList()
            _noteStateFlow.emit(Success("loading",result))
        }catch (e: Exception){
            _noteStateFlow.emit(Error(e.message.toString()))
        }
        }
    }

    fun insertTask(note: Note){

        try {
            _statusLiveData.postValue(Loading())
            CoroutineScope(Dispatchers.IO).launch {
                val result = noteDao.insertTask(note)
                handleResult(result.toInt(), "Note Added Successfully", StatusResult.Added)
            }
        }catch (e: Exception){
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }

    fun deleteNote(note: Note){

        try {
            _statusLiveData.postValue(Loading())
            CoroutineScope(Dispatchers.IO).launch {
                val result = noteDao.deleteNote(note)
                handleResult(result, "Deleted Task Successfully", StatusResult.Deleted)
            }
        }catch (e: Exception){
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }

    fun updateNote(note: Note){

        try {
            _statusLiveData.postValue(Loading())
            CoroutineScope(Dispatchers.IO).launch {
                val result = noteDao.updateNote(note)
                handleResult(result, "Updated Task Successfully", StatusResult.Updated)
            }
        }catch (e: Exception){
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }

    fun searchNoteList(query: String) {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                _noteStateFlow.emit(Loading())
                val result = noteDao.searchNoteList("%${query}%")
                _noteStateFlow.emit(Success("loading", result))
            }catch (e: Exception){
                _noteStateFlow.emit(Error(e.message.toString()))
            }
        }

    }

    fun updateNoteSpecificField(noteId:String, title:String, description:String){

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = noteDao.updateNoteSpecificField(noteId, title, description)
                handleResult(result, "Updated Task Successfully", StatusResult.Updated)

            }
        }catch (e: Exception){
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }

    private fun handleResult(result:Int, message : String, statusResult: StatusResult){
        if (result != -1) {
            _statusLiveData.postValue(Success(message, statusResult))
        } else {
            _statusLiveData.postValue(Error("Something Went Wrong", statusResult))
        }
    }
}