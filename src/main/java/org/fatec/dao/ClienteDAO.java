package org.fatec.dao;

import org.fatec.Cliente;
import org.fatec.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Conexao conexao = new Conexao();

    public ClienteDAO() {
        try {
            String sql = "create table if not exists cliente (" +
                    "id int primary key AUTO_INCREMENT, " +
                    "endereco varchar(255), " +
                    "usuario_id int, " +
                    "FOREIGN KEY (usuario_id) REFERENCES usuario(id))";
            if (conexao.conectar()) {
                Statement stmt = conexao.retornaStatement();
                stmt.execute(sql);
            }
        } catch (SQLException err) {
            System.err.println(">>cliente>>>>.." + err.getMessage());
        } finally {
            conexao.desconectar();
        }
    }

    public int inserir(Cliente cliente) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "insert into cliente(endereco, usuario_id) values(?, ?)";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, cliente.getEndereco());
                stmt.setInt(2, cliente.getUsuario_id());
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

    public int alterar(Cliente cliente, Usuario usuario) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                // Primeiro comando UPDATE para cliente
                String sqlCliente = "update cliente set endereco = ? where usuario_id = ?";
                PreparedStatement stmtCliente = conexao.preparedStatement(sqlCliente);
                stmtCliente.setString(1, cliente.getEndereco());
                stmtCliente.setInt(2, cliente.getUsuario_id());
                cont += stmtCliente.executeUpdate();  // Adicione ao contador de registros afetados

                // Segundo comando UPDATE para usuario
                String sqlUsuario = "update usuario set nome = ?, telefone = ? where id = ?";
                PreparedStatement stmtUsuario = conexao.preparedStatement(sqlUsuario);
                stmtUsuario.setString(1, usuario.getNome());
                stmtUsuario.setString(2, usuario.getTelefone());
                stmtUsuario.setInt(3, cliente.getUsuario_id());  // Use o mesmo ID do cliente para atualização do usuário
                cont += stmtUsuario.executeUpdate();  // Adicione ao contador de registros afetados
            }
        } catch (SQLException err) {
            System.err.println("Erro ao executar SQL: " + err.getMessage());
        } finally {
            conexao.desconectar();
        }
        return cont;
    }


//    public int remover(Cliente cliente) {
//        int cont = 0;
//        try {
//            if (conexao.conectar()) {
//                String sql = "delete from cliente where id = ?";
//                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                stmt.setInt(1, cliente.getId());
//                cont = stmt.executeUpdate();
//            }
//        } catch (SQLException err) {
//            System.err.println(err.getMessage());
//        } finally {
//            conexao.desconectar();
//            return cont;
//        }
//    }

    public Cliente pesquisar(int idUsuario) {
        Cliente cliente = null;
        try {
            if (conexao.conectar()) {
                String sql = "select * from cliente where usuario_id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, idUsuario);
                ResultSet resultado = stmt.executeQuery();
                if (resultado.next()) {
                    String endereco = resultado.getString("endereco");
                    int id = resultado.getInt("id");
                    cliente = new Cliente(endereco);
                    cliente.setId(id);
                } else {
                    throw new SQLException("Cliente não encontrado para o id de usuário: " + idUsuario);
                }
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cliente;
        }
    }

//    public List<Cliente> retornaLista(String busca) {
//        List<Cliente> lista = new ArrayList<>();
//        try {
//            if (conexao.conectar()) {
//                PreparedStatement stmt;
//                if (busca != null && !busca.isEmpty()) {
//                    stmt = conexao.preparedStatement("select * from cliente where nome like ? order by id", Statement.RETURN_GENERATED_KEYS);
//                    stmt.setString(1, "%" + busca + "%");
//                } else {
//                    stmt = conexao.preparedStatement("select * from cliente order by id", Statement.RETURN_GENERATED_KEYS);
//                }
//                ResultSet resultado = stmt.executeQuery();
//                while (resultado.next()) {
//                    int id = resultado.getInt("id");
//                    String nome = resultado.getString("nome");
//                    String telefone = resultado.getString("telefone");
//                    String endereco = resultado.getString("endereco");
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
