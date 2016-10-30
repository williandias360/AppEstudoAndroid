package com.example.willian_note.appestudo.entidade;

/**
 * Created by Willian-Note on 29/10/2016.
 */

public enum Sexo   {
    MASCULINO,FEMININO;

    public static Sexo getSexo(int pos){
        for (Sexo sexo : Sexo.values()) {
            if(sexo.ordinal() == pos)
                return sexo;
        }
        return  null;
    }
}
