package org.fatec.ui.maquina;

import org.fatec.dao.MaquinaDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExcluirMaquinaUI extends JDialog {
    private JPanel contentPane;
    private JTextField txtId;
    private JButton btnExcluir;
    private JButton btnCancelar;
    private JLabel labelId;
    public JLabel lblUsuarioId;

    public ExcluirMaquinaUI() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Excluir Máquina");
        setSize(300, 150);
        setLocationRelativeTo(null);


        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MaquinaUI formMenuMaq = new MaquinaUI();
                formMenuMaq.lblUsuarioId.setText(lblUsuarioId.getText());
                formMenuMaq.lblUsuarioId.setVisible(false);
                formMenuMaq.pack();

                dispose();

                formMenuMaq.setVisible(true);
            }
        });
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txtId.getText());

                MaquinaDAO maquinaDAO = new MaquinaDAO();
                maquinaDAO.remover(id);

                MaquinaUI formMenuMaq = new MaquinaUI();
                formMenuMaq.lblUsuarioId.setText(lblUsuarioId.getText());
                formMenuMaq.lblUsuarioId.setVisible(false);
                formMenuMaq.pack();

                dispose();

                formMenuMaq.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        ExcluirMaquinaUI dialog = new ExcluirMaquinaUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
