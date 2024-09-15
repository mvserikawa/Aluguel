package org.fatec.ui.funcionario;

import org.fatec.Funcionario;
import org.fatec.Usuario;
import org.fatec.dao.FuncionarioDAO;
import org.fatec.dao.UsuarioDAO;
import org.fatec.ui.Suporte.SuporteFuncionarioUI;
import org.fatec.ui.maquina.MaquinaUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFuncionarioUI extends JDialog {
    private JPanel contentPane;
    public JLabel lblOlaF;
    private JButton btnEditCadF;
    private JButton btnSuporteF;
    private JButton btnMaquinaF;
    public JLabel lblValorId;
    private JButton buttonOK;

    public MenuFuncionarioUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        btnEditCadF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditCadFuncionarioUI formEdit = new EditCadFuncionarioUI();

                int id = Integer.parseInt(lblValorId.getText());

                Usuario usuario = new Usuario();
                Funcionario funcionario = new Funcionario();

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.buscaId(id);

                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                funcionario = funcionarioDAO.pesquisar(id);

                if(usuario != null) {
                    String nome = usuario.getNome();
                    String telefone = usuario.getTelefone();
                    String setor = funcionario.getSetor();

                    formEdit.lblOlaF.setText("Dados do funcionário "+nome);
                    formEdit.txtNomeF.setText(nome);
                    formEdit.txtTelF.setText(telefone);
                    formEdit.txtSetor.setText(setor);
                    formEdit.lblValorIdF.setText(lblValorId.getText());
                    formEdit.lblValorIdF.setVisible(false);
                    formEdit.pack();

                    dispose();
                    formEdit.setVisible(true);
                }

            }
        });
        btnMaquinaF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MaquinaUI formMaquina = new MaquinaUI();

                formMaquina.lblUsuarioId.setText(lblValorId.getText());
                formMaquina.lblUsuarioId.setVisible(false);
                formMaquina.pack();

                dispose();

                formMaquina.setVisible(true);

            }
        });
        btnSuporteF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SuporteFuncionarioUI formSuporte = new SuporteFuncionarioUI();
                formSuporte.userIdLabel.setText(lblValorId.getText());
                formSuporte.userIdLabel.setVisible(false);
                formSuporte.pack();

                dispose();

                formSuporte.setVisible(true);

            }
        });
    }

    public static void main(String[] args) {
        MenuFuncionarioUI dialog = new MenuFuncionarioUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}

