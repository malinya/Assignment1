package com.example.assignment1.adapters

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1.R
import com.example.assignment1.databinding.ViewNotesLayoutBinding
import com.example.assignment1.models.Note
import java.text.SimpleDateFormat
import java.util.Locale

class NoteRVViewBindingAdapter(
    private val deleteUpdateCallback: (type:String, position: Int, note: Note)-> Unit
)
    : RecyclerView.Adapter<NoteRVViewBindingAdapter.ViewHolder>() {

    private val noteList = arrayListOf<Note>()

    class ViewHolder(val viewNotesLayoutBinding: ViewNotesLayoutBinding)
        : RecyclerView.ViewHolder(viewNotesLayoutBinding.root)

    fun addAllNote(newNoteList: List<Note>){
        noteList.clear()
        noteList.addAll(newNoteList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ViewNotesLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]

        holder.viewNotesLayoutBinding.titleTxt.text = note.title
        holder.viewNotesLayoutBinding.descrTxt.text = note.description

        val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())
        holder.viewNotesLayoutBinding.dateTxt.text = dateFormat.format(note.date)

        holder.viewNotesLayoutBinding.deleteImg.setOnClickListener{
            if (holder.adapterPosition != -1){
                deleteUpdateCallback("delete",holder.adapterPosition, note)
            }
        }
        holder.viewNotesLayoutBinding.editImg.setOnClickListener{
            if (holder.adapterPosition != -1){
                deleteUpdateCallback("update",holder.adapterPosition, note)
            }
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }


}