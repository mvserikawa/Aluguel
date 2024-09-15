package org.fatec.ui.pedido;

import org.fatec.Usuario;
import org.fatec.dao.UsuarioDAO;
import org.fatec.ui.entregador.MenuEntregadorUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPedidosEntregadorUI extends JFrame implements ActionListener {
    private JPanel contentPane;
    public JLabel idUsuario;
    private JButton btnMeusPedidos;
    private JButton btnEntregar;
    private JButton voltarButton;

    public MenuPedidosEntregadorUI() {
        setTitle("Menu de Pedidos do Entregador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setVisible(true);
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idUsuario.getText());
                MenuEntregadorUI formMenu = new MenuEntregadorUI();
                Usuario usuario = new Usuario();

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.buscaId(id);

                String nome = usuario.getNome();

                formMenu.lblValorId.setText(idUsuario.getText());
                formMenu.lblValorId.setVisible(false);
                formMenu.lblSaud.setText("Olá "+nome+"! O que gostaria de fazer?");

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

        // Botão Entregar
        btnEntregar = new JButton("Entregar");
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPane.add(btnEntregar, gbc);

        voltarButton = new JButton("Voltar");
        gbc.gridx = 3;
        gbc.gridy = 1;
        contentPane.add(voltarButton, gbc);

        btnMeusPedidos.addActionListener(this);
        btnEntregar.addActionListener(this);

        setContentPane(contentPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnMeusPedidos) {
            VisualizarPedidosEntregadorUI formVisu = new VisualizarPedidosEntregadorUI();
            formVisu.setUsuarioId(idUsuario.getText());
            formVisu.idUsuario.setVisible(false);

            formVisu.pack();
            dispose();
            formVisu.setVisible(true);
        } else if (e.getSource() == btnEntregar) {
            PedidoEntregadorUI formPed = new PedidoEntregadorUI();
            formPed.idUsuario.setText(idUsuario.getText());
            formPed.idUsuario.setVisible(false);

            formPed.pack();
            dispose();
            formPed.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPedidosEntregadorUI());
    }
}
