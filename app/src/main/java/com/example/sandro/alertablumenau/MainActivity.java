package com.example.sandro.alertablumenau;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sandro.alertablumenau.config.ConfigFirebase;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
     private EditText texto;
    private FirebaseAuth autenticacao;
    private Toolbar toolbar;
    private RuasAdapter adapter;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signOut();

        toolbar= (Toolbar)findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Alerta Blumenau");
        setSupportActionBar(toolbar);
        texto = (EditText)findViewById(R.id.textoCompartilhar);
        Button btShareText = ( Button ) findViewById( R.id.bt_share_text );

        btShareText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                shareText();
            }
        } );
    }
    private void shareText() {
// cria a intent e define a ação
        Intent intent = new Intent( Intent.ACTION_SEND );
// tipo de conteúdo da intent
        intent.setType( "text/plain" );
// string a ser enviada para outra intent
        intent.putExtra( Intent.EXTRA_TEXT, texto.getText().toString());
// inicia a intent
        startActivity( intent );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sair:
                sairUser();
                return true;
            case R.id.fotoM:
                compartilharFoto();
                return true;
            case R.id.novo:
                Intent intent = new Intent(this,CadastroRuaActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
   private void compartilharFoto(){

        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// escolher as imagens
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append( "android.resource://" )
                .append( "com.example.sandro.alertablumenau/" )
                .append( "drawable/" );

        Uri uriImage = Uri.parse( strBuilder.toString() );
        // cria a intent e define a ação
        Intent intent = new Intent( Intent.ACTION_SEND );
        // tipo de conteúdo da intent
        intent.setType( "image/*" );
        // stream a ser compartilhado
        intent.putExtra( Intent.EXTRA_STREAM, uriImage );
        startActivity( intent );


    }

    public void sairUser(){
        autenticacao.signOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();

    }

}
