package org.fatec.dao;

import org.fatec.Usuario;

import java.sql.*;

public class UsuarioDAO {
    private Conexao conexao = new Conexao();

    public UsuarioDAO() {
        try {
            String sqlUsuario = "CREATE TABLE IF NOT EXISTS usuario (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "nome VARCHAR(100), " +
                    "telefone VARCHAR(20), " +
                    "email VARCHAR(100), " +
                    "senha VARCHAR(100), " +
                    "categoria VARCHAR(100))";
            if (conexao.conectar()) {
                Statement stmt = conexao.retornaStatement();
                stmt.execute(sqlUsuario);
            }
        } catch (SQLException err) {
            System.err.println("Erro ao criar tabela de usuários: " + err.getMessage());
        } finally {
            conexao.desconectar();
        }
    }

    public int inserir(Usuario usuario) {
        int generatedId = -1;
        try {
            if (conexao.conectar()) {
                String sql = "INSERT INTO usuario (nome, telefone, email, senha, categoria) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, usuario.getNome());
                stmt.setString(2, usuario.getTelefone());
                stmt.setString(3, usuario.getEmail());
                stmt.setString(4, usuario.getSenha());
                stmt.setString(5, usuario.getCategoria());
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    // Recupera a chave gerada automaticamente
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            generatedId = generatedKeys.getInt(1);
                        }
                    }
                }
            }
        } catch (SQLException err) {
            System.err.println("Erro ao inserir usuário: " + err.getMessage());
        } finally {
            conexao.desconectar();
            return generatedId;
        }
    }


    // Tirar esse método depois ou refatorar

    public Usuario login(String email) {
        Usuario usuario = null;
        try {
            if (conexao.conectar()) {
                String sql = "SELECT * FROM usuario WHERE email = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, email);
                ResultSet resultado = stmt.executeQuery();
                if (resultado.next()) {
                    usuario = new Usuario();
                    usuario.setNome(resultado.getString("nome"));
                    usuario.setTelefone(resultado.getString("telefone"));
                    usuario.setEmail(resultado.getString("email"));
                    usuario.setSenha(resultado.getString("senha"));
                    usuario.setCategoria(resultado.getString("categoria"));
                    usuario.setId(resultado.getInt("id"));
                }
            }
        } catch (SQLException err) {
            System.err.println("Erro ao buscar usuário por email: " + err.getMessage());
        } finally {
            conexao.desconectar();
            return usuario;
        }
    }

    public Usuario buscaId(int id){
        Usuario usuario = null;
        try {
            if (conexao.conectar()) {
                String sql = "SELECT * FROM usuario WHERE id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, id);
                ResultSet resultado = stmt.executeQuery();
                if (resultado.next()) {
                    usuario = new Usuario();
                    usuario.setNome(resultado.getString("nome"));
                    usuario.setTelefone(resultado.getString("telefone"));
                    usuario.setEmail(resultado.getString("email"));
                    usuario.setSenha(resultado.getString("senha"));
                    usuario.setCategoria(resultado.getString("categoria"));
                    usuario.setId(resultado.getInt("id"));
                }
            }
        } catch (SQLException err) {
            System.err.println("Erro ao buscar usuário por id: " + err.getMessage());
        } finally {
            conexao.desconectar();
            return usuario;
        }
    }

    // Outros métodos de CRUD e utilitários podem ser implementados conforme necessário
}
