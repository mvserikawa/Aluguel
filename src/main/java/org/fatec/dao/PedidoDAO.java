package org.fatec.dao;

import org.fatec.Maquina;
import org.fatec.Pedido;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    private Conexao conexao = new Conexao();

    public PedidoDAO() {
        try {
            String sql = "create table if not exists pedido (" +
                    "id int primary key AUTO_INCREMENT, " +
                    "dataRetirada date, " +
                    "dataDevolucao date, "+
                    "status varchar(255), "+
                    "cliente_id int, " +
                    "maquina_id int, " +
                    "entregador_id int, " +
                    "FOREIGN KEY (cliente_id) REFERENCES cliente(id), "+
                    "FOREIGN KEY (maquina_id) REFERENCES maquina(id), "+
                    "FOREIGN KEY (entregador_id) REFERENCES entregador(id))";
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

    public int alugar(Pedido pedido) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "INSERT INTO pedido (cliente_id, maquina_id, status) VALUES (?, ?, ?)";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, pedido.getCliente().getId());
                stmt.setInt(2, pedido.getMaquina().getId());
                stmt.setString(3, "Pedido solicitado");
                cont = stmt.executeUpdate();
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }

//    public int inserir(Pedido pedido) {
//        int cont = 0;
//        try {
//            if (conexao.conectar()) {
//                String sql = "INSERT INTO pedido (cliente_id, dataRetirada, dataDevolucao, maquina_id) VALUES (?, ?, ?, ?)";
//                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                stmt.setInt(1, pedido.getCliente().getId());
//                stmt.setDate(2, Date.valueOf(pedido.getDataRetirada()));
//                stmt.setDate(3, Date.valueOf(pedido.getDataDevolucao()));
//                stmt.setInt(4, pedido.getMaquina().getId());
//                cont = stmt.executeUpdate();
//            }
//        } catch (SQLException err) {
//            System.err.println(err.getMessage());
//        } finally {
//            conexao.desconectar();
//            return cont;
//        }
//    }

    private void inserirMaquinaPedido(int pedidoId, int maquinaId) {
        try {
            String sql = "INSERT INTO maquina_pedido (pedido_id, maquina_id) VALUES (?, ?)";
            PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, pedidoId);
            stmt.setInt(2, maquinaId);
            stmt.executeUpdate();
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        }
    }

    public int entregar(int pedido_id, int entregador_id) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                String sql = "UPDATE pedido SET entregador_id = ?, dataRetirada = CURDATE(), dataDevolucao = DATE_ADD(CURDATE(), INTERVAL 7 DAY), status = 'Pedido entregue' WHERE id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, entregador_id);
                stmt.setInt(2, pedido_id);
                cont = stmt.executeUpdate();
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }

    private void removerMaquinasDoPedido(int pedidoId) {
        try {
            String sql = "DELETE FROM maquina_pedido WHERE pedido_id = ?";
            PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, pedidoId);
            stmt.executeUpdate();
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        }
    }

    public int remover(Pedido pedido) {
        int cont = 0;
        try {
            if (conexao.conectar()) {
                removerMaquinasDoPedido(pedido.getId());

                String sql = "DELETE FROM pedido WHERE id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, pedido.getId());
                cont = stmt.executeUpdate();
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return cont;
        }
    }

    public Pedido pesquisar(int id) {
        Pedido pedido = null;
        try {
            if (conexao.conectar()) {
                String sql = "SELECT * FROM pedido WHERE id = ?";
                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, id);
                ResultSet resultado = stmt.executeQuery();
                if (resultado.next()) {
                    int maquina_id = resultado.getInt("maquina_id");
                    Maquina maquina = new Maquina();
                    maquina.setId(maquina_id);
                    pedido = new Pedido();
                    pedido.setId(id);
                    pedido.setMaquina(maquina);
                } else {
                    throw new SQLException("Pedido não encontrado para o id: " + id);
                }
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
            return pedido;
        }
    }

    public List<Pedido> retornaListaPedidoCliente(int cliente_id) {
        List<Pedido> lista = new ArrayList<>();
        try {
            if (conexao.conectar()) {
                String sql = "SELECT p.id, p.dataRetirada, p.dataDevolucao, " +
                        "p.status, p.maquina_id, m.descricao, m.valorAluguel " +
                        "FROM pedido p " +
                        "INNER JOIN maquina m ON p.maquina_id = m.id " +
                        "WHERE p.cliente_id = ? " +
                        "ORDER BY p.id";

                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, cliente_id);

                ResultSet resultado = stmt.executeQuery();
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    Date dataRetiradaDate = resultado.getDate("dataRetirada");
                    Date dataDevolucaoDate = resultado.getDate("dataDevolucao");

                    // Convertendo datas para LocalDate, usando null se a data for null
                    LocalDate dataRetirada = (dataRetiradaDate != null) ? dataRetiradaDate.toLocalDate() : null;
                    LocalDate dataDevolucao = (dataDevolucaoDate != null) ? dataDevolucaoDate.toLocalDate() : null;

                    String status = resultado.getString("status");

                    int maquina_id = resultado.getInt("maquina_id");
                    String m_desc = resultado.getString("descricao");
                    double m_valor = resultado.getDouble("valorAluguel");

                    Maquina maquina = new Maquina(maquina_id, m_desc, m_valor);

                    Pedido pedido = new Pedido(id, maquina, dataRetirada, dataDevolucao, status);
                    lista.add(pedido);
                }
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
        }
        return lista;
    }

    public List<Pedido> retornaListaPedidoEntregador(int entregador_id) {
        List<Pedido> lista = new ArrayList<>();
        try {
            if (conexao.conectar()) {
                String sql = "SELECT p.id, p.dataRetirada, p.dataDevolucao, " +
                        "p.status, p.maquina_id, m.descricao, m.valorAluguel " +
                        "FROM pedido p " +
                        "INNER JOIN maquina m ON p.maquina_id = m.id " +
                        "WHERE p.entregador_id = ? " +
                        "ORDER BY p.id";

                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, entregador_id);

                ResultSet resultado = stmt.executeQuery();
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    Date dataRetiradaDate = resultado.getDate("dataRetirada");
                    Date dataDevolucaoDate = resultado.getDate("dataDevolucao");

                    // Convertendo datas para LocalDate, usando null se a data for null
                    LocalDate dataRetirada = (dataRetiradaDate != null) ? dataRetiradaDate.toLocalDate() : null;
                    LocalDate dataDevolucao = (dataDevolucaoDate != null) ? dataDevolucaoDate.toLocalDate() : null;

                    String status = resultado.getString("status");

                    int maquina_id = resultado.getInt("maquina_id");
                    String m_desc = resultado.getString("descricao");
                    double m_valor = resultado.getDouble("valorAluguel");

                    Maquina maquina = new Maquina(maquina_id, m_desc, m_valor);

                    Pedido pedido = new Pedido(id, maquina, dataRetirada, dataDevolucao, status);
                    lista.add(pedido);
                }
            }
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        } finally {
            conexao.desconectar();
        }
        return lista;
    }

    public List<Pedido> retornaListaPedidoSolicitado() {
        List<Pedido> lista = new ArrayList<>();
        try {
            if (conexao.conectar()) {
                String sql = "SELECT p.id, p.dataRetirada, p.dataDevolucao, " +
                        "p.status, p.maquina_id, m.descricao, m.valorAluguel " +
                        "FROM pedido p " +
                        "INNER JOIN maquina m ON p.maquina_id = m.id " +
                        "WHERE p.status = 'Pedido solicitado' " +
                        "ORDER BY p.id";

                PreparedStatement stmt = conexao.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ResultSet resultado = stmt.executeQuery();
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    Date dataRetiradaDate = resultado.getDate("dataRetirada");
                    Date dataDevolucaoDate = resultado.getDate("dataDevolucao");

                    // Convertendo datas para LocalDate, usando null se a data for null
                    LocalDate dataRetirada = (dataRetiradaDate != null) ? dataRetiradaDate.toLocalDate() : null;
                    LocalDate dataDevolucao = (dataDevolucaoDate != null) ? dataDevolucaoDate.toLocalDate() : null;

                    String status = resultado.getString("status");

                    int maquina_id = resultado.getInt("maquina_id");
                    String m_desc = resultado.getString("descricao");
                    double m_valor = resultado.getDouble("valorAluguel");

                    Maquina maquina = new Maquina(maquina_id, m_desc, m_valor);

                    Pedido pedido = new Pedido(id, maquina, dataRetirada, dataDevolucao, status);
                    lista.add(pedido);
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
