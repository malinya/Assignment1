package com.example.assignment1.adapters

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1.R
import com.example.assignment1.models.Note
import java.text.SimpleDateFormat
import java.util.Locale

class NoteRecyclerViewAdapter(
    private val deleteUpdateCallback: (type:String, position: Int, note: Note)-> Unit
)
    : RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>() {

    private val noteList = arrayListOf<Note>()

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val titleTxt : TextView = itemView.findViewById(R.id.titleTxt)
        val descrTxt : TextView = itemView.findViewById(R.id.descrTxt)
        val dateTxt : TextView = itemView.findViewById(R.id.dateTxt)

        val deleteImg : ImageView = itemView.findViewById(R.id.deleteImg)
        val editImg : ImageView = itemView.findViewById(R.id.editImg)
    }

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
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_notes_layout, parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]

        holder.titleTxt.text = note.title
        holder.descrTxt.text = note.description

        val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())
        holder.dateTxt.text = dateFormat.format(note.date)

        holder.deleteImg.setOnClickListener{
            if (holder.adapterPosition != -1){
                deleteUpdateCallback("delete",holder.adapterPosition, note)
            }
        }
        holder.editImg.setOnClickListener{
            if (holder.adapterPosition != -1){
                deleteUpdateCallback("update",holder.adapterPosition, note)
            }
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }


}