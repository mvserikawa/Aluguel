package org.fatec;

public class Funcionario{
    private int id; // Adicionando campo id
    private String setor;
    private  int usuarioId;

    public Funcionario() {

    }

    public Funcionario(String setor, int usuarioId) {
        this.setor = setor;
        this.usuarioId = usuarioId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}
