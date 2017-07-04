package com.example.sandro.alertablumenau.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sandro on 30/06/2017.
 */

public class DataBase extends SQLiteOpenHelper {
    private static int VERSAO = 2;
    private static final String BD="RUASBD";
    public DataBase(Context context){
        super(context,"RUASBD",null,VERSAO);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table ruas("+"_id integer primary key,"+"nome text,"+"decricao text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists ruas ");
        db.execSQL("create table ruas("+"_id integer primary key,"+"nome text,"+"descricao text);");

    }
}