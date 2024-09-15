package org.fatec.ui.avaliacao;

import org.fatec.Avaliacao;
import org.fatec.Pedido;
import org.fatec.Usuario;
import org.fatec.dao.AvaliacaoDAO;
import org.fatec.dao.UsuarioDAO;
import org.fatec.ui.cliente.MenuClienteUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AvaliacaoUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtPedidoId;
    private JComboBox<String> comboNota;
    private JTextArea txtFeedback;
    private JLabel labelPedidoId;
    private JLabel labelNota;
    private JLabel labelFeedback;
    public JLabel labId;

    public AvaliacaoUI() {
        setTitle("Avaliação");
        setSize(600, 400); // Tamanho inicial maior
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        contentPane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        labelPedidoId = new JLabel("ID do Pedido:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(labelPedidoId, gbc);

        txtPedidoId = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtPedidoId, gbc);

        labelNota = new JLabel("Nota:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(labelNota, gbc);

        comboNota = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        gbc.gridx = 1;
        contentPane.add(comboNota, gbc);

        labelFeedback = new JLabel("Feedback:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        contentPane.add(labelFeedback, gbc);

        txtFeedback = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(txtFeedback);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        contentPane.add(scrollPane, gbc);

        buttonOK = new JButton("OK");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        contentPane.add(buttonOK, gbc);

        buttonCancel = new JButton("Cancelar");
        gbc.gridx = 1;
        contentPane.add(buttonCancel, gbc);

        setContentPane(contentPane);
        setModal(true);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        int pedidoId = Integer.parseInt(txtPedidoId.getText());
        int nota = Integer.parseInt((String) comboNota.getSelectedItem());
        String comentario = txtFeedback.getText();

        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);

        Avaliacao avaliacao = new Avaliacao(nota, comentario, pedido);

        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
        avaliacaoDAO.inserir(avaliacao);

        int id = Integer.parseInt(labId.getText());
        MenuClienteUI formMenu = new MenuClienteUI();
        Usuario usuario = new Usuario();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuario = usuarioDAO.buscaId(id);

        String nome = usuario.getNome();

        formMenu.lblvalorId.setText(labId.getText());
        formMenu.lblvalorId.setVisible(false);
        formMenu.lblSaudacao.setText("Olá " + nome + "! O que gostaria de fazer?");

        formMenu.pack();
        dispose();
        formMenu.setVisible(true);
    }

    private void onCancel() {
        int id = Integer.parseInt(labId.getText());
        MenuClienteUI formMenu = new MenuClienteUI();
        Usuario usuario = new Usuario();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuario = usuarioDAO.buscaId(id);

        String nome = usuario.getNome();

        formMenu.lblvalorId.setText(labId.getText());
        formMenu.lblvalorId.setVisible(false);
        formMenu.lblSaudacao.setText("Olá " + nome + "! O que gostaria de fazer?");

        formMenu.pack();
        dispose();
        formMenu.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AvaliacaoUI dialog = new AvaliacaoUI();
                dialog.setVisible(true);
            }
        });
    }
}
