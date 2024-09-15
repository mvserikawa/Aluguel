package org.fatec;

public class Suporte {
    private String comentario;
    private int id;
    private Maquina maquina;
    private Cliente cliente;
    private Pedido pedido;
    private String status;
    private Usuario usuario;

    public Suporte(String comentario, int id, Maquina maquina, Cliente cliente, Pedido pedido, String status) {
        this.comentario = comentario;
        this.id = id;
        this.maquina = maquina;
        this.cliente = cliente;
        this.pedido = pedido;
        this.status = status;
    }

    public Suporte() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
