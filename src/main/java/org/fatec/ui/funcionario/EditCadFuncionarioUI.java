package org.fatec.ui.funcionario;

import org.fatec.Entregador;
import org.fatec.Funcionario;
import org.fatec.Usuario;
import org.fatec.dao.EntregadorDAO;
import org.fatec.dao.FuncionarioDAO;
import org.fatec.dao.UsuarioDAO;
import org.fatec.ui.entregador.MenuEntregadorUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditCadFuncionarioUI extends JDialog {
    private JPanel contentPane;
    public JTextField txtNomeF;
    public JTextField txtTelF;
    public JTextField txtSetor;
    private JButton btnCancelarF;
    private JButton btnSalvarF;
    public JLabel lblValorIdF;
    public JLabel lblOlaF;
    private JLabel IblNomeF;
    private JLabel IblTelF;
    private JLabel IblMatF;
    private JButton buttonOK;

    public EditCadFuncionarioUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        btnCancelarF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(lblValorIdF.getText());
                MenuFuncionarioUI formMenu = new MenuFuncionarioUI();
                Usuario usuario = new Usuario();

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.buscaId(id);

                String nome = usuario.getNome();

                formMenu.lblValorId.setText(lblValorIdF.getText());
                formMenu.lblValorId.setVisible(false);
                formMenu.lblOlaF.setText("Olá "+nome+"! O que gostaria de fazer?");

                formMenu.pack();
                dispose();
                formMenu.setVisible(true);
            }
        });
        btnSalvarF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNomeF.getText();
                String telefone = txtTelF.getText();
                String setor = txtSetor.getText();
                int id = Integer.parseInt(lblValorIdF.getText());

                Usuario usuario = new Usuario();
                Funcionario funcionario = new Funcionario();

                usuario.setTelefone(telefone);
                usuario.setNome(nome);
                funcionario.setSetor(setor);
                funcionario.setUsuarioId(id);

                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                funcionarioDAO.alterar(funcionario, usuario);

                MenuFuncionarioUI formMenu = new MenuFuncionarioUI();
                formMenu.lblValorId.setText(lblValorIdF.getText());
                formMenu.lblValorId.setVisible(false);
                formMenu.lblOlaF.setText("Olá "+nome+"! O que gostaria de fazer?");

                formMenu.pack();
                dispose();
                formMenu.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        EditCadFuncionarioUI dialog = new EditCadFuncionarioUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
