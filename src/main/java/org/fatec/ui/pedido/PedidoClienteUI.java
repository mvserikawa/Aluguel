package org.fatec.ui.pedido;

import org.fatec.Cliente;
import org.fatec.Maquina;
import org.fatec.Pedido;
import org.fatec.dao.ClienteDAO;
import org.fatec.dao.MaquinaDAO;
import org.fatec.dao.PedidoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PedidoClienteUI extends JFrame implements ActionListener {
    private JPanel contentPane;
    private JTable tblMaquinas;
    private JButton btnVoltar;
    private JButton btnAlugar;
    public JLabel idUsuario;

    public PedidoClienteUI() {
        setTitle("Pedidos de Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setVisible(true);
    }

    private void initComponents() {
        List<Maquina> lista = new ArrayList<>();
        MaquinaDAO maquinaDAO = new MaquinaDAO();

        lista = maquinaDAO.retornaLista();

        Object[][] data = new Object[lista.size()][6];
        for (int i = 0; i < lista.size(); i++) {
            Maquina maquina = lista.get(i);
            data[i][0] = maquina.getId();
            data[i][1] = maquina.getTipo();
            data[i][2] = maquina.getDescricao();
            data[i][3] = maquina.getEspecificacao();
            data[i][4] = maquina.getValorAluguel();
            data[i][5] = maquina.getStatus();
        }

        // Nomes das colunas
        String[] columnNames = {"Id","Tipo", "Descrição", "Especificação", "Valor Aluguel", "Status"};

        // Cria o modelo da tabela com os dados
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        // Conectar os componentes da interface com os campos da classe
        contentPane = new JPanel(new BorderLayout());

        // Configuração da tabela de máquinas
        tblMaquinas = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(tblMaquinas);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Configuração dos botões
        btnVoltar = new JButton("Voltar");
        btnAlugar = new JButton("Alugar");

        btnVoltar.addActionListener(this);
        btnAlugar.addActionListener(this);

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnVoltar);
        panelButtons.add(btnAlugar);

        contentPane.add(panelButtons, BorderLayout.SOUTH);

        // Configuração do label do usuário
        idUsuario = new JLabel("idUsuario");
        contentPane.add(idUsuario, BorderLayout.NORTH);

        setContentPane(contentPane);

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
        } else if (e.getSource() == btnAlugar) {
            int selectedRow = tblMaquinas.getSelectedRow();
            if (selectedRow != -1) { // Verifica se alguma linha está selecionada
                // Obtém os dados da linha selecionada
                int idMaquina = (int) tblMaquinas.getValueAt(selectedRow, 0);
                int usuario_id = Integer.parseInt(idUsuario.getText());
                String tipo = (String) tblMaquinas.getValueAt(selectedRow, 1);
                String descricao = (String) tblMaquinas.getValueAt(selectedRow, 2);
                String especificacao = (String) tblMaquinas.getValueAt(selectedRow, 3);
                double valorAluguel = (double) tblMaquinas.getValueAt(selectedRow, 4);
                String status = (String) tblMaquinas.getValueAt(selectedRow, 5);

                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente cliente = clienteDAO.pesquisar(usuario_id);

                Maquina maquina = new Maquina();
                maquina.setId(idMaquina);

                Pedido pedido = new Pedido();
                PedidoDAO pedidoDAO = new PedidoDAO();
                pedido.setCliente(cliente);
                pedido.setMaquina(maquina);

                pedidoDAO.alugar(pedido);

                maquina.setStatus("Indisponível");
                MaquinaDAO maquinaDAO = new MaquinaDAO();
                maquinaDAO.alterarStatus(maquina);

                MenuPedidosClienteUI formMenu = new MenuPedidosClienteUI();

                formMenu.idUsuario.setText(idUsuario.getText());
                formMenu.idUsuario.setVisible(false);
                formMenu.pack();

                dispose();

                formMenu.setVisible(true);

            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PedidoClienteUI());
    }
}
