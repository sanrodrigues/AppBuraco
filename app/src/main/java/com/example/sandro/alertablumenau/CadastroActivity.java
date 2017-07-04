package com.example.sandro.alertablumenau;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sandro.alertablumenau.config.ConfigFirebase;
import com.example.sandro.alertablumenau.model.Pessoa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {
    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button cadastrar;
    private Pessoa pessoa;
    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        nome = (EditText)findViewById(R.id.edit_cad_nome);
        email = (EditText)findViewById(R.id.edit_cad_email);
        senha = (EditText)findViewById(R.id.edit_cad_senha);
        cadastrar = (Button) findViewById(R.id.button_cad);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pessoa = new Pessoa();//instanciar a classe
                pessoa.setNome(nome.getText().toString());
                pessoa.setEmail(email.getText().toString());
                pessoa.setSenha(senha.getText().toString());

                cadastrarUsuario();

            }
        });

    }
    private void cadastrarUsuario(){
     autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                pessoa.getEmail(),
                pessoa.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            //verifica se o cadastro foi feito
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this,"Cadastro realizado com sucesso",Toast.LENGTH_LONG).show();
                    FirebaseUser pessoaFirebase = task.getResult().getUser();
                    pessoa.setId(pessoaFirebase.getUid());
                    pessoa.salvar();

                    autenticacao.signOut();
                    finish();

                }else {
                    String erroExcecao="";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Senha fraca, digite uma senha com numeros e letras";
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "Email invalido, digite um novo email";
                    }
                    catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Email já está em uso";
                    }catch (Exception e) {
                        erroExcecao = "Erro em efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this,"Erro ao cadastrar usuario",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
