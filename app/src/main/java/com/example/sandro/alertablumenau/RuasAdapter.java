package com.example.sandro.alertablumenau;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sandro.alertablumenau.model.Ruas;

import java.util.List;

/**
 * Created by Sandro on 30/06/2017.
 */

public class RuasAdapter  extends BaseAdapter {

    private List<Ruas> ruas;
    private Activity activity;

    public RuasAdapter(Activity activity, List<Ruas> ruas){
        this.ruas =ruas;
        this.activity=activity;
    }
    @Override
    public int getCount() {
        return ruas.size();
    }

    @Override
    public Object getItem(int position) {
        return ruas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ruas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = this.activity.getLayoutInflater().inflate(android.R.layout.simple_list_item_1,parent,false);
        TextView txt = (TextView)view;
        txt.setText(ruas.get(position).getNome());
        return view;
    }

    public void remove(int position) {
        ruas.remove(position);

    }
}