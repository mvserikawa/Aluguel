package org.fatec.ui.maquina;

import org.fatec.Maquina;
import org.fatec.dao.MaquinaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BuscarMaquinaUI extends JDialog {
    private JPanel contentPane;
    private JTable tblMaquinas;
    private JButton btnVoltar;
    private JButton btnReceber;
    private JScrollBar scrollBar1;
    public JLabel idUsuario;

    public BuscarMaquinaUI() {
        createUIComponents();  // Inicializa os componentes da UI
        setContentPane(contentPane);
        setModal(true);
        btnVoltar.addActionListener(e -> onVoltar());
        populateTable();
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MaquinaUI formMenuMaq = new MaquinaUI();
                formMenuMaq.lblUsuarioId.setText(idUsuario.getText());
                formMenuMaq.lblUsuarioId.setVisible(false);
                formMenuMaq.pack();

                dispose();

                formMenuMaq.setVisible(true);
            }
        });

        btnReceber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblMaquinas.getSelectedRow();
                if (selectedRow != -1) {
                    // Obter o ID da máquina selecionada
                    int maquinaId = (int) tblMaquinas.getValueAt(selectedRow, 0);

                    Maquina maquina = new Maquina();
                    maquina.setId(maquinaId);
                    maquina.setStatus("Disponível");

                    MaquinaDAO maquinaDAO = new MaquinaDAO();
                    maquinaDAO.alterarStatus(maquina);

                    MaquinaUI formMenuMaq = new MaquinaUI();
                    formMenuMaq.lblUsuarioId.setText(idUsuario.getText());
                    formMenuMaq.lblUsuarioId.setVisible(false);
                    formMenuMaq.pack();

                    dispose();

                    formMenuMaq.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(BuscarMaquinaUI.this, "Selecione uma máquina para receber.");
                }
            }
        });
    }

    private void onOK() {
        // Adicione seu código aqui, se necessário
        dispose();
    }

    private void onVoltar() {
        // Adicione seu código aqui para a ação do botão Voltar, se necessário
        dispose();
    }

    private void populateTable() {
        List<Maquina> lista = new ArrayList<>();
        MaquinaDAO maquinaDAO = new MaquinaDAO();

        lista = maquinaDAO.retornaListaTudo();

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
        String[] columnNames = {"Id", "Tipo", "Descrição", "Especificação", "Valor Aluguel", "Status"};

        // Cria o modelo da tabela com os dados
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Define o modelo para a tabela
        tblMaquinas.setModel(model);
    }

    public static void main(String[] args) {
        BuscarMaquinaUI dialog = new BuscarMaquinaUI();
        dialog.pack();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    // Crie os componentes da UI manualmente (se não estiver usando um designer de GUI)
    private void createUIComponents() {
        contentPane = new JPanel(new BorderLayout());
        tblMaquinas = new JTable();
        btnVoltar = new JButton("Voltar");
        btnReceber = new JButton("Receber");

        // Cria JScrollPane e adiciona JTable a ele
        JScrollPane scrollPane = new JScrollPane(tblMaquinas);

        // Acessa a barra de rolagem vertical do JScrollPane
        scrollBar1 = scrollPane.getVerticalScrollBar();

        // Adiciona JScrollPane ao painel de conteúdo
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Adiciona os botões em um painel separado
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnVoltar);
        buttonPanel.add(btnReceber);
        buttonPanel.add(idUsuario);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }
}
