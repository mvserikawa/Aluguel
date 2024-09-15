package org.fatec.ui.maquina;

import org.fatec.Maquina;
import org.fatec.dao.MaquinaDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastrarMaquinaUI extends JDialog {
    private JPanel contentPane;
    private JTextField txtId;
    private JTextField txtTipo;
    private JTextField txtDescricao;
    private JTextField txtEspecificacao;
    private JTextField txtValorAluguel;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private JLabel Tipolabel;
    private JLabel Desclabel;
    private JLabel Esplabel;
    private JLabel Vllabel;
    private JTextArea txtEspec;
    public JLabel lblUsuarioId;

    public CadastrarMaquinaUI() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Cadastrar Máquina");
        setSize(400, 300);
        setLocationRelativeTo(null);

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
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
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = txtTipo.getText();
                String descricao = txtDescricao.getText();
                String especificacao = txtEspec.getText();
                double valorAluguel = Double.parseDouble(txtValorAluguel.getText());

                Maquina maquina = new Maquina(tipo, descricao, especificacao, valorAluguel);

                MaquinaDAO maquinaDAO = new MaquinaDAO();
                maquinaDAO.inserir(maquina);

                MaquinaUI formMenuMaq = new MaquinaUI();
                formMenuMaq.lblUsuarioId.setText(lblUsuarioId.getText());
                formMenuMaq.lblUsuarioId.setVisible(false);
                formMenuMaq.pack();

                dispose();

                formMenuMaq.setVisible(true);
            }
        });
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    CadastrarMaquinaUI dialog = new CadastrarMaquinaUI();
                    dialog.pack();  // Pack para ajustar o tamanho da janela automaticamente
                    dialog.setVisible(true);  // Mostra a janela
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
