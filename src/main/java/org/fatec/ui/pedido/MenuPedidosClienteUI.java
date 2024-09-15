package org.fatec.ui.pedido;

import org.fatec.Usuario;
import org.fatec.dao.UsuarioDAO;
import org.fatec.ui.cliente.MenuClienteUI;
import org.fatec.ui.entregador.MenuEntregadorUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPedidosClienteUI extends JFrame implements ActionListener {
    private JPanel contentPane;
    public JLabel idUsuario;
    private JButton btnMeusPedidos;
    private JButton btnAlugar;
    private JButton btnVoltar;

    public MenuPedidosClienteUI() {
        setTitle("Menu de Pedidos do Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setVisible(true);
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt(idUsuario.getText());
                MenuClienteUI formMenu = new MenuClienteUI();
                Usuario usuario = new Usuario();

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.buscaId(id);

                String nome = usuario.getNome();

                formMenu.lblvalorId.setText(idUsuario.getText());
                formMenu.lblvalorId.setVisible(false);
                formMenu.lblSaudacao.setText("Olá "+nome+"! O que gostaria de fazer?");

                formMenu.pack();
                dispose();
                formMenu.setVisible(true);

            }
        });
    }

    private void initComponents() {
        contentPane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Label do ID do Usuário
        idUsuario = new JLabel("idUsuario");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPane.add(idUsuario, gbc);

        // Botão Meus Pedidos
        btnMeusPedidos = new JButton("Meus Pedidos");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPane.add(btnMeusPedidos, gbc);

        // Botão Alugar
        btnAlugar = new JButton("Alugar");
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPane.add(btnAlugar, gbc);

        btnVoltar = new JButton("Voltar");
        gbc.gridx = 3;
        gbc.gridy = 1;
        contentPane.add(btnVoltar, gbc);

        btnMeusPedidos.addActionListener(this);
        btnAlugar.addActionListener(this);

        setContentPane(contentPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnMeusPedidos) {
            VisualizarPedidosClientesUI formVerPed = new VisualizarPedidosClientesUI();

            formVerPed.setUsuarioId(idUsuario.getText());
            formVerPed.idUsuario.setVisible(false);
            formVerPed.pack();

            dispose();

            formVerPed.setVisible(true);

        } else if (e.getSource() == btnAlugar) {
            PedidoClienteUI formAlugar = new PedidoClienteUI();

            formAlugar.idUsuario.setText(idUsuario.getText());
            formAlugar.idUsuario.setVisible(false);
            formAlugar.pack();

            dispose();

            formAlugar.setVisible(true);

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPedidosClienteUI());
    }
}
