package org.fatec.dao;

import org.fatec.Funcionario;
import org.fatec.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    private Conexao conexao = new Conexao();

    public FuncionarioDAO() {
        try {
            String sql = "create table if not exists funcionario (" +
                    "id int primary key AUTO_INCREMENT, " +
                    "setor varchar(255), " +
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

    public int inserir(Funcionario funcionario) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "insert into funcionario(setor, usuario_id) values(?, ?)";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, funcionario.getSetor());
                stmt.setInt(2, funcionario.getUsuarioId());
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

    public int alterar(Funcionario funcionario, Usuario usuario) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sqlFuncionario = "update funcionario set setor = ? where usuario_id = ?";
                PreparedStatement stmtFuncionario = conexao.preparedStatement(sqlFuncionario);
                stmtFuncionario.setString(1, funcionario.getSetor());
                stmtFuncionario.setInt(2, funcionario.getUsuarioId());
                cont += stmtFuncionario.executeUpdate();

                String sqlUsuario = "update usuario set nome = ?, telefone = ? where id = ?";
                PreparedStatement stmtUsuario = conexao.preparedStatement(sqlUsuario);
                stmtUsuario.setString(1, usuario.getNome());
                stmtUsuario.setString(2, usuario.getTelefone());
                stmtUsuario.setInt(3, funcionario.getUsuarioId());  // Use o mesmo ID do cliente para atualização do usuário
                cont += stmtUsuario.executeUpdate();
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }

    public int remover(Funcionario funcionario) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "delete from funcionario where id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, funcionario.getId());
                cont = stmt.executeUpdate();
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }

    public Funcionario pesquisar(int usuarioId) {
        Funcionario funcionario = null;
        try {
            if (conexao.conectar()) {
                String sql = "select * from funcionario where usuario_id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, usuarioId);
                ResultSet resultado = stmt.executeQuery();
                if (resultado.next()) {
                    String setor = resultado.getString("setor");
                    funcionario = new Funcionario(setor, usuarioId);
                }
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return funcionario;
        }
    }

//    public List<Funcionario> retornaLista(String busca) {
//        List<Funcionario> lista = new ArrayList<>();
//        try {
//            if (conexao.conectar()) {
//                PreparedStatement stmt;
//                if (busca != null && !busca.isEmpty()) {
//                    stmt = conexao.preparedStatement("select * from funcionario where nome like ? order by id", Statement.RETURN_GENERATED_KEYS);
//                    stmt.setString(1, "%" + busca + "%");
//                } else {
//                    stmt = conexao.preparedStatement("select * from funcionario order by id", Statement.RETURN_GENERATED_KEYS);
//                }
//                ResultSet resultado = stmt.executeQuery();
//                while (resultado.next()) {
//                    int id = resultado.getInt("id");
//                    String nome = resultado.getString("nome");
//                    String telefone = resultado.getString("telefone");
//                    String email = resultado.getString("email");
//                    String senha = resultado.getString("senha");
//                    String setor = resultado.getString("setor");
//                    Funcionario funcionario = new Funcionario(nome, telefone, email, senha, setor);
//                    funcionario.setId(id);
//                    lista.add(funcionario);
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
