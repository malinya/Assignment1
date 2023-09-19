package com.example.assignment1.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.assignment1.database.NoteDatabase
import com.example.assignment1.models.Note
import com.example.assignment1.utils.Resource
import com.example.assignment1.utils.Resource.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.Exception

class NoteRepository(application: Application) {

    private val noteDao = NoteDatabase.getInstance(application).noteDao

    fun insertTask(note: Note) = MutableLiveData<Resource<Long>>().apply {

        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = noteDao.insertTask(note)
                postValue(Success(result))
            }

        }catch (e: Exception){
            postValue(Error(e.message.toString()))
        }


    }

}