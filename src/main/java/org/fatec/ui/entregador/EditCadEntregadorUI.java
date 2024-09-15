package org.fatec.ui.entregador;

import org.fatec.Cliente;
import org.fatec.Entregador;
import org.fatec.Usuario;
import org.fatec.dao.ClienteDAO;
import org.fatec.dao.EntregadorDAO;
import org.fatec.dao.UsuarioDAO;
import org.fatec.ui.cliente.MenuClienteUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditCadEntregadorUI extends JDialog {
    private JPanel contentPane;
    public JTextField txtNome;
    public JTextField txtTel;
    public JTextField txtPlaca;
    private JButton btnCancelar;
    private JButton btnSalvar;
    public JLabel lblValorId;
    public JLabel lblSaud;
    private JButton buttonOK;

    public EditCadEntregadorUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(lblValorId.getText());
                MenuEntregadorUI formMenu = new MenuEntregadorUI();
                Usuario usuario = new Usuario();

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.buscaId(id);

                String nome = usuario.getNome();

                formMenu.lblValorId.setText(lblValorId.getText());
                formMenu.lblValorId.setVisible(false);
                formMenu.lblSaud.setText("Olá "+nome+"! O que gostaria de fazer?");

                formMenu.pack();
                dispose();
                formMenu.setVisible(true);
            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String telefone = txtTel.getText();
                String placa = txtPlaca.getText();
                int id = Integer.parseInt(lblValorId.getText());

                Usuario usuario = new Usuario();
                Entregador entregador = new Entregador();

                usuario.setTelefone(telefone);
                usuario.setNome(nome);
                entregador.setPlaca(placa);
                entregador.setUsuario_id(id);

                EntregadorDAO entregadorDAO = new EntregadorDAO();
                entregadorDAO.alterar(entregador, usuario);

                MenuEntregadorUI formMenu = new MenuEntregadorUI();
                formMenu.lblValorId.setText(lblValorId.getText());
                formMenu.lblValorId.setVisible(false);
                formMenu.lblSaud.setText("Olá "+nome+"! O que gostaria de fazer?");

                formMenu.pack();
                dispose();
                formMenu.setVisible(true);

            }
        });
    }

    public static void main(String[] args) {
        EditCadEntregadorUI dialog = new EditCadEntregadorUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
