package com.example.assignment1

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.assignment1.databinding.ActivityMainBinding
import com.example.assignment1.utils.setupDialog
import com.example.assignment1.utils.validateEditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
            addNoteDialog.show()
        }

        val saveNoteBtn = addNoteDialog.findViewById<Button>(R.id.saveNoteBtn)
        saveNoteBtn.setOnClickListener {
            if (validateEditText(addENTitle, addENTitleL)
                && validateEditText(addENDesc, addENDescL)
            ) {
                addNoteDialog.dismiss()
                Toast.makeText(this, "Validated!", Toast.LENGTH_LONG).show()
                loadingDialog.show()
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
        editNoteBtn.setOnClickListener {
            if (validateEditText(upENTitle, upENTitleL)
                && validateEditText(upENDesc, upENDescL)
            ) {
                updateNoteDialog.dismiss()
                Toast.makeText(this, "Validated!", Toast.LENGTH_LONG).show()
                loadingDialog.show()
            }
        }

    }
}