package com.example.ecflashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import com.example.flashcard.R
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(),CoroutineScope {
    lateinit var addWordButton: Button
    lateinit var db: AppDatabase
    private lateinit var job: Job
    lateinit var wordView: TextView
    var currentWord: Word? = null
    val wordList = WordList()


    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addWordButton = findViewById<Button>(R.id.addWord)
        wordView = findViewById(R.id.wordTextView)

        db = AppDatabase.getInstance(this)
        job = Job()
        showNewWord()

        addWordButton.setOnClickListener {
            val intent = Intent(this, AddWordActivity::class.java)
            startActivity(intent)
        }

        wordView.setOnClickListener {
            revealTranslation()
        }


    }

    override fun onResume() {
        super.onResume()
        wordList.clearWordList()
        wordList.initializeWords()
        launch {
            var newWordList = getDbList()
            var list = newWordList.await()
            addWords(list)
        }
    }

    fun addWords(list: List<Word>) {
        for (word in list) {
            wordList.addWord(word)
        }

    }

    fun getDbList(): Deferred<List<Word>> =
        async(Dispatchers.IO) {
            db.wordDao.getAllWords()
        }

    fun revealTranslation() {
        wordView.text = currentWord?.english
    }


    fun showNewWord() {

        currentWord = wordList.getNewWord()
        wordView.text = currentWord?.swedish
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_UP) {
            showNewWord()
        }

        return true
    }
}






// 1. skapa en ny aktivitet där ett nytt ord får skrivas in

// 2. spara det nya ordet i databasen.

// 3. i main activity läs in alla ord från databasen.

// (använd coroutiner när ni läser och skriver till databasen, se tidigare exempel)

