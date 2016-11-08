package com.example.willian_note.appestudo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.willian_note.appestudo.entidade.Pessoa;
import com.example.willian_note.appestudo.repository.PessoaRepository;
import com.example.willian_note.appestudo.util.TipoMsg;
import com.example.willian_note.appestudo.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

public class ListaPessoaActivity extends AppCompatActivity {

    private ListView lstPessoas;
    private PessoaRepository repository;
    private ImageButton btnNovaPessoa;
    private  List<Pessoa> listaPessoas;
    private int PosicaoSelecionada;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoa);
        getSupportActionBar().setTitle("Listagem de Pessoas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        lstPessoas = (ListView)findViewById(R.id.lstPessoas);
        btnNovaPessoa = (ImageButton)findViewById(R.id.btnNewPessoa);
        repository = new PessoaRepository(this);

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        setArrayAdapterPessoas();

        lstPessoas.setOnItemClickListener(clickListenerPessoas);
        lstPessoas.setOnCreateContextMenuListener(contextMenuListener);
        lstPessoas.setOnItemLongClickListener(onItemLongClickListener);

        btnNovaPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaPessoaActivity.this, PessoaActivity.class);
                startActivity(i);
            }
        });
    }

    private void setArrayAdapterPessoas() {
        listaPessoas = repository.listarPessoas();
        List<String> valores = new ArrayList<String>();
        for(Pessoa p : listaPessoas){
            valores.add(p.getNome());
        }
        adapter.clear();
        adapter.addAll(valores);
        lstPessoas.setAdapter(adapter);
    }

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            PosicaoSelecionada = position;
            return false;
        }
    };

    private View.OnCreateContextMenuListener contextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Opções").setHeaderIcon(R.drawable.edit).add(1, 10, 1, "Editar");
            menu.add(1, 20, 2, "Deletar");
        }
    };
    private AdapterView.OnItemClickListener clickListenerPessoas = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Pessoa pessoa = repository.ConsultarPessoaPorId(listaPessoas.get(position).getIdPessoa());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            StringBuilder info = new StringBuilder();
            info.append("Nome: " +pessoa.getNome());
            info.append("\nEndereço: " +pessoa.getEndereco());
            info.append("\nCPF: " +pessoa.getCpfCnpj());
            info.append("\nSexo: " +pessoa.getSexo().getDescricao());
            info.append("\nProfissão: " +pessoa.getProfissao().getDescrica());
            info.append("\nData Nasc: " + dateFormat.format(pessoa.getDtNasc()));

            Util.showMsgAlertOK(ListaPessoaActivity.this,"Info",info.toString(), TipoMsg.INFO);
        }
    };

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 10:
                Pessoa p = repository.ConsultarPessoaPorId(listaPessoas.get(PosicaoSelecionada).getIdPessoa());
                Intent i = new Intent(ListaPessoaActivity.this,EditarPessoaActivity.class);
                i.putExtra("pessoa",p);
                startActivity(i);
                //finish();
                break;
            case 20:
                Util.showMsgConfirm(ListaPessoaActivity.this, "Remover Pessoa", "Deseja realmente remover essa pessoa?", TipoMsg.ALERTA, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = listaPessoas.get(PosicaoSelecionada).getIdPessoa();
                        repository.RemoverPessoaPorId(id);
                        setArrayAdapterPessoas();
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
