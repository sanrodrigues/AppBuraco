package com.example.sandro.alertablumenau;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sandro.alertablumenau.bd.RuasRepositorio;
import com.example.sandro.alertablumenau.model.Ruas;

public class CadastroRuaActivity extends AppCompatActivity {
    private EditText editNome;
    private EditText editDescricao;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_rua);
        editNome = (EditText) (findViewById(R.id.edit_cad_rua));
        editDescricao = (EditText) (findViewById(R.id.edit_cad_desc));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            id = bundle.getInt("id");
            editNome.setText(bundle.getString("nome"));
            editDescricao.setText(bundle.getString("descricao"));
        }
    }
    public void salvar(View view) {

        Ruas rua = new Ruas();

        rua.setId(id);

        rua.setNome(editNome.getText().toString());

        rua.setDescricao(editDescricao.getText().toString());


        //validação
        //gravar

        RuasRepositorio repository = new RuasRepositorio(this);

        try {
            long registros = repository.salvar(rua);
            Toast.makeText(this, "registros gravados: " + registros, Toast.LENGTH_SHORT).show();

        } finally {
            repository.close();
        }
    }
}

