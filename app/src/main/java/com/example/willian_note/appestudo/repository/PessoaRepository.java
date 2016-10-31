package com.example.willian_note.appestudo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.speech.tts.TextToSpeechService;

import com.example.willian_note.appestudo.entidade.Pessoa;
import com.example.willian_note.appestudo.entidade.Profissao;
import com.example.willian_note.appestudo.entidade.Sexo;
import com.example.willian_note.appestudo.entidade.TipoPessoa;
import com.example.willian_note.appestudo.util.Constantes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Willian-Note on 29/10/2016.
 */

public class PessoaRepository extends SQLiteOpenHelper {
    public PessoaRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS TB_PESSOA( ");
        query.append(" ID_PESSOA INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" NOME TEXT(30) NOT NULL,");
        query.append(" ENDERECO TEXT(50),");
        query.append(" CPF TEXT(14),");
        query.append(" CNPJ TEXT(15),");
        query.append(" SEXO INTEGER NOT NULL, ");
        query.append(" PROFISSAO INTEGER(3) NOT NULL,");
        query.append(" DT_NASCIMENTO INTEGER NOT NULL)");

        db.execSQL(query.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public boolean SalvarPessoa(Pessoa pessoa){
        boolean Cadastrou = true;
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("NOME",pessoa.getNome());
            contentValues.put("ENDERECO",pessoa.getEndereco());
            switch (pessoa.getTipoPessoa()){
                case FISICA:
                    contentValues.put("CPF",pessoa.getCpfCnpj());
                    break;
                case JURIDICA:
                    contentValues.put("CNPJ", pessoa.getCpfCnpj());
                    break;
            }
            contentValues.put("SEXO", pessoa.getSexo().ordinal());
            contentValues.put("PROFISSAO", pessoa.getProfissao().ordinal());
            contentValues.put("DT_NASCIMENTO", pessoa.getDtNasc().getTime());
            db.insert("TB_PESSOA", null, contentValues);
        }
        catch (Exception e){
            e.printStackTrace();
            Cadastrou = false;
        }
        return Cadastrou;
    }

    public List<Pessoa> listarPessoas(){
        List<Pessoa> lista = new ArrayList<Pessoa>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TB_PESSOA", null, null, null, null, null, "NOME");
        while (cursor.moveToNext()){
            Pessoa pessoa = new Pessoa();
            pessoa.setIdPessoa(cursor.getInt(cursor.getColumnIndex("ID_PESSOA")));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            pessoa.setEndereco(cursor.getString(cursor.getColumnIndex("ENDERECO")));
            String cpf = cursor.getString(cursor.getColumnIndex("CPF"));
            String cnpj = cursor.getString(cursor.getColumnIndex("CNPJ"));
            if(cpf != null){
                pessoa.setTipoPessoa(TipoPessoa.FISICA);
                pessoa.setCpfCnpj(cpf);
            }
            else{
                pessoa.setTipoPessoa(TipoPessoa.JURIDICA);
                pessoa.setCpfCnpj(cnpj);
            }
            int sexo = cursor.getInt(cursor.getColumnIndex("SEXO"));
            pessoa.setSexo(Sexo.getSexo(sexo));
            int profissao = cursor.getInt(cursor.getColumnIndex("PROFISSAO"));
            pessoa.setProfissao(Profissao.getProfissao(profissao));
            int time = cursor.getInt(cursor.getColumnIndex("DT_NASCIMENTO"));
            Date dtNasc = new Date();
            dtNasc.setTime(time);
            pessoa.setDtNasc(dtNasc);

            lista.add(pessoa);
        }
        return lista;
    }
}
