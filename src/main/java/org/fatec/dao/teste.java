package org.fatec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class teste{
    public static void main(String[] args){
        Conexao conexao = new Conexao();

        try {
            if(conexao.conectar()){
                String sql = "select * from maquina where id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, 1);
                ResultSet resultado = stmt.executeQuery();
                if(resultado.next()){
                    System.out.println("Tipo da máquina: " + resultado.getString("tipo"));
                }
            }
        } catch (SQLException err){
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
        }

    }
}
