package view;

import controller.SistemaController;
import model.Professor;
import model.Turma;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PainelProfessor extends JPanel {

    private JLabel lblFoto;
    private JButton btnAnexarFoto, btnSalvarNotas, btnGerarPauta;
    private JTextField txtNome, txtEmail, txtDisciplina, txtSalario;
    private JTable tabelaTurma, tabelaNotas;
    private JTextField txtPesquisarAluno;

    public PainelProfessor(SistemaController controller, Professor professor) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));

        // Painel superior - Perfil
        JPanel painelPerfil = new JPanel(new BorderLayout(10, 10));
        painelPerfil.setBackground(Color.WHITE);
        painelPerfil.setBorder(BorderFactory.createTitledBorder("Perfil do Professor"));

        lblFoto = new JLabel("Foto", SwingConstants.CENTER);
        lblFoto.setPreferredSize(new Dimension(120, 150));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btnAnexarFoto = new JButton("Anexar Foto");

        JPanel painelFoto = new JPanel(new BorderLayout(5, 5));
        painelFoto.add(lblFoto, BorderLayout.CENTER);
        painelFoto.add(btnAnexarFoto, BorderLayout.SOUTH);

        // Dados básicos
        JPanel painelDados = new JPanel(new GridLayout(4, 2, 10, 10));
        painelDados.add(new JLabel("Nome:"));
        txtNome = criarCampo(professor.getNome());
        painelDados.add(txtNome);

        painelDados.add(new JLabel("Email:"));
        txtEmail = criarCampo(professor.getEmail());
        painelDados.add(txtEmail);

        painelDados.add(new JLabel("Disciplina Principal:"));
        txtDisciplina = criarCampo(professor.getDisciplinaPrincipal());
        painelDados.add(txtDisciplina);

        painelDados.add(new JLabel("Salário:"));
        txtSalario = criarCampo(String.valueOf(professor.getSalario()));
        painelDados.add(txtSalario);

        painelPerfil.add(painelFoto, BorderLayout.WEST);
        painelPerfil.add(painelDados, BorderLayout.CENTER);

        // Abas
        JTabbedPane abas = new JTabbedPane();

        // ====================== TURMAS ======================
        JPanel painelTurmas = new JPanel(new BorderLayout(5, 5));
        txtPesquisarAluno = new JTextField();
        JPanel painelBusca = new JPanel(new BorderLayout());
        painelBusca.add(new JLabel("Pesquisar aluno:"), BorderLayout.WEST);
        painelBusca.add(txtPesquisarAluno, BorderLayout.CENTER);
        painelTurmas.add(painelBusca, BorderLayout.NORTH);

        DefaultTableModel modeloTurma = new DefaultTableModel(
                new String[]{"ID Turma", "Disciplina", "Curso"}, 0
        );
        tabelaTurma = new JTable(modeloTurma);

        for (Turma t : controller.getTurmasProfessor(professor)) {
            modeloTurma.addRow(new Object[]{t.getIdTurma(), t.getDisciplina(), t.getCurso()});
        }

        painelTurmas.add(new JScrollPane(tabelaTurma), BorderLayout.CENTER);
        abas.addTab("Turmas", painelTurmas);

        // ====================== LANÇAR NOTAS ======================
        JPanel painelNotas = new JPanel(new BorderLayout(5, 5));
        DefaultTableModel modeloNotas = new DefaultTableModel(
                new String[]{"Aluno", "Disciplina", "Nota"}, 0
        );
        tabelaNotas = new JTable(modeloNotas);

        // Você pode preencher a lista inicial de notas se desejar
        // (ou deixar vazia e adicionar conforme o professor lançar)

        painelNotas.add(new JScrollPane(tabelaNotas), BorderLayout.CENTER);

        btnSalvarNotas = new JButton("Salvar Notas");
        btnSalvarNotas.setBackground(new Color(46, 204, 113));
        btnSalvarNotas.setForeground(Color.WHITE);
        btnSalvarNotas.addActionListener(e -> controller.salvarNotas(tabelaNotas, professor));

        painelNotas.add(btnSalvarNotas, BorderLayout.SOUTH);
        abas.addTab("Lançar Notas", painelNotas);

        // ====================== PAUTAS ======================
        JPanel painelPautas = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnGerarPauta = new JButton("Gerar Pauta da Turma");
        btnGerarPauta.setBackground(new Color(241, 196, 15));
        // btnGerarPauta.addActionListener(e -> controller.gerarPauta(turmaSelecionada));
        painelPautas.add(btnGerarPauta);
        abas.addTab("Pautas", painelPautas);

        // Montagem final
        add(painelPerfil, BorderLayout.NORTH);
        add(abas, BorderLayout.CENTER);
    }

    private JTextField criarCampo(String texto) {
        JTextField campo = new JTextField(texto);
        campo.setEditable(false);
        return campo;
    }
}
