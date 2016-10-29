package com.example.willian_note.appestudo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.willian_note.appestudo.Validation.LoginValidation;
import com.example.willian_note.appestudo.bo.LoginBO;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLogin;
    private EditText edtSenha;

    private Button btnLogar;
    private SharedPreferences preferences;
    private LoginBO loginBo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        loginBo = new LoginBO(this);
        preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

        String login = preferences.getString("login", null);
        String senha = preferences.getString("senha", null);

        if(login!= null && senha != null){
            Intent i = new Intent(LoginActivity.this, PessoaActivity.class);
            startActivity(i);
            finish();
        }

        edtLogin = (EditText)findViewById(R.id.edtLogin);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        btnLogar = (Button)findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = edtLogin.getText().toString();
                String senha = edtSenha.getText().toString();

                LoginValidation validation = new LoginValidation();
                validation.setActivity(LoginActivity.this);
                validation.setEdtLogin(edtLogin);
                validation.setEdtSenha(edtSenha);
                validation.setLogin(login);
                validation.setSenha(senha);

                boolean isValido = loginBo.ValidarCamposLogin(validation);
                if(isValido){
                    Intent i = new Intent(LoginActivity.this, PessoaActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }

}
