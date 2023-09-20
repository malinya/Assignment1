package com.example.assignment1.adapters

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1.R
import com.example.assignment1.databinding.ViewNotesLayoutBinding
import com.example.assignment1.models.Note
import java.text.SimpleDateFormat
import java.util.Locale

class NoteRVVBListAdapter(
    private val deleteUpdateCallback: (type:String, position: Int, note: Note)-> Unit
)
    : ListAdapter<Note,NoteRVVBListAdapter.ViewHolder>(DiffCallBack()) {



    class ViewHolder(val viewNotesLayoutBinding: ViewNotesLayoutBinding)
        : RecyclerView.ViewHolder(viewNotesLayoutBinding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ViewNotesLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = getItem(position)

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



    class DiffCallBack : DiffUtil.ItemCallback<Note>()
    {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
           return oldItem == newItem
        }

    }
}