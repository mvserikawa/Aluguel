package org.fatec;

public class Avaliacao {
    private int id;
    private int nota;
    private String comentario;
    private Pedido pedido;

    public Avaliacao() {
    }

    public Avaliacao(int id, int nota, String comentario, Pedido pedido) {
        this.id = id;
        this.nota = nota;
        this.comentario = comentario;
        this.pedido = pedido;
    }

    public Avaliacao(int nota, String comentario, Pedido pedido) {
        this.nota = nota;
        this.comentario = comentario;
        this.pedido = pedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}
