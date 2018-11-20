package com.felipe.docs.Model;

public class Outros {
    private String descricao;
    private String senha;

    public Outros() {
    }

    public Outros(String descricao, String senha) {
        this.descricao = descricao;
        this.senha = senha;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
