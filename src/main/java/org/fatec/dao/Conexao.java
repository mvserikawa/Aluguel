package org.fatec.dao;

import java.sql.*;

public class Conexao {
    private Connection conexao;

    public Connection getConexao() {
        return conexao;
    }
    public boolean conectar(){
        try{
            String url = "jdbc:mysql://localhost:3306/sistemamaquina";
            String user = "root";
            String pass = "";
            this.conexao = DriverManager.getConnection(url, user, pass);
            System.out.println("CONECTADO>>>>>>>>>>>>");
            return true;
        }
        catch(SQLException err){
            System.out.println("NAO CONECTADO>>>>>>>>>>>>"+err.getMessage());
            return false;
        }
    }
    public boolean desconectar(){
        try{
            if(!conexao.isClosed()){
                conexao.close();
            }
            return true;
        }
        catch(SQLException err){
            return false;
        }
    }

    public Statement retornaStatement() throws SQLException{
        return conexao.createStatement();
    }

    public PreparedStatement preparedStatement(String sql) throws SQLException {
        return conexao.prepareStatement(sql);
    }

    public PreparedStatement preparedStatement(String sql, int returnGeneratedKeys) throws SQLException {
        return conexao.prepareStatement(sql, returnGeneratedKeys);
    }
}
