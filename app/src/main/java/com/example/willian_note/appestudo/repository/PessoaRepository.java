package com.example.willian_note.appestudo.repository;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.willian_note.appestudo.util.Constantes;

/**
 * Created by Willian-Note on 29/10/2016.
 */

public class PessoaRepository extends SQLiteOpenHelper {
    public PessoaRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }

    public PessoaRepository(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE TB_PESSOA( ");
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
}
