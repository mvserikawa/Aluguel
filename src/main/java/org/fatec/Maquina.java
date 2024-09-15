package org.fatec;

public class Maquina {
    private int id;
    private String tipo;
    private String descricao;
    private String especificacao;
    private double valorAluguel;
    private String status;

    // Construtor conforme usado no código de teste


    public Maquina(int id, String descricao, double valorAluguel) {
        this.id = id;
        this.descricao = descricao;
        this.valorAluguel = valorAluguel;
    }

    public Maquina(int id, String tipo, String descricao, String especificacao, double valorAluguel, String status) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.especificacao = especificacao;
        this.valorAluguel = valorAluguel;
        this.status = status;
    }

    public Maquina(String tipo, String descricao, String especificacao, double valorAluguel, String status) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.especificacao = especificacao;
        this.valorAluguel = valorAluguel;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Maquina(int id, String tipo, String descricao, String especificacao, double valorAluguel) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.especificacao = especificacao;
        this.valorAluguel = valorAluguel;
    }

    public Maquina(String tipo, String descricao, String especificacao, double valorAluguel) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.especificacao = especificacao;
        this.valorAluguel = valorAluguel;
    }

    public Maquina() {
    }



    // Getters e Setters (se necessário)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    public double getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(double valorAluguel) {
        this.valorAluguel = valorAluguel;
    }
}
