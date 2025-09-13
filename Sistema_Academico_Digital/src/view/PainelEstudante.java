package view;

import controller.SistemaController;
import model.Aluno;
import model.Fatura;
import model.Nota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PainelEstudante extends JPanel {

    private JLabel lblFoto;
    private JButton btnAnexarFoto, btnEditarPerfil, btnGerarFatura;
    private JTextField txtNome, txtIdade, txtCurso, txtMatricula, txtEmail;
    private JTable tabelaMensalidades, tabelaNotas;

    public PainelEstudante(SistemaController controller, Aluno aluno) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));

        // Painel superior - Perfil
        JPanel painelPerfil = new JPanel(new BorderLayout(10, 10));
        painelPerfil.setBackground(Color.WHITE);
        painelPerfil.setBorder(BorderFactory.createTitledBorder("Perfil do Estudante"));

        // 📸 Foto
        lblFoto = new JLabel("Foto", SwingConstants.CENTER);
        lblFoto.setPreferredSize(new Dimension(120, 150));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btnAnexarFoto = new JButton("Anexar Foto");

        JPanel painelFoto = new JPanel(new BorderLayout(5, 5));
        painelFoto.add(lblFoto, BorderLayout.CENTER);
        painelFoto.add(btnAnexarFoto, BorderLayout.SOUTH);

        // 🧍 Dados básicos
        JPanel painelDados = new JPanel(new GridLayout(5, 2, 10, 10));
        painelDados.add(new JLabel("Nome:"));
        txtNome = criarCampo();
        txtNome.setText(aluno.getNome());
        painelDados.add(txtNome);

        painelDados.add(new JLabel("Idade:"));
        txtIdade = criarCampo();
        txtIdade.setText(String.valueOf(aluno.getIdade()));
        painelDados.add(txtIdade);

        painelDados.add(new JLabel("Curso:"));
        txtCurso = criarCampo();
        txtCurso.setText(aluno.getCurso() != null ? aluno.getCurso().getNome() : "");
        painelDados.add(txtCurso);

        painelDados.add(new JLabel("Matrícula:"));
        txtMatricula = criarCampo();
        txtMatricula.setText(aluno.getMatricula());
        painelDados.add(txtMatricula);

        painelDados.add(new JLabel("Email:"));
        txtEmail = criarCampo();
        txtEmail.setText(aluno.getEmail());
        painelDados.add(txtEmail);

        // ✏️ Botão editar perfil
        btnEditarPerfil = new JButton("Editar Perfil");
        btnEditarPerfil.setBackground(new Color(52, 152, 219));
        btnEditarPerfil.setForeground(Color.WHITE);
        btnEditarPerfil.setFocusPainted(false);

        JPanel painelDadosComBotao = new JPanel(new BorderLayout());
        painelDadosComBotao.add(painelDados, BorderLayout.CENTER);
        painelDadosComBotao.add(btnEditarPerfil, BorderLayout.SOUTH);

        painelPerfil.add(painelFoto, BorderLayout.WEST);
        painelPerfil.add(painelDadosComBotao, BorderLayout.CENTER);

        // 📑 Abas
        JTabbedPane abas = new JTabbedPane();

        // 🧾 Aba Mensalidades
        JPanel painelMensalidades = new JPanel(new BorderLayout(5, 5));
        DefaultTableModel modeloMensalidades = new DefaultTableModel(
                new String[]{"Mês", "Valor", "Estado"}, 0
        );
        tabelaMensalidades = new JTable(modeloMensalidades);

        for (Fatura f : controller.getMensalidades(aluno)) {
            modeloMensalidades.addRow(new Object[]{f.getMes(), f.getValor(), f.getEstado()});
        }

        painelMensalidades.add(new JScrollPane(tabelaMensalidades), BorderLayout.CENTER);

        btnGerarFatura = new JButton("Gerar Fatura");
        btnGerarFatura.setBackground(new Color(241, 196, 15));
        btnGerarFatura.setForeground(Color.BLACK);

        painelMensalidades.add(btnGerarFatura, BorderLayout.SOUTH);
        abas.addTab("Mensalidades", painelMensalidades);

        // 📊 Aba Disciplinas & Notas
        JPanel painelNotas = new JPanel(new BorderLayout(5, 5));
        DefaultTableModel modeloNotas = new DefaultTableModel(
                new String[]{"Disciplina", "Nota"}, 0
        );
        tabelaNotas = new JTable(modeloNotas);

        for (Nota n : controller.getNotas(aluno)) {
            modeloNotas.addRow(new Object[]{n.getDisciplina(), n.getValor()});
        }

        painelNotas.add(new JScrollPane(tabelaNotas), BorderLayout.CENTER);
        abas.addTab("Disciplinas & Notas", painelNotas);

        // Monta o painel
        add(painelPerfil, BorderLayout.NORTH);
        add(abas, BorderLayout.CENTER);
    }

    private JTextField criarCampo() {
        JTextField campo = new JTextField();
        campo.setEditable(false);
        return campo;
    }
}
