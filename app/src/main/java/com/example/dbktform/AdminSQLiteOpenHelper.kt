package com.example.dbktform

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper


class AdminSQLiteOpenHelper(
    context: Context?,
    name: String?,
    factory: CursorFactory?,
    version: Int
) :
    SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table usuario(nocontrol integer primary key, nombre text, carrera text, semestre integer)")
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("drop table if exists usuario")
        db.execSQL("create table usuario(nocontrol integer primary key, nombre text, carrera text, semestre integer)")
    }
}

