package org.fatec.ui.pedido;

import org.fatec.Cliente;
import org.fatec.Pedido;
import org.fatec.dao.ClienteDAO;
import org.fatec.dao.PedidoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VisualizarPedidosClientesUI extends JFrame implements ActionListener {
    private JPanel contentPane;
    private JTable tblPedidos;
    private JButton btnVoltar;
    public JLabel idUsuario;

    public VisualizarPedidosClientesUI() {
        setTitle("Visualizar Pedidos de Clientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        contentPane = new JPanel(new BorderLayout());

        // Inicializa idUsuario
        idUsuario = new JLabel();
        contentPane.add(idUsuario, BorderLayout.NORTH);

        tblPedidos = new JTable();
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

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.pesquisar(usuario_id);

        PedidoDAO pedidoDAO = new PedidoDAO();
        List<Pedido> lista = pedidoDAO.retornaListaPedidoCliente(cliente.getId());

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
            MenuPedidosClienteUI formMenu = new MenuPedidosClienteUI();

            formMenu.idUsuario.setText(idUsuario.getText());
            formMenu.idUsuario.setVisible(false);
            formMenu.pack();

            dispose();

            formMenu.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VisualizarPedidosClientesUI ui = new VisualizarPedidosClientesUI();
            // ui.setUsuarioId("123"); // Exemplo de configuração do ID do usuário, pode ser removido.
        });
    }
}
