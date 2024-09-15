package org.fatec;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private Maquina maquina;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;
    private Entregador entregador;
    private String status;

    public String getStatus() {
        return status;
    }

    public Pedido(int id, Maquina maquina, LocalDate dataRetirada, LocalDate dataDevolucao, String status) {
        this.id = id;
        this.maquina = maquina;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pedido(int id, Cliente cliente, Maquina maquina, LocalDate dataRetirada, LocalDate dataDevolucao, Entregador entregador) {
        this.id = id;
        this.cliente = cliente;
        this.maquina = maquina;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.entregador = entregador;
    }

    public Pedido(Cliente cliente, Maquina maquina, LocalDate dataRetirada, LocalDate dataDevolucao, Entregador entregador) {
        this.cliente = cliente;
        this.maquina = maquina;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.entregador = entregador;
    }

    public Pedido(Cliente cliente, Maquina maquina, LocalDate dataRetirada, LocalDate dataDevolucao) {
        this.cliente = cliente;
        this.maquina = maquina;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
    }

    public Pedido() {
        // Construtor vazio
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public Entregador getEntregador() {
        return entregador;
    }

    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(LocalDate dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

}
