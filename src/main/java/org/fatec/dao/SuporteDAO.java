package org.fatec.dao;

import org.fatec.Maquina;
import org.fatec.Pedido;
import org.fatec.Suporte;
import org.fatec.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SuporteDAO {
    private Conexao conexao = new Conexao();

    public SuporteDAO() {
        try {
            String sqlSuporte = "CREATE TABLE IF NOT EXISTS suporte (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "comentario TEXT, " +
                    "status VARCHAR(200), "+
                    "cliente_id INT, " +
                    "pedido_id INT, "+
                    "maquina_id INT, "+
                    "FOREIGN KEY (cliente_id) REFERENCES cliente(id), "+
                    "FOREIGN KEY (pedido_id) REFERENCES pedido(id), "+
                    "FOREIGN KEY (maquina_id) REFERENCES maquina(id))";
            if (conexao.conectar()) {
                Statement stmt = conexao.retornaStatement();
                stmt.execute(sqlSuporte);
            }
        } catch (SQLException err) {
            System.err.println(">>>>>>.." + err.getMessage());
        } finally {
            conexao.desconectar();
        }
    }

    public int inserir(Suporte suporte) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "INSERT INTO suporte (comentario, status, cliente_id, pedido_id, maquina_id)" +
                        " VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, suporte.getComentario());
                stmt.setString(2, suporte.getStatus());
                stmt.setInt(3, suporte.getCliente().getId());
                stmt.setInt(4, suporte.getPedido().getId());
                stmt.setInt(5, suporte.getMaquina().getId());
                cont = stmt.executeUpdate();
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }

    public int alterarStatus(int suporte_id) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "UPDATE suporte SET status = 'Resolvido' WHERE id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql);
                stmt.setInt(1, suporte_id);
                cont = stmt.executeUpdate();
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }

    public int remover(Suporte suporte) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "DELETE FROM suporte WHERE id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql);
                stmt.setInt(1, suporte.getId());
                cont = stmt.executeUpdate();
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }
//
//    public Suporte pesquisar(int id) {
//        Suporte suporte = null;
//        try {
//            if (conexao.conectar()) {
//                String sql = "SELECT * FROM suporte WHERE id = ?";
//                PreparedStatement stmt = conexao.preparedStatement(sql);
//                stmt.setInt(1, id);
//                ResultSet resultado = stmt.executeQuery();
//                if (resultado.next()) {
//                    String descricao = resultado.getString("descricao");
//                    int clienteId = resultado.getInt("cliente_id");
//
//                    Cliente cliente = new ClienteDAO().pesquisar(clienteId);
//
//                    suporte = new Suporte(descricao, cliente);
//                    suporte.setId(id);
//                }
//            }
//        } catch (SQLException err) {
//            System.err.println(err.getMessage());
//        } finally {
//            conexao.desconectar();
//            return suporte;
//        }
//    }
//
    public List<Suporte> retornaLista() {
        List<Suporte> lista = new ArrayList<>();
        try {
            if (conexao.conectar()) {
                PreparedStatement stmt;
                stmt = conexao.preparedStatement("SELECT s.id, s.pedido_id, " +
                                "s.comentario, s.status, " +
                                "m.descricao, u.nome " +
                                "FROM suporte s " +
                                "INNER JOIN maquina m ON s.maquina_id = m.id " +
                                "INNER JOIN cliente c ON s.cliente_id = c.id " +
                                "INNER JOIN usuario u ON u.id = c.usuario_id " +
                                "ORDER BY id");
                ResultSet resultado = stmt.executeQuery();
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String comentario = resultado.getString("comentario");
                    int pedido_id = resultado.getInt("pedido_id");
                    String desc_maquina = resultado.getString("descricao");
                    String status = resultado.getString("status");
                    String nome_usuario = resultado.getString("nome");

                    Maquina maquina = new Maquina();
                    maquina.setDescricao(desc_maquina);

                    Pedido pedido = new Pedido();
                    pedido.setId(pedido_id);

                    Usuario usuario = new Usuario();
                    usuario.setNome(nome_usuario);

                    Suporte suporte = new Suporte();
                    suporte.setId(id);
                    suporte.setComentario(comentario);
                    suporte.setStatus(status);
                    suporte.setPedido(pedido);
                    suporte.setMaquina(maquina);
                    suporte.setUsuario(usuario);

                    lista.add(suporte);
                }
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
        }
        return lista;
    }
}
