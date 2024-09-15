package org.fatec.ui;

import org.fatec.Cliente;
import org.fatec.Entregador;
import org.fatec.Funcionario;
import org.fatec.Usuario;
import org.fatec.dao.ClienteDAO;
import org.fatec.dao.EntregadorDAO;
import org.fatec.dao.FuncionarioDAO;
import org.fatec.dao.UsuarioDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioUI extends JFrame {
    private JPanel contentPane;
    private JLabel lblNome;
    private JTextField textFieldNome;
    private JLabel lblEmail;
    private JTextField textFieldEmail;
    private JLabel lblSenha;
    private JPasswordField passFieldSenha;
    private JCheckBox chkVerSenha;
    private JLabel lblTelefone;
    private JTextField textFieldTelefone;
    private JLabel lblCategoria;
    private JComboBox<String> cbCategoria;
    private JButton btnSalvar;
    private JButton btnLogin;

    public UsuarioUI() {
        setTitle("Usuário UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 20, 80, 25);
        contentPane.add(lblNome);

        textFieldNome = new JTextField();
        textFieldNome.setBounds(120, 20, 200, 25);
        contentPane.add(textFieldNome);

        lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 60, 80, 25);
        contentPane.add(lblEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setBounds(120, 60, 200, 25);
        contentPane.add(textFieldEmail);

        lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(20, 100, 80, 25);
        contentPane.add(lblSenha);

        passFieldSenha = new JPasswordField();
        passFieldSenha.setBounds(120, 100, 200, 25);
        contentPane.add(passFieldSenha);

        chkVerSenha = new JCheckBox("Ver senha");
        chkVerSenha.setBounds(340, 100, 100, 25);
        contentPane.add(chkVerSenha);

        chkVerSenha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkVerSenha.isSelected()) {
                    passFieldSenha.setEchoChar((char) 0);
                } else {
                    passFieldSenha.setEchoChar('*');
                }
            }
        });

        lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(20, 140, 80, 25);
        contentPane.add(lblTelefone);

        textFieldTelefone = new JTextField();
        textFieldTelefone.setBounds(120, 140, 200, 25);
        contentPane.add(textFieldTelefone);

        lblCategoria = new JLabel("Você é:");
        lblCategoria.setBounds(20, 180, 100, 25);
        contentPane.add(lblCategoria);

        String[] categorias = {"Cliente", "Funcionário", "Entregador"};

        cbCategoria = new JComboBox<>(categorias);
        cbCategoria.setBounds(120, 180, 150, 25);
        contentPane.add(cbCategoria);

        btnSalvar = new JButton("Cadastrar");
        btnSalvar.setBounds(20, 220, 100, 25);
        contentPane.add(btnSalvar);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(140, 220, 100, 25);
        contentPane.add(btnLogin);

        // Adicionando ação ao botão salvar
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para salvar o usuário
                String nome = textFieldNome.getText();
                String email = textFieldEmail.getText();
                String telefone = textFieldTelefone.getText();
                String senha = passFieldSenha.getText();
                String categoria = (String) cbCategoria.getSelectedItem();

                textFieldNome.setText("");
                textFieldEmail.setText("");
                textFieldTelefone.setText("");
                passFieldSenha.setText("");

                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setNome(nome);
                usuario.setSenha(senha);
                usuario.setTelefone(telefone);
                usuario.setCategoria(categoria);

                UsuarioDAO bd = new UsuarioDAO();
                int usuarioId = bd.inserir(usuario);

                if ("Cliente".equals(categoria)) {
                    Cliente cliente = new Cliente();
                    cliente.setUsuario_id(usuarioId);
                    cliente.setEndereco("");

                    ClienteDAO clienteDAO = new ClienteDAO();
                    clienteDAO.inserir(cliente);
                }

                if ("Entregador".equals(categoria)) {
                    Entregador entregador = new Entregador();
                    entregador.setUsuario_id(usuarioId);
                    entregador.setPlaca("");

                    EntregadorDAO entregadorDAO = new EntregadorDAO();
                    entregadorDAO.inserir(entregador);
                }

                if ("Funcionário".equals(categoria)) {
                    Funcionario funcionario = new Funcionario();
                    funcionario.setUsuarioId(usuarioId);
                    funcionario.setSetor("");

                    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                    funcionarioDAO.inserir(funcionario);
                }

                LoginUI formLogin = new LoginUI();
                formLogin.pack();

                dispose();

                formLogin.setVisible(true);

                if (usuarioId > 0) {
                    System.out.println("Usuário " + nome + " adicionado com sucesso");
                } else {
                    System.out.println("Erro durante cadastro de usuário");
                }
            }
        });

        // Adicionando ação ao botão login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginUI formLogin = new LoginUI();
                formLogin.pack();

                dispose();

                formLogin.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        UsuarioUI frame = new UsuarioUI();
        frame.setVisible(true);
    }
}
