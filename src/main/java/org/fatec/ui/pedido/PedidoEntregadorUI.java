package org.fatec.ui.pedido;

import org.fatec.Entregador;
import org.fatec.Pedido;
import org.fatec.dao.EntregadorDAO;
import org.fatec.dao.PedidoDAO;
import org.fatec.ui.entregador.MenuEntregadorUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PedidoEntregadorUI extends JFrame implements ActionListener {
    private JPanel contentPane;
    private JTable tblMaquinas;
    private JButton btnVoltar;
    private JButton btnEntregar;
    public JLabel idUsuario;
    private JButton btnPerfil;

    public PedidoEntregadorUI() {
        setTitle("Pedidos de Entregador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);// Centraliza a janela na tela
        popularTabela();
        setVisible(true);
        btnPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuEntregadorUI formMenu = new MenuEntregadorUI();

                formMenu.lblValorId.setText(idUsuario.getText());
                formMenu.lblValorId.setVisible(false);
                formMenu.pack();

                dispose();

                formMenu.setVisible(true);
            }
        });
    }

    private void initComponents() {
        // Conectar os componentes da interface com os campos da classe
        contentPane = new JPanel(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(tblMaquinas);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Configuração dos botões
        btnVoltar = new JButton("Voltar");
        btnEntregar = new JButton("Entregar");

        btnVoltar.addActionListener(this);
        btnEntregar.addActionListener(this);

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnVoltar);
        panelButtons.add(btnEntregar);

        contentPane.add(panelButtons, BorderLayout.SOUTH);

        // Configuração do label do usuário
        idUsuario = new JLabel();
        contentPane.add(idUsuario, BorderLayout.NORTH);

        setContentPane(contentPane);
    }

    public void popularTabela() {

        PedidoDAO pedidoDAO = new PedidoDAO();
        List<Pedido> lista = pedidoDAO.retornaListaPedidoSolicitado();


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
        tblMaquinas.setModel(model);
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
        } else if (e.getSource() == btnEntregar) {
            int usuarioId = Integer.parseInt(idUsuario.getText());

            Entregador entregador = new Entregador();
            EntregadorDAO entregadorDAO = new EntregadorDAO();
            entregador = entregadorDAO.pesquisar(usuarioId);

            int selectedRow = tblMaquinas.getSelectedRow();
            if (selectedRow != -1){
                int id_pedido = (int) tblMaquinas.getValueAt(selectedRow, 0);

                PedidoDAO pedidoDAO = new PedidoDAO();
                pedidoDAO.entregar(id_pedido, entregador.getId());

                MenuPedidosEntregadorUI formMenu = new MenuPedidosEntregadorUI();

                formMenu.idUsuario.setText(idUsuario.getText());
                formMenu.idUsuario.setVisible(false);
                formMenu.pack();

                dispose();

                formMenu.setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PedidoEntregadorUI());
    }
}
