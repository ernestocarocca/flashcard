package com.example.ecflashcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.flashcard.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddWordActivity : AppCompatActivity(), CoroutineScope {
    lateinit var saveButton: Button
    lateinit var engEditText: EditText
    lateinit var sweEditText: EditText
    private lateinit var job : Job
    lateinit var backButton: Button
    lateinit var db : AppDatabase

    override val coroutineContext : CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_word)

        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)
        engEditText = findViewById(R.id.engEditText)
        sweEditText = findViewById(R.id.sweEditText)

        job = Job()
        db = AppDatabase.getInstance(this)


        backButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            saveNewWordToDB()
            clearEditText()
        }

    }


    private fun clearEditText() {
        sweEditText.text.clear()
        engEditText.text.clear()
    }

    private fun saveNewWordToDB() {
        if (engEditText.text.isEmpty() || sweEditText.text.isEmpty()) {
            Log.d("!!!", "Input Fields cannot be empty, when you add a new word, please fill them.")

        } else {
            var englishWord = engEditText.text.toString()
            var swedishWord = sweEditText.text.toString()
            var newWord = Word(englishWord, swedishWord)

            launch(Dispatchers.IO) {
                db.wordDao.insert(newWord)
            }
        }

    }

}