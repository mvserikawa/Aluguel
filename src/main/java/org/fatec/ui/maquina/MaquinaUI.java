package org.fatec.ui.maquina;

import org.fatec.Usuario;
import org.fatec.dao.UsuarioDAO;
import org.fatec.ui.funcionario.MenuFuncionarioUI;

import javax.swing.*;
import java.awt.event.*;

public class MaquinaUI extends JDialog {
    private JPanel contentPane;
    private JButton btnExcM;
    private JButton btnCadM;
    private JButton btnEditM;
    private JButton btnBuscM;
    private JButton btnMeuPerfil;
    public JLabel lblUsuarioId;

    public MaquinaUI() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Gestão de Máquinas");
        setSize(400, 300);
        setLocationRelativeTo(null);

        btnExcM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ação para excluir máquina
                System.out.println("Excluir Máquina");
            }
        });

        btnCadM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ação para cadastrar máquina
                System.out.println("Cadastrar Máquina");
            }
        });

        btnEditM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ação para editar máquina
                System.out.println("Editar Máquina");
            }
        });

        btnBuscM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BuscarMaquinaUI formBuscMaq = new BuscarMaquinaUI();
                formBuscMaq.idUsuario.setText(lblUsuarioId.getText());
                formBuscMaq.idUsuario.setVisible(false);
                formBuscMaq.pack();
                dispose();
                formBuscMaq.setVisible(true);
            }
        });

        btnMeuPerfil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onMeuPerfil();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        btnMeuPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(lblUsuarioId.getText());
                MenuFuncionarioUI formMenu = new MenuFuncionarioUI();
                Usuario usuario = new Usuario();

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.buscaId(id);

                String nome = usuario.getNome();

                formMenu.lblValorId.setText(lblUsuarioId.getText());
                formMenu.lblValorId.setVisible(false);
                formMenu.lblOlaF.setText("Olá "+nome+"! O que gostaria de fazer?");

                formMenu.pack();
                dispose();
                formMenu.setVisible(true);
            }
        });
        btnCadM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastrarMaquinaUI formCadMaq = new CadastrarMaquinaUI();
                formCadMaq.lblUsuarioId.setText(lblUsuarioId.getText());
                formCadMaq.lblUsuarioId.setVisible(false);
                formCadMaq.pack();
                dispose();
                formCadMaq.setVisible(true);
            }
        });
        btnExcM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExcluirMaquinaUI formExcMaq = new ExcluirMaquinaUI();
                formExcMaq.lblUsuarioId.setText(lblUsuarioId.getText());
                formExcMaq.lblUsuarioId.setVisible(false);
                formExcMaq.pack();
                dispose();
                formExcMaq.setVisible(true);
            }
        });
        btnEditM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditarMaquinaUI formEdMaq = new EditarMaquinaUI();
                formEdMaq.lblUsuarioId.setText(lblUsuarioId.getText());
                formEdMaq.lblUsuarioId.setVisible(false);
                formEdMaq.pack();
                dispose();
                formEdMaq.setVisible(true);
            }
        });
    }

    private void onMeuPerfil() {
        // Implement the action to go back to the employee's profile
        System.out.println("Redirecionando para o perfil do funcionário...");
        // Add logic to navigate to the employee's profile
    }

    private void onCancel() {
        // Add your code here if you want to do anything before closing
        dispose();
    }

    public static void main(String[] args) {
        MaquinaUI dialog = new MaquinaUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
