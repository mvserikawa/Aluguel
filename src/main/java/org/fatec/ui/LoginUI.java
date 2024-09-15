package org.fatec.ui;

import org.fatec.Cliente;
import org.fatec.Usuario;
import org.fatec.dao.ClienteDAO;
import org.fatec.dao.UsuarioDAO;
import org.fatec.ui.cliente.MenuClienteUI;
import org.fatec.ui.entregador.MenuEntregadorUI;
import org.fatec.ui.funcionario.MenuFuncionarioUI;

import javax.swing.*;
import java.awt.event.*;

public class LoginUI extends JDialog {
    private JPanel contentPane;
    private JButton btnCadastro;
    private JButton btnLogin;
    private JTextField txtEmail;
    private JLabel lblMsg;
    private JPasswordField pwdSenha;
    private JButton buttonOK;

    public LoginUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        btnCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioUI formCadastro = new UsuarioUI();
                formCadastro.setSize(500, 400);
                formCadastro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                formCadastro.setVisible(true);

                dispose();

            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String email = txtEmail.getText();
                String senha = pwdSenha.getText();

                Usuario usuario = new Usuario();

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.login(email);



                if(usuario != null){
                    String emailP = usuario.getEmail();
                    String senhaP = usuario.getSenha();

                    if(emailP.equals(email) && senhaP.equals(senha)){

                        if(usuario.getCategoria().equals("Cliente")){
                            MenuClienteUI formMenuCliente = new MenuClienteUI();

                            formMenuCliente.lblSaudacao.setText("Olá "+usuario.getNome()+"! O que gostaria de fazer?");
                            formMenuCliente.lblvalorId.setText(""+usuario.getId());
                            formMenuCliente.lblvalorId.setVisible(false);
                            formMenuCliente.pack();

                            dispose();

                            formMenuCliente.setVisible(true);
                        }

                        if(usuario.getCategoria().equals("Entregador")){
                            MenuEntregadorUI formMenuEntregador = new MenuEntregadorUI();

                            formMenuEntregador.lblSaud.setText("Olá "+usuario.getNome()+"! O que gostaria de fazer?");
                            formMenuEntregador.lblValorId.setText(""+usuario.getId());
                            formMenuEntregador.lblValorId.setVisible(false);
                            formMenuEntregador.pack();

                            dispose();

                            formMenuEntregador.setVisible(true);
                        }

                        if(usuario.getCategoria().equals("Funcionário")){
                            MenuFuncionarioUI formMenuFunc = new MenuFuncionarioUI();

                            formMenuFunc.lblOlaF.setText("Olá "+usuario.getNome()+"! O que gostaria de fazer?");
                            formMenuFunc.lblValorId.setText(""+usuario.getId());
                            formMenuFunc.lblValorId.setVisible(false);
                            formMenuFunc.pack();

                            dispose();

                            formMenuFunc.setVisible(true);
                        }

                    }

                    lblMsg.setText("E-mail ou senha incorretos.");
                }else{
                    lblMsg.setText("E-mail ou senha incorretos.");
                }
            }
        });
    }

    public static void main(String[] args) {
        LoginUI dialog = new LoginUI();
        dialog.pack();
        dialog.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
