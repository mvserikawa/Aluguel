package org.fatec.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FuncionarioUI extends JFrame implements ActionListener {
    private JPanel contentPane;
    private JLabel labelNome;
    private JTextField textFieldNome;
    private JLabel labelCargo;
    private JTextField textFieldCargo;
    private JLabel labelSalario;
    private JTextField textFieldSalario;
    private JLabel labelDataAdmissao;
    private JTextField textFieldDataAdmissao;
    private JButton btnSalvar;
    private JButton btnExcluir;
    private JButton btnEditar;
    private JButton btnBuscar;

    public FuncionarioUI() {
        setTitle("Funcionário UI");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        btnSalvar.addActionListener(this);
        btnExcluir.addActionListener(this);
        btnEditar.addActionListener(this);
        btnBuscar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalvar) {
            // Código para lidar com o clique no botão Salvar
            JOptionPane.showMessageDialog(this, "Botão Salvar clicado");
        } else if (e.getSource() == btnExcluir) {
            // Código para lidar com o clique no botão Excluir
            JOptionPane.showMessageDialog(this, "Botão Excluir clicado");
        } else if (e.getSource() == btnEditar) {
            // Código para lidar com o clique no botão Editar
            JOptionPane.showMessageDialog(this, "Botão Editar clicado");
        } else if (e.getSource() == btnBuscar) {
            // Código para lidar com o clique no botão Buscar
            JOptionPane.showMessageDialog(this, "Botão Buscar clicado");
        }
    }

    public static void main(String[] args) {
        new FuncionarioUI();
    }
}
