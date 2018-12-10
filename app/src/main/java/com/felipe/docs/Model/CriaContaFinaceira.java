package com.felipe.docs.Model;

public class CriaContaFinaceira {
    private String descricao;
    private String observacao;
    private int parcela;
    private String tipoPagamento;
    private double valorTotal;
    private int tipo;
    private String data;

    public CriaContaFinaceira() {
    }

    public CriaContaFinaceira(String descricao, String observacao, int parcela, String tipoPagamento, double valorTotal, int tipo, String data) {
        this.descricao = descricao;
        this.observacao = observacao;
        this.parcela = parcela;
        this.tipoPagamento = tipoPagamento;
        this.valorTotal = valorTotal;
        this.tipo = tipo;
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getParcela() {
        return parcela;
    }

    public void setParcela(int parcela) {
        this.parcela = parcela;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
