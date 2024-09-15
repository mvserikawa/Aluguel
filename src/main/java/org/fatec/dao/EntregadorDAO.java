package org.fatec.dao;

import org.fatec.Entregador;
import org.fatec.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregadorDAO {
    private Conexao conexao = new Conexao();

    public EntregadorDAO() {
        try {
            String sql = "create table if not exists entregador (" +
                    "id int primary key AUTO_INCREMENT, " +
                    "placa varchar(100), " +
                    "usuario_id int, " +
                    "FOREIGN KEY (usuario_id) REFERENCES usuario(id))";
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

    public int inserir(Entregador entregador) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "insert into entregador(placa, usuario_id) values(?, ?)";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, entregador.getPlaca());
                stmt.setInt(2, entregador.getUsuario_id());
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

    public int alterar(Entregador entregador, Usuario usuario) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sqlEntregador = "update entregador set placa = ? where usuario_id = ?";
                PreparedStatement stmtEntregador = conexao.preparedStatement(sqlEntregador);
                stmtEntregador.setString(1, entregador.getPlaca());
                stmtEntregador.setInt(2, entregador.getUsuario_id());
                cont += stmtEntregador.executeUpdate();

                String sqlUsuario = "update usuario set nome = ?, telefone = ? where id = ?";
                PreparedStatement stmtUsuario = conexao.preparedStatement(sqlUsuario);
                stmtUsuario.setString(1, usuario.getNome());
                stmtUsuario.setString(2, usuario.getTelefone());
                stmtUsuario.setInt(3, entregador.getUsuario_id());  // Use o mesmo ID do cliente para atualização do usuário
                cont += stmtUsuario.executeUpdate();
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }

    public int remover(Entregador entregador) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "delete from entregador where id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, entregador.getId());
                cont = stmt.executeUpdate();
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }

    public Entregador pesquisar(int id) {
        Entregador entregador = null;
        try {
            if (conexao.conectar()) {
                String sql = "select * from entregador where usuario_id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, id);
                ResultSet resultado = stmt.executeQuery();
                if (resultado.next()) {
                    String placa = resultado.getString("placa");
                    int usuarioId = id;
                    entregador = new Entregador(placa, usuarioId);
                    entregador.setId(resultado.getInt("id"));
                }
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return entregador;
        }
    }

//    public List<Entregador> retornaLista(String busca) {
//        List<Entregador> lista = new ArrayList<>();
//        try {
//            if (conexao.conectar()) {
//                PreparedStatement stmt;
//                if (busca != null && !busca.isEmpty()) {
//                    stmt = conexao.preparedStatement("select * from entregador where nome like ? order by id", Statement.RETURN_GENERATED_KEYS);
//                    stmt.setString(1, "%" + busca + "%");
//                } else {
//                    stmt = conexao.preparedStatement("select * from entregador order by id", Statement.RETURN_GENERATED_KEYS);
//                }
//                ResultSet resultado = stmt.executeQuery();
//                while (resultado.next()) {
//                    int id = resultado.getInt("id");
//                    String nome = resultado.getString("nome");
//                    String telefone = resultado.getString("telefone");
//                    String email = resultado.getString("email");
//                    String senha = resultado.getString("senha");
//                    String placa = resultado.getString("placa");
//                    Entregador entregador = new Entregador(nome, telefone, email, senha, placa);
//                    entregador.setId(id);
//                    lista.add(entregador);
//                }
//            }
//        } catch (SQLException err) {
//            System.err.println(err.getMessage());
//        } finally {
//            conexao.desconectar();
//            return lista;
//        }
//    }
}
