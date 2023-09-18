package com.example.assignment1.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Note(@PrimaryKey(autoGenerate = false)
                @ColumnInfo(name = "noteId")
                val id: String,
                @ColumnInfo(name = "noteTitle")
                val title: String,
                val description: String,
                val date: Date,

    )
