package com.example.willian_note.appestudo.entidade;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Willian-Note on 29/10/2016.
 */

public class Pessoa  implements Serializable{
    private int idPessoa;
    private String nome;
    private String endereco;
    private String CpfCnpj;
    private TipoPessoa tipoPessoa;
    private Sexo sexo;
    private Date dtNasc;
    private Profissao profissao;

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpfCnpj() {
        return CpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        CpfCnpj = cpfCnpj;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", CpfCnpj='" + CpfCnpj + '\'' +
                ", tipoPessoa=" + tipoPessoa +
                ", sexo=" + sexo +
                ", dtNasc=" + dtNasc +
                ", profissao=" + profissao +
                '}';
    }
}
