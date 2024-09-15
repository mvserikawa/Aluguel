package org.fatec.ui.entregador;

import org.fatec.Entregador;
import org.fatec.Usuario;
import org.fatec.dao.EntregadorDAO;
import org.fatec.dao.UsuarioDAO;
import org.fatec.ui.pedido.MenuPedidosEntregadorUI;

import javax.swing.*;
import java.awt.event.*;

public class MenuEntregadorUI extends JDialog {
    private JPanel contentPane;
    public JLabel lblSaud;
    private JButton btnEditCad;
    private JButton btnPedidos;
    public JLabel lblValorId;
    private JButton buttonOK;

    public MenuEntregadorUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        btnEditCad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditCadEntregadorUI formEdit = new EditCadEntregadorUI();

                int id = Integer.parseInt(lblValorId.getText());

                Usuario usuario = new Usuario();
                Entregador entregador = new Entregador();

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.buscaId(id);

                EntregadorDAO entregadorDAO = new EntregadorDAO();
                entregador = entregadorDAO.pesquisar(id);

                if(usuario != null) {
                    String nome = usuario.getNome();
                    String telefone = usuario.getTelefone();
                    String placa = entregador.getPlaca();

                    formEdit.lblSaud.setText("Dados do entregador " + nome);
                    formEdit.txtNome.setText(nome);
                    formEdit.txtTel.setText(telefone);
                    formEdit.txtPlaca.setText(placa);
                    formEdit.lblValorId.setText(lblValorId.getText());
                    formEdit.lblValorId.setVisible(false);
                    formEdit.pack();

                    dispose();
                    formEdit.setVisible(true);
                }

            }
        });
        btnPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPedidosEntregadorUI formMenuPed = new MenuPedidosEntregadorUI();
                formMenuPed.idUsuario.setText(lblValorId.getText());
                formMenuPed.idUsuario.setVisible(false);
                formMenuPed.pack();
                dispose();
                formMenuPed.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        MenuEntregadorUI dialog = new MenuEntregadorUI();
        dialog.pack();
        dialog.setVisible(true);
    }
}
