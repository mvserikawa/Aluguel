package org.fatec.ui.cliente;

import org.fatec.Cliente;
import org.fatec.Usuario;
import org.fatec.dao.ClienteDAO;
import org.fatec.dao.UsuarioDAO;
import org.fatec.ui.Suporte.SuporteClienteUI;
import org.fatec.ui.avaliacao.AvaliacaoUI;
import org.fatec.ui.pedido.MenuPedidosClienteUI;

import javax.swing.*;
import java.awt.event.*;

public class MenuClienteUI extends JDialog {
    private JPanel contentPane;
    private JButton btnEditarCadastro;
    private JButton btnPedido;
    private JButton btnAvaliar;
    private JButton btnSuporte;
    public JLabel lblSaudacao;
    public JLabel lblvalorId;
    private JButton buttonOK;
    private JButton buttonCancel;


    public MenuClienteUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);





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
        btnEditarCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditCadClienteUI formEdit = new EditCadClienteUI();

                int id = Integer.parseInt(lblvalorId.getText());

                Usuario usuario = new Usuario();
                Cliente cliente = new Cliente();

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.buscaId(id);

                ClienteDAO clienteDAO = new ClienteDAO();
                cliente = clienteDAO.pesquisar(id);

                if(usuario != null){
                    String nome = usuario.getNome();
                    String telefone = usuario.getTelefone();
                    String endereco = cliente.getEndereco();

                    formEdit.lblSaudacao.setText("Dados do cliente "+nome);
                    formEdit.txtNome.setText(nome);
                    formEdit.txtTelefone.setText(telefone);
                    formEdit.txtEndereco.setText(endereco);
                    formEdit.lblValorId.setText(lblvalorId.getText());
                    formEdit.lblValorId.setVisible(false);
                    formEdit.pack();

                    dispose();
                    formEdit.setVisible(true);

                }


            }
        });
        btnPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPedidosClienteUI formMenuPed = new MenuPedidosClienteUI();

                formMenuPed.idUsuario.setText(lblvalorId.getText());
                formMenuPed.idUsuario.setVisible(false);
                formMenuPed.pack();
                dispose();
                formMenuPed.setVisible(true);

            }
        });
        btnAvaliar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AvaliacaoUI formAval = new AvaliacaoUI();

                formAval.labId.setText(lblvalorId.getText());
                formAval.labId.setVisible(false);
                formAval.pack();
                dispose();
                formAval.setVisible(true);
            }
        });
        btnSuporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SuporteClienteUI formSuporte = new SuporteClienteUI();
                formSuporte.userIdLabel.setText(lblvalorId.getText());
                formSuporte.userIdLabel.setVisible(false);
                formSuporte.pack();
                dispose();
                formSuporte.setVisible(true);
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        MenuClienteUI dialog = new MenuClienteUI();
        dialog.pack();
        dialog.setVisible(true);
    }
}
