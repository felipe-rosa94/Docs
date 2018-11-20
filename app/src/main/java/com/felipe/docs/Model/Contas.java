package com.felipe.docs.Model;

public class Contas {

    private String nome;
    private String agencia;
    private String numeroConta;
    private String tipo;

    public Contas() {
    }

    public Contas(String nome, String agencia, String numeroConta, String tipo) {
        this.nome = nome;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
