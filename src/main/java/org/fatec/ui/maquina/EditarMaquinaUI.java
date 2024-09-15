package org.fatec.ui.maquina;

import org.fatec.Maquina;
import org.fatec.dao.MaquinaDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarMaquinaUI extends JDialog {
    private JPanel contentPane;
    private JTextField txtId;
    private JTextField txtTipo;
    private JTextField txtDescricao;
    private JTextField txtEspecificacao;
    private JTextField txtValorAluguel;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private JLabel labelId;
    private JLabel labelTipo;
    private JLabel labelDescricao;
    private JLabel labelEspecificacao;
    private JLabel labelValorAluguel;
    private JButton btnBuscar;
    private JTextArea txtEspec;
    public JLabel lblUsuarioId;

    private Maquina maquina; // Referência para a máquina sendo editada

    public EditarMaquinaUI() {
        this.maquina = maquina;

        setContentPane(contentPane);
        setModal(true);
        setTitle("Editar Máquina");
        setSize(400, 300);
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
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txtId.getText());

                MaquinaDAO maquinaDAO = new MaquinaDAO();
                Maquina maquina = maquinaDAO.pesquisar(id);

                txtTipo.setText(maquina.getTipo());
                txtTipo.setEnabled(true);
                txtDescricao.setText(maquina.getDescricao());
                txtDescricao.setEnabled(true);
                txtEspec.setText(maquina.getEspecificacao());
                txtEspec.setEnabled(true);
                txtValorAluguel.setText(""+maquina.getValorAluguel());
                txtValorAluguel.setEnabled(true);

                btnSalvar.setEnabled(true);
                txtId.setEnabled(false);
            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txtId.getText());
                String tipo = txtTipo.getText();
                String desc = txtDescricao.getText();
                String espec = txtEspec.getText();
                Double valorAluguel = Double.parseDouble(txtValorAluguel.getText());

                Maquina maquina = new Maquina(id, tipo, desc, espec, valorAluguel);

                MaquinaDAO maquinaDAO = new MaquinaDAO();
                maquinaDAO.alterar(maquina);

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
        EditarMaquinaUI dialog = new EditarMaquinaUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
