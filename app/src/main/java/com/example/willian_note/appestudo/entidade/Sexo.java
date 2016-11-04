package com.example.willian_note.appestudo.entidade;

/**
 * Created by Willian-Note on 29/10/2016.
 */

public enum Sexo   {
    MASCULINO("Masculino"),FEMININO("Feminino");

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    private Sexo(String descricao){
        this.descricao = descricao;

    }

    public static Sexo getSexo(int pos){
        for (Sexo sexo : Sexo.values()) {
            if(sexo.ordinal() == pos)
                return sexo;
        }
        return  null;
    }
}
