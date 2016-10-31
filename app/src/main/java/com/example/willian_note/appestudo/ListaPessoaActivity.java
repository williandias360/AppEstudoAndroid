package com.example.willian_note.appestudo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.willian_note.appestudo.entidade.Pessoa;
import com.example.willian_note.appestudo.repository.PessoaRepository;

import java.util.ArrayList;
import java.util.List;

public class ListaPessoaActivity extends AppCompatActivity {

    private ListView lstPessoas;
    private PessoaRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoa);
        lstPessoas = (ListView)findViewById(R.id.lstPessoas);
        repository = new PessoaRepository(this);

        List<Pessoa> lista = repository.listarPessoas();
        List<String> valores = new ArrayList<String>();
        for(Pessoa p : lista){
            valores.add(p.getNome());
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,valores);

        lstPessoas.setAdapter(adapter);
    }

}
