package com.example.willian_note.appestudo.entidade;

/**
 * Created by Willian-Note on 25/10/2016.
 */

public enum Profissao {
    ARQUITETO("Arquiteto de Software"), PEDREIRO("Pedreiro"), PROFESSOR("Professor"), DESENVOLVEDOR("Desenvolvedor de Sistemas"),
    ANALISTA("Analista Financeiro"), ENGENHEIRO("Engenheiro Mecanico"), ZELADOR("Zelador"), CARTEIRO("Carteirro");

    private Profissao(String descricao){
        this.descrica = descricao;
    }

    private String descrica;
    public String getDescrica() {
        return descrica;
    }

    public static Profissao getProfissao(int pos){
        for (Profissao p: Profissao.values()) {
            if (p.ordinal() == pos)
                return  p;
            
        }
        return null;
    }
}
