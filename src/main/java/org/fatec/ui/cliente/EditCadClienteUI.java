package org.fatec.ui.cliente;

import org.fatec.Cliente;
import org.fatec.Usuario;
import org.fatec.dao.ClienteDAO;
import org.fatec.dao.UsuarioDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditCadClienteUI extends JDialog {
    private JPanel contentPane;
    public JLabel lblSaudacao;
    public JTextField txtNome;
    public JTextField txtTelefone;
    public JTextField txtEndereco;
    private JButton btnCancelar;
    private JButton btnSalvar;
    private JLabel lblNome;
    private JLabel lblTel;
    private JLabel lblEnd;
    public JLabel lblValorId;
    private JButton buttonOK;

    public EditCadClienteUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(lblValorId.getText());
                MenuClienteUI formMenu = new MenuClienteUI();
                Usuario usuario = new Usuario();

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.buscaId(id);

                String nome = usuario.getNome();

                formMenu.lblvalorId.setText(lblValorId.getText());
                formMenu.lblvalorId.setVisible(false);
                formMenu.lblSaudacao.setText("Olá "+nome+"! O que gostaria de fazer?");

                formMenu.pack();
                dispose();
                formMenu.setVisible(true);
            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String telefone = txtTelefone.getText();
                String endereco = txtEndereco.getText();
                int id = Integer.parseInt(lblValorId.getText());

                Usuario usuario = new Usuario();
                Cliente cliente = new Cliente();

                usuario.setTelefone(telefone);
                usuario.setNome(nome);
                cliente.setEndereco(endereco);
                cliente.setUsuario_id(id);

                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.alterar(cliente, usuario);

                MenuClienteUI formMenu = new MenuClienteUI();
                formMenu.lblvalorId.setText(lblValorId.getText());
                formMenu.lblvalorId.setVisible(false);
                formMenu.lblSaudacao.setText("Dados do cliente "+nome);

                formMenu.pack();
                dispose();
                formMenu.setVisible(true);

            }
        });
    }

    public static void main(String[] args) {
        EditCadClienteUI dialog = new EditCadClienteUI();
        dialog.pack();
        dialog.setVisible(true);
    }
}
