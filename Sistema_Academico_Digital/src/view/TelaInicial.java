package view;

import controller.SistemaController;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JFrame {

    private SistemaController controller;

    public TelaInicial(SistemaController controller) {
        this.controller = controller;

        try {
          
        } catch (Exception e) { e.printStackTrace(); }

        setTitle("Universidade - Sistema Acadêmico");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelCentral = new JPanel(new GridLayout(1, 3, 30, 30));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(100,50,100,50));
        painelCentral.setBackground(new Color(245, 247, 250));

        JButton btnGestao = criarBotao("Gestão Acadêmica", new Color(52,152,219));
        btnGestao.addActionListener(e -> new TelaLogin(controller,"gestao").setVisible(true));

        JButton btnProfessor = criarBotao("Professor", new Color(46,204,113));
        btnProfessor.addActionListener(e -> new TelaLogin(controller,"professor").setVisible(true));

        JButton btnAluno = criarBotao("Aluno", new Color(231,76,60));
        btnAluno.addActionListener(e -> new TelaLogin(controller,"aluno").setVisible(true));

        painelCentral.add(btnGestao);
        painelCentral.add(btnProfessor);
        painelCentral.add(btnAluno);

        add(painelCentral, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 22));
        botao.setForeground(Color.WHITE);
        botao.setBackground(cor);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { botao.setBackground(cor.darker()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { botao.setBackground(cor); }
        });
        return botao;
    }
}
