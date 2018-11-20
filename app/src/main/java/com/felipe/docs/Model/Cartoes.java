package com.felipe.docs.Model;

public class Cartoes {

    private String descricao;
    private String nome;
    private String numero;
    private String data;
    private String cvc;
    private String senha;

    public Cartoes() {
    }

    public Cartoes(String descricao, String nome, String numero, String data, String cvc, String senha) {
        this.descricao = descricao;
        this.nome = nome;
        this.numero = numero;
        this.data = data;
        this.cvc = cvc;
        this.senha = senha;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
