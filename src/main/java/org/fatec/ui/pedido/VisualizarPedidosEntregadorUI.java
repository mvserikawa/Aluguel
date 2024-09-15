package org.fatec.ui.pedido;

import org.fatec.Entregador;
import org.fatec.Pedido;
import org.fatec.dao.EntregadorDAO;
import org.fatec.dao.PedidoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VisualizarPedidosEntregadorUI extends JFrame implements ActionListener {
    private JPanel contentPane;
    private JTable tblPedidos;
    private JButton btnVoltar;
    public JLabel idUsuario;

    public VisualizarPedidosEntregadorUI() {
        setTitle("Visualizar Pedidos do Entregador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setVisible(true);
    }

    private void initComponents() {
        contentPane = new JPanel(new BorderLayout());

        // Label do ID do Usuário
        idUsuario = new JLabel();
        contentPane.add(idUsuario, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(tblPedidos);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Painel dos botões
        JPanel buttonPanel = new JPanel();
        btnVoltar = new JButton("Voltar");

        btnVoltar.addActionListener(this);

        buttonPanel.add(btnVoltar);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

    public void setUsuarioId(String usuarioId) {
        idUsuario.setText(usuarioId);
        popularTabela();
    }

    public void popularTabela() {
        int usuario_id = Integer.parseInt(idUsuario.getText());

        EntregadorDAO entregadorDAO = new EntregadorDAO();
        Entregador entregador = entregadorDAO.pesquisar(usuario_id);

        PedidoDAO pedidoDAO = new PedidoDAO();
        List<Pedido> lista = pedidoDAO.retornaListaPedidoEntregador(entregador.getId());

        Object[][] data = new Object[lista.size()][7];
        for (int i = 0; i < lista.size(); i++) {
            Pedido pedido = lista.get(i);
            data[i][0] = pedido.getId();
            data[i][1] = pedido.getDataRetirada();
            data[i][2] = pedido.getDataDevolucao();
            data[i][3] = pedido.getStatus();
            data[i][4] = pedido.getMaquina().getId();
            data[i][5] = pedido.getMaquina().getDescricao();
            data[i][6] = pedido.getMaquina().getValorAluguel();
        }

        String[] columnNames = {"Id do pedido", "Data de retirada", "Data de devolução", "Status do pedido", "Id da máquina", "Descrição da máquina", "Valor do aluguel"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblPedidos.setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnVoltar) {
            MenuPedidosEntregadorUI formMenu = new MenuPedidosEntregadorUI();

            formMenu.idUsuario.setText(idUsuario.getText());
            formMenu.idUsuario.setVisible(false);
            formMenu.pack();

            dispose();

            formMenu.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VisualizarPedidosEntregadorUI());
    }
}
