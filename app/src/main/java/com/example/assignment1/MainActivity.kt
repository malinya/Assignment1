package com.example.assignment1

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
//import androidx.room.Query
import com.example.assignment1.adapters.NoteRVVBListAdapter
import com.example.assignment1.databinding.ActivityMainBinding
import com.example.assignment1.models.Note
import com.example.assignment1.utils.Status
import com.example.assignment1.utils.StatusResult
import com.example.assignment1.utils.StatusResult.*
import com.example.assignment1.utils.clearEditText
import com.example.assignment1.utils.hideKeyboard
import com.example.assignment1.utils.longToastShow
import com.example.assignment1.utils.setupDialog
import com.example.assignment1.utils.validateEditText
import com.example.assignment1.viewmodels.NoteViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val addNoteDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.add_notes_d)
        }
    }

    private val updateNoteDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.update_notes_d)
        }
    }

    private val loadingDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.loading_dialog)
        }
    }

    private val noteViewModel : NoteViewModel by lazy {
        ViewModelProvider(this)[NoteViewModel::class.java]
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)



        // Adding note
        val addCloseImg = addNoteDialog.findViewById<ImageView>(R.id.closeImg)
        addCloseImg.setOnClickListener { addNoteDialog.dismiss() }


        val addENTitle = addNoteDialog.findViewById<TextInputEditText>(R.id.edNoteTitle)
        val addENTitleL = addNoteDialog.findViewById<TextInputLayout>(R.id.edNoteTitleL)

        addENTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(addENTitle, addENTitleL)
            }})

        val addENDesc = addNoteDialog.findViewById<TextInputEditText>(R.id.edNoteDesc)
        val addENDescL = addNoteDialog.findViewById<TextInputLayout>(R.id.edNoteDescL)

        addENDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(addENDesc, addENDescL)
            }})


        mainBinding.addNoteFABtn.setOnClickListener {
            clearEditText(addENTitle, addENTitleL)
            clearEditText(addENDesc, addENDescL)
            addNoteDialog.show()
        }

        val saveNoteBtn = addNoteDialog.findViewById<Button>(R.id.saveNoteBtn)
        saveNoteBtn.setOnClickListener {
            if (validateEditText(addENTitle, addENTitleL)
                && validateEditText(addENDesc, addENDescL)
            ) {

                val newNote = Note(
                    UUID.randomUUID().toString(),
                    addENTitle.text.toString().trim(),
                    addENDesc.text.toString().trim(),
                    Date()
                )
                hideKeyboard(it)
                addNoteDialog.dismiss()
                //Toast.makeText(this, "Validated!", Toast.LENGTH_LONG).show()
                //loadingDialog.show()

                noteViewModel.insertTask(newNote)
            }
        }

        val upENTitle = updateNoteDialog.findViewById<TextInputEditText>(R.id.edNoteTitle)
        val upENTitleL = updateNoteDialog.findViewById<TextInputLayout>(R.id.edNoteTitleL)

        upENTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(upENTitle, upENTitleL)
            }})

        val upENDesc = updateNoteDialog.findViewById<TextInputEditText>(R.id.edNoteDesc)
        val upENDescL = updateNoteDialog.findViewById<TextInputLayout>(R.id.edNoteDescL)

        upENDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(upENDesc, upENDescL)
            }})

        val editCloseImg = updateNoteDialog.findViewById<ImageView>(R.id.closeImg)
        editCloseImg.setOnClickListener { updateNoteDialog.dismiss() }

        val editNoteBtn = updateNoteDialog.findViewById<Button>(R.id.saveEditNoteBtn)

        //update note -------ends

       val noteRVVBListAdapter = NoteRVVBListAdapter{ type, position, note ->
           if(type == "delete") {
               noteViewModel
                   .deleteNote(note)
              }else if(type == "update"){
                  upENTitle.setText(note.title)
                  upENDesc.setText(note.description)
                  editNoteBtn.setOnClickListener {
                       if (validateEditText(upENTitle, upENTitleL)
                           && validateEditText(upENDesc, upENDescL)
                       ) {
                           val updateNote = Note(
                               note.id,
                               upENTitle.text.toString().trim(),
                               upENDesc.text.toString().trim(),
                               Date()
                           )
                           hideKeyboard(it)
                           updateNoteDialog.dismiss()
                           noteViewModel
                               .updateNote(updateNote)

                       }
                  }
               updateNoteDialog.show()
              }
        }
        mainBinding.noteRV.adapter = noteRVVBListAdapter

        noteRVVBListAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver()
        {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                mainBinding.noteRV.smoothScrollToPosition(positionStart)
            }
        })
        callGetNoteList(noteRVVBListAdapter)

        noteViewModel.getNoteList()
        statusCallback()

        callSearch()

    }

    private fun statusCallback(){
        noteViewModel
            .statusLiveData
            .observe(this){
                when (it.status) {
                    Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        when(it.data as StatusResult){
                            Added -> {
                                Log.d("StatusResult","Added")
                            }
                            Deleted ->{
                                Log.d("StatusResult","Deleted")
                            }
                            Updated ->{
                                Log.d("StatusResult","Updated")
                            }
                        }
                        it.message?.let { it1 -> longToastShow(it1) }
                    }

                    Status.ERROR -> {
                        loadingDialog.dismiss()
                        it.message?.let { it1 -> longToastShow(it1) }

                    }
                }
            }

    }

    private fun callSearch() {
        mainBinding.edSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(query: Editable) {
                if(query.toString().isNotEmpty()){
                    noteViewModel.searchNoteList(query.toString())
                }else{
                    noteViewModel.getNoteList()
                }
            }
        })

        mainBinding.edSearch.setOnEditorActionListener{v,actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun callGetNoteList(noteRecyclerViewAdapter: NoteRVVBListAdapter) {
        CoroutineScope(Dispatchers.Main).launch {

            noteViewModel.noteStateFlow
                .collectLatest {
                when (it.status) {
                    Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.collect {noteList ->
                            noteRecyclerViewAdapter.submitList(noteList)
                        }
                    }

                    Status.ERROR -> {
                        loadingDialog.dismiss()
                        it.message?.let { it1 -> longToastShow(it1) }

                    }
                }
            }
        }
    }
}

