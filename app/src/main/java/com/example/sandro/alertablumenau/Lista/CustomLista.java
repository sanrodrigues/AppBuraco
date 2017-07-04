package com.example.sandro.alertablumenau.Lista;

import android.util.Log;

import com.example.sandro.alertablumenau.model.Pessoa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Sandro on 30/06/2017.
 */

public class CustomLista implements ValueEventListener {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot d: dataSnapshot.getChildren()){//ultimos dados no banco
            Pessoa u = d.getValue(Pessoa.class);
            Log.i("Log","Nome:"+u.getNome());
            Log.i("Log","Email:"+u.getEmail());


        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
