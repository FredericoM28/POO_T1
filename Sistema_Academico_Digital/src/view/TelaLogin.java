package view;

import controller.SistemaController;
import model.Aluno;
import model.Professor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaLogin extends JFrame {

    private SistemaController controller;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private String perfilForcado; // "gestao", "professor", "aluno"

    public TelaLogin(SistemaController controller) {
        this(controller, null);
    }

    public TelaLogin(SistemaController controller, String perfilForcado) {
        this.controller = controller;
        this.perfilForcado = perfilForcado;

        // Configuração da janela
        setTitle("Sistema Universitário Digital - Login");
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(248, 249, 250));
        
        // Painel principal com padding
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 20));
        painelPrincipal.setBorder(new EmptyBorder(40, 50, 30, 50));
        painelPrincipal.setBackground(new Color(248, 249, 250));
        
        // Título
        JLabel titulo = new JLabel("Acesso ao Sistema", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(33, 37, 41));
        titulo.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Painel de formulário
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(new Color(248, 249, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 10, 0);
        
        // Campo email
        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelFormulario.add(lblEmail, gbc);
        
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmail.setPreferredSize(new Dimension(300, 38));
        gbc.gridy = 1;
        gbc.ipady = 10;
        painelFormulario.add(txtEmail, gbc);
        
        // Campo senha
        JLabel lblSenha = new JLabel("Senha/Código:");
        lblSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 2;
        gbc.ipady = 0;
        painelFormulario.add(lblSenha, gbc);
        
        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenha.setPreferredSize(new Dimension(300, 38));
        gbc.gridy = 3;
        gbc.ipady = 10;
        painelFormulario.add(txtSenha, gbc);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(1, 3, 15, 0));
        painelBotoes.setBackground(new Color(248, 249, 250));
        painelBotoes.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        JButton btnAluno = criarBotao("Aluno", new Color(220, 53, 69));
        JButton btnProfessor = criarBotao("Professor", new Color(40, 167, 69));
        JButton btnGestao = criarBotao("Gestão", new Color(23, 162, 184));
        
        btnAluno.addActionListener(e -> autenticar("aluno"));
        btnProfessor.addActionListener(e -> autenticar("professor"));
        btnGestao.addActionListener(e -> autenticar("gestao"));
        
        painelBotoes.add(btnAluno);
        painelBotoes.add(btnProfessor);
        painelBotoes.add(btnGestao);
        
        // Se perfil forçado, desabilitar os outros botões
        if (perfilForcado != null) {
            btnAluno.setEnabled(perfilForcado.equals("aluno"));
            btnProfessor.setEnabled(perfilForcado.equals("professor"));
            btnGestao.setEnabled(perfilForcado.equals("gestao"));
        }
        
        // Adicionar componentes ao painel principal
        painelPrincipal.add(titulo, BorderLayout.NORTH);
        painelPrincipal.add(painelFormulario, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botao.setForeground(Color.WHITE);
        botao.setBackground(cor);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(0, 45));
        
        // Efeito hover
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor);
            }
        });
        
        return botao;
    }

    private void autenticar(String tipo) {
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());

        switch(tipo){
            case "aluno":
                Aluno aluno = controller.loginAluno(email,senha);
                if(aluno != null){
                    JFrame tela = new JFrame("Painel Estudante"); 
                    tela.setSize(950,600);
                    tela.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    tela.setLocationRelativeTo(null);
                    tela.add(new PainelEstudante(controller,aluno));
                    tela.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"Aluno não encontrado!", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
                }
                break;

            case "professor":
                Professor prof = controller.loginProfessor(email, senha);
                if(prof != null){
                    JFrame tela = new JFrame("Painel Professor"); 
                    tela.setSize(950,600);
                    tela.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    tela.setLocationRelativeTo(null);
                    tela.add(new PainelProfessor(controller,prof));
                    tela.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"Professor não encontrado!", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
                }
                break;

            case "gestao":
                if(controller.gestaoLogin(email,senha)){
                    new PainelGestaoAcademica(controller);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"Credenciais inválidas!", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
    }
}