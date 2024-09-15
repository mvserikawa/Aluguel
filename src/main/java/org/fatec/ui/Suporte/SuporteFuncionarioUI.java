package org.fatec.ui.Suporte;

import org.fatec.Suporte;
import org.fatec.Usuario;
import org.fatec.dao.SuporteDAO;
import org.fatec.dao.UsuarioDAO;
import org.fatec.ui.cliente.MenuClienteUI;
import org.fatec.ui.funcionario.MenuFuncionarioUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SuporteFuncionarioUI extends JDialog {
    private JTable supportTable;
    private JButton backButton;
    private JButton resolveButton;
    public JLabel userIdLabel;

    public SuporteFuncionarioUI() {
        super((Frame) null, "Gerenciamento de Suporte", true);
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Label para o ID do usuário
        userIdLabel = new JLabel();
        add(userIdLabel, BorderLayout.NORTH);

        // Modelo da tabela
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"ID do Suporte", "ID do Pedido", "Comentário", "Descrição da Máquina", "Nome do Cliente", "Status"},
                0
        );

        supportTable = new JTable(tableModel);
        add(new JScrollPane(supportTable), BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        backButton = new JButton("Voltar");
        resolveButton = new JButton("Resolver");

        buttonPanel.add(backButton);
        buttonPanel.add(resolveButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners para os botões
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(userIdLabel.getText());
                MenuFuncionarioUI formMenu = new MenuFuncionarioUI();
                Usuario usuario = new Usuario();

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.buscaId(id);

                String nome = usuario.getNome();

                formMenu.lblValorId.setText(userIdLabel.getText());
                formMenu.lblValorId.setVisible(false);
                formMenu.lblOlaF.setText("Olá "+nome+"! O que gostaria de fazer?");

                formMenu.pack();
                dispose();
                formMenu.setVisible(true);
            }
        });

        resolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = supportTable.getSelectedRow();
                if (selectedRow != -1) {
                    int suporteId = (int) supportTable.getValueAt(selectedRow, 0); // Obter o ID do suporte
                    SuporteDAO suporteDAO = new SuporteDAO();
                    suporteDAO.alterarStatus(suporteId);

                    int id = Integer.parseInt(userIdLabel.getText());
                    MenuFuncionarioUI formMenu = new MenuFuncionarioUI();
                    Usuario usuario = new Usuario();

                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    usuario = usuarioDAO.buscaId(id);

                    String nome = usuario.getNome();

                    formMenu.lblValorId.setText(userIdLabel.getText());
                    formMenu.lblValorId.setVisible(false);
                    formMenu.lblOlaF.setText("Olá "+nome+"! O que gostaria de fazer?");

                    formMenu.pack();
                    dispose();
                    formMenu.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(SuporteFuncionarioUI.this, "Selecione uma linha para resolver.");
                }
            }
        });

        carregarDados();
    }

    private void carregarDados() {
        SuporteDAO suporteDAO = new SuporteDAO();
        List<Suporte> listaSuporte = suporteDAO.retornaLista();

        DefaultTableModel tableModel = (DefaultTableModel) supportTable.getModel();
        tableModel.setRowCount(0); // Limpar dados existentes

        for (Suporte suporte : listaSuporte) {
            tableModel.addRow(new Object[]{
                    suporte.getId(),
                    suporte.getPedido().getId(),
                    suporte.getComentario(),
                    suporte.getMaquina().getDescricao(),
                    suporte.getUsuario().getNome(),
                    suporte.getStatus()
            });
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SuporteFuncionarioUI dialog = new SuporteFuncionarioUI();
                dialog.setVisible(true);
            }
        });
    }
}
