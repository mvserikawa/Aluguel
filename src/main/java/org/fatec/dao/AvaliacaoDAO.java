package org.fatec.dao;

import org.fatec.Avaliacao;
import org.fatec.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {
    private Conexao conexao = new Conexao();

    public AvaliacaoDAO() {
        try {
            String sql = "create table if not exists avaliacao(" +
                    "id int primary key AUTO_INCREMENT, " +
                    "nota int, " +
                    "comentario varchar(255), " +
                    "pedido_id int, " +
                    "FOREIGN KEY (pedido_id) REFERENCES pedido(id))";
            if (conexao.conectar()) {
                Statement stmt = conexao.retornaStatement();
                stmt.execute(sql);
            }
        } catch (SQLException err) {
            System.err.println(">>>>>>.." + err.getMessage());
        } finally {
            conexao.desconectar();
        }
    }

    public int inserir(Avaliacao avaliacao) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "insert into avaliacao(nota, comentario, pedido_id) values(?, ?, ?)";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, avaliacao.getNota());
                stmt.setString(2, avaliacao.getComentario());
                stmt.setInt(3, avaliacao.getPedido().getId());
                cont = stmt.executeUpdate();
            } else {
                System.out.println("Conexão não estabelecida!");
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }

//    public int alterar(Avaliacao avaliacao) {
//        int cont = 0;
//        try {
//            if (conexao.conectar()) {
//                String sql = "update avaliacao set nota = ?, comentario = ?, pedido_id = ? where id = ?";
//                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                stmt.setInt(1, avaliacao.getNota());
//                stmt.setString(2, avaliacao.getComentario());
//                stmt.setInt(3, avaliacao.getPedido().getId());
//                stmt.setInt(4, avaliacao.getId());
//                cont = stmt.executeUpdate();
//            }
//        } catch (SQLException err) {
//            System.err.println(err.getMessage());
//        } finally {
//            conexao.desconectar();
//            return cont;
//        }
//    }

//    public int remover(Avaliacao avaliacao) {
//        int cont = 0;
//        try {
//            if (conexao.conectar()) {
//                String sql = "delete from avaliacao where id = ?";
//                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                stmt.setInt(1, avaliacao.getId());
//                cont = stmt.executeUpdate();
//            }
//        } catch (SQLException err) {
//            System.err.println(err.getMessage());
//        } finally {
//            conexao.desconectar();
//            return cont;
//        }
//    }

//    public Avaliacao pesquisar(int id) {
//        Avaliacao avaliacao = null;
//        try {
//            if (conexao.conectar()) {
//                String sql = "select * from avaliacao where id = ?";
//                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                stmt.setInt(1, id);
//                ResultSet resultado = stmt.executeQuery();
//                if (resultado.next()) {
//                    int nota = resultado.getInt("nota");
//                    String comentario = resultado.getString("comentario");
//                    int pedidoId = resultado.getInt("pedido_id");
//                    // Supondo que há um método em PedidoDAO para buscar Pedido pelo ID
//                    Pedido pedido = new PedidoDAO().pesquisar(pedidoId);
//                    avaliacao = new Avaliacao(id, nota, comentario, pedido);
//                }
//            }
//        } catch (SQLException err) {
//            System.err.println(err.getMessage());
//        } finally {
//            conexao.desconectar();
//            return avaliacao;
//        }
//    }

    public List<Avaliacao> retornaLista() {
        List<Avaliacao> lista = new ArrayList<>();
        try {
            if (conexao.conectar()) {
                PreparedStatement stmt;
                stmt = conexao.preparedStatement("select * from avaliacao order by id", Statement.RETURN_GENERATED_KEYS);
                ResultSet resultado = stmt.executeQuery();
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    int nota = resultado.getInt("nota");
                    String comentario = resultado.getString("comentario");
                    int pedidoId = resultado.getInt("pedido_id");
                    Pedido pedido = new Pedido();
                    pedido.setId(pedidoId);
                    Avaliacao avaliacao = new Avaliacao(id, nota, comentario, pedido);
                    lista.add(avaliacao);
                }
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return lista;
        }
    }
}

