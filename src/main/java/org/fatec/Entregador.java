package org.fatec;

public class Entregador {
    private int id; // Adicionando campo id
    private String placa;
    private int usuario_id;

    public Entregador() {

    }

    public Entregador(String placa, int usuario_id) {
        this.placa = placa;
        this.usuario_id = usuario_id;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
}
