package com.example.willian_note.appestudo.repository;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.willian_note.appestudo.util.Constantes;
import com.example.willian_note.appestudo.util.Util;

/**
 * Created by Willian-Note on 17/10/2016.
 */

public class LoginRepository extends SQLiteOpenHelper{

    public LoginRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE TB_LOGIN( ");
        query.append(" ID_LOGIN INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" USUARIO TEXT(15) NOT NULL,");
        query.append(" SENHA TEXT(15) NOT NULL)");

        db.execSQL(query.toString());

        popularBD(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void popularBD(SQLiteDatabase db){
        StringBuilder query = new StringBuilder();
        query.append("insert into TB_LOGIN (USUARIO,SENHA) ");
        query.append(" values (?, ?)");
        db.execSQL(query.toString(), new String[] {"admin", "admin"});
    }

    public void listarLogins(Activity activity){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("TB_LOGIN", null, "ID_LOGIN = ? ", new String[]{"1"}, null, null, "USUARIO");
        while (cursor.moveToNext()){
            String txt = "Id do usuário: " + String.valueOf(cursor.getInt(cursor.getColumnIndex("ID_LOGIN")));
            Util.showMsgToast(activity, txt);
        }
    }

    public void AdicionarLogin(String login, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USUARIO", login);
        contentValues.put("SENHA", senha);

        db.insert("TB_LOGIN",null, contentValues);

    }

    public void UpdateLogin(String login, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USUARIO",login);
        contentValues.put("SENHA", senha);

        db.update("TB_LOGIN",contentValues, "ID_LOGIN > 1", null);
    }

    public void DeletarLogin(String login, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TB_LOGIN","USUARIO=? or SENHA=?", new String[]{login,senha});
    }
}
