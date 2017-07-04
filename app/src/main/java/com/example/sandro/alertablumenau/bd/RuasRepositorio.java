package com.example.sandro.alertablumenau.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sandro.alertablumenau.model.Ruas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandro on 30/06/2017.
 */

public class RuasRepositorio {
    private DataBase helper;
    private static final String TABLE="ruas";
    public RuasRepositorio(Context context){
        helper=new DataBase(context);
    }

    public void close(){
        helper.close();
    }

    public long salvar(Ruas ruas){
        SQLiteDatabase db= helper.getWritableDatabase();
        try {
            ContentValues values= new ContentValues();
            values.put("nome",ruas.getNome());
            values.put("email",ruas.getDescricao());

            if (ruas.getId() ==0){
                return db.insert(TABLE,null,values);
            }
            else {
                String where = "_id=?";
                String[] whereArgs = new String[1];
                whereArgs[0]=String.valueOf(ruas.getId());
                return   db.update(TABLE, values,where,whereArgs);
            }

        }finally {
            db.close();
        }
    }
    public List<Ruas> findNomeRuas(){
        SQLiteDatabase db= helper.getWritableDatabase();
        try {
// db.rawQuery("select nome from " + TABLE + " order by nome");

            String sql = String.format("select _id, nome, descricao from %s order by nome", TABLE);

            Cursor cursor = db.rawQuery(sql,null);

            cursor.moveToFirst();

            List<Ruas> ruas = new ArrayList<>();

            while (!cursor.isAfterLast()){
                Ruas rua = new Ruas();
                rua.setId(cursor.getInt(0));
                rua.setNome(cursor.getString(1));
                rua.setDescricao(cursor.getString(2));
                ruas.add(rua);
                cursor.moveToNext();
            }
            return ruas;

        }finally {

            db.close();
        }

    }
    public void excluir(long id){
        SQLiteDatabase db=helper.getWritableDatabase();
        try{
            String where = "_id=?";
            String[] whereArgs = new String[1];
            whereArgs[0]=String.valueOf(id);
            db.delete(TABLE, where,whereArgs);

        }finally {
            db.close();
        }

    }
}
