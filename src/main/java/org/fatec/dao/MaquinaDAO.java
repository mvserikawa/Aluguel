package org.fatec.dao;

import org.fatec.Maquina;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaquinaDAO {
    private Conexao conexao = new Conexao();

    public MaquinaDAO() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS maquina (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "tipo VARCHAR(100), " +
                    "descricao VARCHAR(255), " +
                    "especificacao TEXT, " +
                    "valorAluguel decimal(10, 2), " +
                    "status VARCHAR(255))";
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

    public int inserir(Maquina maquina) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "INSERT INTO maquina (tipo, descricao, especificacao, valorAluguel, status) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conexao.preparedStatement(sql);
                stmt.setString(1, maquina.getTipo());
                stmt.setString(2, maquina.getDescricao());
                stmt.setString(3, maquina.getEspecificacao());
                stmt.setDouble(4, maquina.getValorAluguel());
                stmt.setString(5, "Disponível");
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

    public int alterar(Maquina maquina) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "UPDATE maquina SET tipo = ?, descricao = ?, especificacao = ?, valorAluguel = ? WHERE id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql);
                stmt.setString(1, maquina.getTipo());
                stmt.setString(2, maquina.getDescricao());
                stmt.setString(3, maquina.getEspecificacao());
                stmt.setDouble(4, maquina.getValorAluguel());
                stmt.setInt(5, maquina.getId());
                cont = stmt.executeUpdate();
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }

    public int alterarStatus(Maquina maquina) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "UPDATE maquina SET status = ? WHERE id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql);
                stmt.setString(1, maquina.getStatus());
                stmt.setInt(2, maquina.getId());
                cont = stmt.executeUpdate();
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }

    public int remover(int id) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "DELETE FROM maquina WHERE id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql);
                stmt.setInt(1, id);
                cont = stmt.executeUpdate();
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }

    public Maquina pesquisar(int id) {
        Maquina maquina = null;
        try {
            if (conexao.conectar()) {
                String sql = "SELECT * FROM maquina WHERE id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql);
                stmt.setInt(1, id);
                ResultSet resultado = stmt.executeQuery();
                if (resultado.next()) {
                    String tipo = resultado.getString("tipo");
                    String descricao = resultado.getString("descricao");
                    String especificacao = resultado.getString("especificacao");
                    double valorAluguel = resultado.getDouble("valorAluguel");
                    String status = resultado.getString("status");
                    maquina = new Maquina(id, tipo, descricao, especificacao, valorAluguel, status);
                }
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return maquina;
        }
    }

    public List<Maquina> retornaLista() {
        List<Maquina> lista = new ArrayList<>();
        try {
            if (conexao.conectar()) {
                PreparedStatement stmt;
                stmt = conexao.preparedStatement("SELECT * FROM maquina WHERE status = 'Disponível' ORDER BY id");
                ResultSet resultado = stmt.executeQuery();
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String tipo = resultado.getString("tipo");
                    String descricao = resultado.getString("descricao");
                    String especificacao = resultado.getString("especificacao");
                    double valorAluguel = resultado.getDouble("valorAluguel");
                    String status = resultado.getString("status");
                    Maquina maquina = new Maquina(id, tipo, descricao, especificacao, valorAluguel, status);
                    lista.add(maquina);
                }
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return lista;
        }
    }

    public List<Maquina> retornaListaTudo() {
        List<Maquina> lista = new ArrayList<>();
        try {
            if (conexao.conectar()) {
                PreparedStatement stmt;
                stmt = conexao.preparedStatement("SELECT * FROM maquina ORDER BY id");
                ResultSet resultado = stmt.executeQuery();
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String tipo = resultado.getString("tipo");
                    String descricao = resultado.getString("descricao");
                    String especificacao = resultado.getString("especificacao");
                    double valorAluguel = resultado.getDouble("valorAluguel");
                    String status = resultado.getString("status");
                    Maquina maquina = new Maquina(id, tipo, descricao, especificacao, valorAluguel, status);
                    lista.add(maquina);
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
