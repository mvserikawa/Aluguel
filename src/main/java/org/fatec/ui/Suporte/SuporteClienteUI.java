package org.fatec.ui.Suporte;

import org.fatec.*;
import org.fatec.dao.ClienteDAO;
import org.fatec.dao.PedidoDAO;
import org.fatec.dao.SuporteDAO;
import org.fatec.dao.UsuarioDAO;
import org.fatec.ui.cliente.MenuClienteUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuporteClienteUI extends JDialog {
    private JTextField orderIdField;
    private JTextArea commentArea;
    public JLabel userIdLabel;
    private JButton submitButton;
    private JButton backButton;

    public SuporteClienteUI() {
        // Usar o construtor da superclasse sem argumentos
        super((Frame) null, "Solicitação de Suporte", true);
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(null); // Centraliza a janela

        // Painel de entrada de dados
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        // Label e campo para ID do pedido
        inputPanel.add(new JLabel("ID do Pedido:"));
        orderIdField = new JTextField();
        inputPanel.add(orderIdField);

        // Label e área de texto para comentário
        inputPanel.add(new JLabel("Comentário:"));
        commentArea = new JTextArea(5, 20);
        inputPanel.add(new JScrollPane(commentArea));

        // Label para ID do usuário
        userIdLabel = new JLabel();
        inputPanel.add(userIdLabel);

        add(inputPanel, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        submitButton = new JButton("Enviar");
        backButton = new JButton("Voltar");

        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners para os botões
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para enviar a solicitação
                int pedido_id = Integer.parseInt(orderIdField.getText());
                int usuario_id = Integer.parseInt(userIdLabel.getText());
                String comentario = commentArea.getText();

                Cliente cliente = new Cliente();
                ClienteDAO clienteDAO = new ClienteDAO();
                cliente = clienteDAO.pesquisar(usuario_id);
                Pedido pedido = new PedidoDAO().pesquisar(pedido_id);
                Maquina maquina = pedido.getMaquina();

                Suporte suporte = new Suporte();
                suporte.setCliente(cliente);
                suporte.setMaquina(maquina);
                suporte.setPedido(pedido);
                suporte.setComentario(comentario);
                suporte.setStatus("Não resolvido");

                SuporteDAO suporteDAO = new SuporteDAO();
                suporteDAO.inserir(suporte);

                int id = Integer.parseInt(userIdLabel.getText());
                MenuClienteUI formMenu = new MenuClienteUI();
                Usuario usuario = new Usuario();

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.buscaId(id);

                String nome = usuario.getNome();

                formMenu.lblvalorId.setText(userIdLabel.getText());
                formMenu.lblvalorId.setVisible(false);
                formMenu.lblSaudacao.setText("Olá "+nome+"! O que gostaria de fazer?");

                formMenu.pack();
                dispose();
                formMenu.setVisible(true);





            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(userIdLabel.getText());
                MenuClienteUI formMenu = new MenuClienteUI();
                Usuario usuario = new Usuario();

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.buscaId(id);

                String nome = usuario.getNome();

                formMenu.lblvalorId.setText(userIdLabel.getText());
                formMenu.lblvalorId.setVisible(false);
                formMenu.lblSaudacao.setText("Olá "+nome+"! O que gostaria de fazer?");

                formMenu.pack();
                dispose();
                formMenu.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SuporteClienteUI dialog = new SuporteClienteUI();
                dialog.setVisible(true);
            }
        });
    }
}
