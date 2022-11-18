package com.example.ecflashcard
class WordList() {
    private val wordList = mutableListOf<Word>()
    private val usedWords = mutableListOf<Word>()

    init {
        initializeWords()
    }


    fun initializeWords() {
        val word = Word(english = "Hello", "Hej")
        wordList.add(word)


    }



    //alternativ 1
    fun clearWordList() {
        wordList.clear()
    }

    fun addWord(word: Word) {
        wordList.add(word)
    }

    fun getNewWord() : Word {
        if (wordList.size == usedWords.size) {
            usedWords.clear()
        }

        var word : Word? = null

        do {
            val rnd = (0 until wordList.size).random()
            word = wordList[rnd]
        } while(usedWords.contains(word))

        usedWords.add(word!!)

        return word
    }
}