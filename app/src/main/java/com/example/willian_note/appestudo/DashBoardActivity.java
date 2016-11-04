package com.example.willian_note.appestudo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashBoardActivity extends AppCompatActivity {

    private Button btnCadastrar;
    private Button btnListar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        getSupportActionBar().hide();

        btnCadastrar = (Button)findViewById(R.id.btndashCadastro);
        btnListar = (Button)findViewById(R.id.btndashLista);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBoardActivity.this, PessoaActivity.class);
                startActivity(i);
            }
        });
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBoardActivity.this, ListaPessoaActivity.class);
                startActivity(i);
            }
        });
    }
}
