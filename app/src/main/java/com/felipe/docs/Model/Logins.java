package com.felipe.docs.Model;

public class Logins {

    private String aplicacao;
    private String logins;
    private String senha;

    public Logins() {
    }

    public Logins(String aplicacao, String logins, String senha) {
        this.aplicacao = aplicacao;
        this.logins = logins;
        this.senha = senha;
    }

    public String getAplicacao() {
        return aplicacao;
    }

    public void setAplicacao(String aplicacao) {
        this.aplicacao = aplicacao;
    }

    public String getLogins() {
        return logins;
    }

    public void setLogins(String logins) {
        this.logins = logins;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
