package com.example.ecflashcard

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
class Word(

    @ColumnInfo(name = "english") var english : String,
    @ColumnInfo(name = "swedish") var swedish : String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null) {




}