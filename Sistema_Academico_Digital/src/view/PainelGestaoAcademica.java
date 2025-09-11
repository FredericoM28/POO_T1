package view;

import controller.SistemaController;
import model.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;


public class PainelGestaoAcademica extends JFrame {

    private final SistemaController controller;

    // Paleta & estilo
    private static final Color BG = new Color(245, 247, 250);
    private static final Color SURFACE = new Color(255, 255, 255);
    private static final Color PRIMARY = new Color(59, 130, 246);      // Azul vibrante
    private static final Color PRIMARY_DARK = new Color(37, 99, 235);
    private static final Color ACCENT = new Color(16, 185, 129);       // Verde
    private static final Color WARN = new Color(234, 179, 8);          // Amarelo
    private static final Color DANGER = new Color(239, 68, 68);        // Vermelho
    private static final Color TEXT = new Color(17, 24, 39);
    private static final Color TEXT_MUTED = new Color(107, 114, 128);
    private static final Color TABLE_ALT = new Color(249, 250, 251);
    private static final Font  FONT_TITLE = new Font("SansSerif", Font.BOLD, 20);
    private static final Font  FONT_H2 = new Font("SansSerif", Font.BOLD, 16);
    private static final Font  FONT_REG = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font  FONT_BTN = new Font("SansSerif", Font.BOLD, 14);

    // Tabelas
    private JTable tabelaAlunos, tabelaProfessores, tabelaTurmas, tabelaDisciplinas;
    private DefaultTableModel modeloAlunos, modeloProfessores, modeloTurmas, modeloDisciplinas;

    // Relatório
    private JTextArea txtRelatorio;

    public PainelGestaoAcademica(SistemaController controller) {
        this.controller = controller;

        setTitle("Gestão Acadêmica — Sistema Acadêmico");
        setSize(1200, 760);
        setMinimumSize(new Dimension(1100, 680));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Aparência global leve (sem libs externas)
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        UIManager.put("Table.showVerticalLines", Boolean.FALSE);
        UIManager.put("Table.showHorizontalLines", Boolean.TRUE);

        // Container base
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);

        // Topbar
        root.add(buildTopBar(), BorderLayout.NORTH);

        // Abas
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.setFont(FONT_REG);
        tabs.setBackground(BG);
        tabs.setBorder(new EmptyBorder(8, 16, 16, 16));

        tabs.addTab("Alunos", buildPadded(criarPainelAlunos()));
        tabs.addTab("Professores", buildPadded(criarPainelProfessores()));
        tabs.addTab("Turmas", buildPadded(criarPainelTurmas()));
        tabs.addTab("Disciplinas", buildPadded(criarPainelDisciplina()));
        tabs.addTab("Relatórios", buildPadded(criarPainelRelatorios()));



        // ------------------ NOTAS ------------------
JPanel painelNotas = new JPanel(new BorderLayout());

// Topo: selecionar aluno e disciplina
JPanel painelSelecao = new JPanel();
JComboBox<String> comboAlunos = new JComboBox<>(
        controller.alunoListar().stream().map(Aluno::getNome).toArray(String[]::new)
);
JComboBox<String> comboDisciplinas = new JComboBox<>(
        controller.disciplinaListar().stream().map(Disciplina::getNome).toArray(String[]::new)
);
JTextField campoNota = new JTextField(5);
JButton btnRegistrarNota = new JButton("Registrar Nota");
JButton btnVerSituacao = new JButton("Ver Situação");

painelSelecao.add(new JLabel("Aluno:"));
painelSelecao.add(comboAlunos);
painelSelecao.add(new JLabel("Disciplina:"));
painelSelecao.add(comboDisciplinas);
painelSelecao.add(new JLabel("Nota:"));
painelSelecao.add(campoNota);
painelSelecao.add(btnRegistrarNota);
painelSelecao.add(btnVerSituacao);

painelNotas.add(painelSelecao, BorderLayout.NORTH);

                // Tabela de notas
                String[] colunasNotas = {"Disciplina", "Nota"};
                DefaultTableModel modeloNotas = new DefaultTableModel(colunasNotas, 0);
                JTable tabelaNotas = new JTable(modeloNotas);
                painelNotas.add(new JScrollPane(tabelaNotas), BorderLayout.CENTER);

                // Evento: registrar nota
                btnRegistrarNota.addActionListener(e -> {
                    String nomeAluno = (String) comboAlunos.getSelectedItem();
                    String disciplina = (String) comboDisciplinas.getSelectedItem();
                    double valor;
                    try {
                        valor = Double.parseDouble(campoNota.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Nota inválida!");
                        return;
                    }

                    Aluno aluno = controller.alunoListar().stream()
                            .filter(a -> a.getNome().equals(nomeAluno))
                            .findFirst().orElse(null);

                    if (aluno != null) {
                        controller.registrarNota(aluno, disciplina, valor);
                        modeloNotas.setRowCount(0);
                        for (Nota n : controller.listarNotasAluno(aluno)) {
                            modeloNotas.addRow(new Object[]{n.getDisciplina(), n.getValor()});
                        }
                    }
                });

                // Evento: ver situação
                btnVerSituacao.addActionListener(e -> {
                    String nomeAluno = (String) comboAlunos.getSelectedItem();
                    Aluno aluno = controller.alunoListar().stream()
                            .filter(a -> a.getNome().equals(nomeAluno))
                            .findFirst().orElse(null);

                    if (aluno != null) {
                        String situacao = controller.verificarSituacaoAluno(aluno);
                        JOptionPane.showMessageDialog(this, "Situação: " + situacao);
                    }
                });

                //abas.addTab("Notas", painelNotas);
                tabs.addTab("Notas", painelNotas);


                // ------------------ MENSALIDADES ------------------
JPanel painelMensalidades = new JPanel(new BorderLayout());

// Topo: selecionar aluno, mês e valor
JPanel painelFormMensal = new JPanel();
JComboBox<String> comboAlunosMensal = new JComboBox<>(
        controller.alunoListar().stream().map(Aluno::getNome).toArray(String[]::new)
);
JTextField txtMes = new JTextField(5);
JTextField txtValor = new JTextField(6);
JButton btnGerarMensalidade = new JButton("Gerar Mensalidade");
JButton btnRegistrarPagamento = new JButton("Registrar Pagamento");

painelFormMensal.add(new JLabel("Aluno:"));
painelFormMensal.add(comboAlunosMensal);
painelFormMensal.add(new JLabel("Mês:"));
painelFormMensal.add(txtMes);
painelFormMensal.add(new JLabel("Valor:"));
painelFormMensal.add(txtValor);
painelFormMensal.add(btnGerarMensalidade);
painelFormMensal.add(btnRegistrarPagamento);

painelMensalidades.add(painelFormMensal, BorderLayout.NORTH);

// Tabela de mensalidades
String[] colunasMensalidade = {"ID Fatura", "Aluno", "Mês", "Valor", "Estado"};
DefaultTableModel modeloMensalidade = new DefaultTableModel(colunasMensalidade, 0);
JTable tabelaMensalidade = new JTable(modeloMensalidade);
styleTable(tabelaMensalidade);
painelMensalidades.add(new JScrollPane(tabelaMensalidade), BorderLayout.CENTER);

// Atualiza tabela
Runnable atualizarMensalidades = () -> {
    modeloMensalidade.setRowCount(0);
    for (Aluno a : controller.alunoListar()) {
        for (Fatura f : controller.listarMensalidadesDoAluno(a)) {
            modeloMensalidade.addRow(new Object[]{
                    f.getIdFatura(),
                    a.getNome(),
                    f.getMes(),
                    f.getValor(),
                    f.getEstado()
            });
        }
    }
};
atualizarMensalidades.run();

// Evento: gerar mensalidade
btnGerarMensalidade.addActionListener(e -> {
    String nomeAluno = (String) comboAlunosMensal.getSelectedItem();
    Aluno aluno = controller.alunoListar().stream()
            .filter(a -> a.getNome().equals(nomeAluno))
            .findFirst().orElse(null);
    if (aluno != null) {
        try {
            double valor = Double.parseDouble(txtValor.getText());
            controller.gerarMensalidade(aluno, txtMes.getText(), valor);
            atualizarMensalidades.run();
            JOptionPane.showMessageDialog(this, "Mensalidade gerada!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido!");
        }
    }
});

// Evento: registrar pagamento
btnRegistrarPagamento.addActionListener(e -> {
    int row = tabelaMensalidade.getSelectedRow();
    if (row >= 0) {
        String idFatura = modeloMensalidade.getValueAt(row, 0).toString();
        controller.registrarPagamento(idFatura);
        atualizarMensalidades.run();
        JOptionPane.showMessageDialog(this, "Pagamento registrado!");
    } else {
        JOptionPane.showMessageDialog(this, "Selecione uma mensalidade na tabela.");
    }
});

// Adicionar aba
tabs.addTab("Mensalidades", buildPadded(painelMensalidades));



        root.add(tabs, BorderLayout.CENTER);

        // Barra inferior
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(BG);
        JButton btnSair = createButton("Sair", DANGER, Color.WHITE);
        btnSair.addActionListener(e -> dispose());
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        right.add(btnSair);
        bottom.add(right, BorderLayout.EAST);
        bottom.setBorder(new EmptyBorder(0, 16, 16, 16));
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
        setVisible(true);
    }

    // ===================== COMPONENTES DE APOIO =====================
    private JPanel buildTopBar() {
        JPanel top = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Gradiente suave
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY, getWidth(), 0, PRIMARY_DARK);
                g2.setPaint(gp);
                g2.fillRoundRect(8, 8, getWidth() - 16, getHeight() - 8, 24, 24);
                g2.dispose();
            }
        };
        top.setOpaque(false);
        top.setPreferredSize(new Dimension(0, 90));
        top.setBackground(BG);
        top.setBorder(new EmptyBorder(16, 16, 0, 16));

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel title = new JLabel("Gestão Acadêmica");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Administre alunos, professores, turmas, disciplinas e relatórios");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(new Color(255, 255, 255, 215));

        JPanel textBox = new JPanel();
        textBox.setOpaque(false);
        textBox.setLayout(new BoxLayout(textBox, BoxLayout.Y_AXIS));
        textBox.add(title);
        textBox.add(Box.createVerticalStrut(4));
        textBox.add(subtitle);

        // Ações rápidas (decorativas/uteis)
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        JButton btnRefresh = createButton("Atualizar Tabelas", ACCENT, Color.WHITE);
        btnRefresh.addActionListener(e -> {
            atualizarTabelaAlunos();
            atualizarTabelaProfessores();
            atualizarTabelaTurmas();
            atualizarTabelaDisciplinas();
            JOptionPane.showMessageDialog(this, "Tabelas atualizadas!");
        });
        JButton btnTema = createButton("Tema Claro", WARN, Color.BLACK);
        btnTema.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tema aplicado (demonstrativo)."));

        actions.add(btnRefresh);
        actions.add(btnTema);

        content.add(textBox, BorderLayout.WEST);
        content.add(actions, BorderLayout.EAST);

        top.add(content, BorderLayout.CENTER);
        return top;
    }

    private JPanel buildCard(String titulo, JComponent inner) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(SURFACE);
        card.setBorder(new CompoundBorder(
                new EmptyBorder(0, 0, 8, 0),
                new CompoundBorder(new LineBorder(new Color(229, 231, 235)), new EmptyBorder(16, 16, 16, 16))
        ));

        JLabel h = new JLabel(titulo);
        h.setFont(FONT_H2);
        h.setForeground(TEXT);
        h.setBorder(new EmptyBorder(0, 0, 12, 0));

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.add(h, BorderLayout.NORTH);
        wrap.add(inner, BorderLayout.CENTER);

        card.add(wrap, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildPadded(JComponent c) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG);
        p.setBorder(new EmptyBorder(8, 8, 8, 8));
        p.add(c, BorderLayout.CENTER);
        return p;
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setFont(FONT_BTN);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(10, 16, 10, 16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.setBorder(new CompoundBorder(
                new LineBorder(new Color(0,0,0,25), 1, true),
                new EmptyBorder(8, 18, 8, 18)
        ));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { b.setBackground(b.getBackground().darker()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { b.setBackground(bg); }
        });
        return b;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(FONT_REG);
        table.setForeground(TEXT);
        table.setGridColor(new Color(241, 245, 249));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(243, 244, 246));
        table.getTableHeader().setForeground(TEXT);

        // Zebra
        DefaultTableCellRenderer zebra = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : TABLE_ALT);
                } else {
                    c.setBackground(new Color(219, 234, 254));
                }
                setBorder(new EmptyBorder(0, 8, 0, 8));
                return c;
            }
        };
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(zebra);
        }
    }

    private JPanel formGrid(int rows, int cols) {
        JPanel form = new JPanel(new GridLayout(rows, cols, 12, 10));
        form.setOpaque(false);
        return form;
    }

    private JTextField field() {
        JTextField f = new JTextField();
        f.setFont(FONT_REG);
        f.setBorder(new CompoundBorder(
                new LineBorder(new Color(229, 231, 235), 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        return f;
    }

    // ===================== PAINÉIS =====================
    private JPanel criarPainelAlunos() {
    JPanel root = new JPanel(new BorderLayout(12, 12));
    root.setOpaque(false);

    // Tabela
    modeloAlunos = new DefaultTableModel(new Object[]{"Matrícula", "Nome", "Curso", "Email", "Telefone"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    tabelaAlunos = new JTable(modeloAlunos);
    styleTable(tabelaAlunos);
    atualizarTabelaAlunos();

    JScrollPane sp = new JScrollPane(tabelaAlunos);
    sp.getViewport().setBackground(Color.WHITE);
    JPanel cardTable = buildCard("Lista de Alunos", sp);

    // Formulário
    JPanel form = formGrid(2, 6);
    JTextField txtMatricula = field();
    JTextField txtNome = field();

    // 🔹 Substituí o JTextField por ComboBox de Cursos
    JComboBox<Curso> comboCurso = criarComboCursos();

    JTextField txtEmail = field();
    JTextField txtTelefone = field();
    JTextField txtIdade = field();

    form.add(new JLabel("Matrícula:")); form.add(txtMatricula);
    form.add(new JLabel("Nome:"));      form.add(txtNome);
    form.add(new JLabel("Curso:"));     form.add(comboCurso); // aqui uso o ComboBox
    form.add(new JLabel("Email:"));     form.add(txtEmail);
    form.add(new JLabel("Telefone:"));  form.add(txtTelefone);
    form.add(new JLabel("Idade:"));     form.add(txtIdade);

    JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
    actions.setOpaque(false);
    JButton btnAdd = createButton("Adicionar", PRIMARY, Color.WHITE);
    JButton btnEditar = createButton("Editar", WARN, Color.BLACK);
    JButton btnRemover = createButton("Remover", DANGER, Color.WHITE);

    btnAdd.addActionListener(e -> {
        Curso cursoSelecionado = (Curso) comboCurso.getSelectedItem(); // pega curso
        controller.alunoCriar(
                txtMatricula.getText(),
                txtNome.getText(),
                cursoSelecionado != null ? cursoSelecionado.getCodigo() : "", // salva código
                txtEmail.getText(),
                txtTelefone.getText(),
                txtIdade.getText()
        );
        atualizarTabelaAlunos();
        JOptionPane.showMessageDialog(this, "Aluno adicionado!");
    });

    btnEditar.addActionListener(e -> {
        int row = tabelaAlunos.getSelectedRow();
        if (row >= 0) {
            String matricula = modeloAlunos.getValueAt(row, 0).toString();
            Curso cursoSelecionado = (Curso) comboCurso.getSelectedItem();
            controller.alunoAtualizar(
                    matricula,
                    txtNome.getText(),
                    cursoSelecionado != null ? cursoSelecionado.getCodigo() : "",
                    txtEmail.getText(),
                    txtTelefone.getText(),
                    txtIdade.getText()
            );
            atualizarTabelaAlunos();
            JOptionPane.showMessageDialog(this, "Aluno atualizado!");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela.");
        }
    });

    // botão remover mantem igual
    btnRemover.addActionListener(e -> {
        int row = tabelaAlunos.getSelectedRow();
        if (row >= 0) {
            String matricula = modeloAlunos.getValueAt(row, 0).toString();
            int ok = JOptionPane.showConfirmDialog(this, "Remover aluno " + matricula + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                controller.alunoRemover(matricula);
                atualizarTabelaAlunos();
                JOptionPane.showMessageDialog(this, "Aluno removido!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela.");
        }
    });

    actions.add(btnAdd);
    actions.add(btnEditar);
    actions.add(btnRemover);

    JPanel formWrap = new JPanel(new BorderLayout());
    formWrap.setOpaque(false);
    formWrap.add(form, BorderLayout.CENTER);
    formWrap.add(actions, BorderLayout.SOUTH);

    JPanel cardForm = buildCard("Cadastro / Edição", formWrap);

    JPanel grid = new JPanel(new GridLayout(2, 1, 12, 12));
    grid.setOpaque(false);
    grid.add(cardTable);
    grid.add(cardForm);

    root.add(grid, BorderLayout.CENTER);
    return root;
}

    private JPanel criarPainelProfessores() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setOpaque(false);

        // 🔹 Tabela de Professores
        modeloProfessores = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Email", "Disciplina", "Salário"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaProfessores = new JTable(modeloProfessores);
        styleTable(tabelaProfessores);
        atualizarTabelaProfessores();

        JScrollPane sp = new JScrollPane(tabelaProfessores);
        sp.getViewport().setBackground(Color.WHITE);
        JPanel cardTable = buildCard("Lista de Professores", sp);

        // 🔹 Formulário
        JPanel form = formGrid(2, 5);
        JTextField txtId = field();
        JTextField txtNome = field();
        JTextField txtEmail = field();
        JTextField txtSalario = field();

        // 🔹 ComboBox de Disciplinas
        JComboBox<Disciplina> comboDisciplina = criarComboDisciplinas();

        form.add(new JLabel("ID:"));         form.add(txtId);
        form.add(new JLabel("Nome:"));       form.add(txtNome);
        form.add(new JLabel("Email:"));      form.add(txtEmail);
        form.add(new JLabel("Disciplina:")); form.add(comboDisciplina);
        form.add(new JLabel("Salário:"));    form.add(txtSalario);

        // 🔹 Botões
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        JButton btnAdd = createButton("Cadastrar", PRIMARY, Color.WHITE);
        JButton btnEditar = createButton("Editar", WARN, Color.BLACK);
        JButton btnRemover = createButton("Remover", DANGER, Color.WHITE);

        // Cadastrar
        btnAdd.addActionListener(e -> {
            Disciplina disciplina = (Disciplina) comboDisciplina.getSelectedItem();
            controller.professorCriar(
                txtId.getText(),
                txtNome.getText(),
                txtEmail.getText(),
                disciplina != null ? disciplina.getCodigo() : "",
                txtSalario.getText()
            );
            atualizarTabelaProfessores();
            JOptionPane.showMessageDialog(this, "Professor cadastrado!");
        });

        // Editar
        btnEditar.addActionListener(e -> {
            int row = tabelaProfessores.getSelectedRow();
            if (row >= 0) {
                String id = modeloProfessores.getValueAt(row, 0).toString();
                Disciplina disciplina = (Disciplina) comboDisciplina.getSelectedItem();
                controller.professorAtualizar(
                    id,
                    txtNome.getText(),
                    txtEmail.getText(),
                    disciplina != null ? disciplina.getCodigo() : "",
                    txtSalario.getText()
                );
                atualizarTabelaProfessores();
                JOptionPane.showMessageDialog(this, "Professor atualizado!");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um professor.");
            }
        });

        // Remover
        btnRemover.addActionListener(e -> {
            int row = tabelaProfessores.getSelectedRow();
            if (row >= 0) {
                String id = modeloProfessores.getValueAt(row, 0).toString();
                int ok = JOptionPane.showConfirmDialog(
                    this,
                    "Remover professor " + id + "?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
                );
                if (ok == JOptionPane.YES_OPTION) {
                    controller.professorRemover(id);
                    atualizarTabelaProfessores();
                    JOptionPane.showMessageDialog(this, "Professor removido!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um professor.");
            }
        });

        actions.add(btnAdd);
        actions.add(btnEditar);
        actions.add(btnRemover);

        JPanel formWrap = new JPanel(new BorderLayout());
        formWrap.setOpaque(false);
        formWrap.add(form, BorderLayout.CENTER);
        formWrap.add(actions, BorderLayout.SOUTH);

        JPanel cardForm = buildCard("Cadastro / Edição", formWrap);

        JPanel grid = new JPanel(new GridLayout(2, 1, 12, 12));
        grid.setOpaque(false);
        grid.add(cardTable);
        grid.add(cardForm);

        root.add(grid, BorderLayout.CENTER);
        return root;
    }



    private JPanel criarPainelTurmas() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setOpaque(false);

        // Tabela de Turmas
        modeloTurmas = new DefaultTableModel(
            new Object[]{"Código", "Curso", "Disciplina", "Professor", "Ano", "Semestre", "Capacidade"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaTurmas = new JTable(modeloTurmas);
        styleTable(tabelaTurmas);
        atualizarTabelaTurmas();

        JScrollPane sp = new JScrollPane(tabelaTurmas);
        sp.getViewport().setBackground(Color.WHITE);
        JPanel cardTable = buildCard("Lista de Turmas", sp);

        // Formulário
        JPanel form = formGrid(2, 7);
        JTextField txtCodigo = field();
        JTextField txtAno = field();
        JTextField txtCapacidade = field();

        // 🔹 ComboBox de Cursos
        JComboBox<Curso> comboCurso = criarComboCursos();

        // 🔹 ComboBox de Disciplinas
        JComboBox<Disciplina> comboDisciplina = criarComboDisciplinas();

        // 🔹 ComboBox de Professores
        JComboBox<Professor> comboProfessor = criarComboProfessores();

        // 🔹 ComboBox de Semestre
        JComboBox<String> comboSemestre = new JComboBox<>(new String[]{
            "1º Semestre", "2º Semestre"
        });

        // Campos no formulário
        form.add(new JLabel("Código:"));     form.add(txtCodigo);
        form.add(new JLabel("Curso:"));      form.add(comboCurso);
        form.add(new JLabel("Disciplina:")); form.add(comboDisciplina);
        form.add(new JLabel("Professor:"));  form.add(comboProfessor);
        form.add(new JLabel("Ano:"));        form.add(txtAno);
        form.add(new JLabel("Semestre:"));   form.add(comboSemestre);
        form.add(new JLabel("Capacidade:")); form.add(txtCapacidade);

        // Botões
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        JButton btnAdd = createButton("Adicionar", PRIMARY, Color.WHITE);
        JButton btnEditar = createButton("Editar", WARN, Color.BLACK);
        JButton btnRemover = createButton("Remover", DANGER, Color.WHITE);

        btnAdd.addActionListener(e -> {
            Curso curso = (Curso) comboCurso.getSelectedItem();
            Disciplina disciplina = (Disciplina) comboDisciplina.getSelectedItem();
            Professor professor = (Professor) comboProfessor.getSelectedItem();
            String semestre = (String) comboSemestre.getSelectedItem();

            controller.turmaCriar(
                txtCodigo.getText(),
                disciplina != null ? disciplina.getCodigo() : "",
                curso != null ? curso.getCodigo() : "",
                professor != null ? professor.getIdProfessor() : "",
                txtAno.getText(),
                semestre,
                txtCapacidade.getText()
            );
            atualizarTabelaTurmas();
            JOptionPane.showMessageDialog(this, "Turma adicionada!");
        });

        btnEditar.addActionListener(e -> {
            int row = tabelaTurmas.getSelectedRow();
            if (row >= 0) {
                String codigo = modeloTurmas.getValueAt(row, 0).toString();
                Curso curso = (Curso) comboCurso.getSelectedItem();
                Disciplina disciplina = (Disciplina) comboDisciplina.getSelectedItem();
                Professor professor = (Professor) comboProfessor.getSelectedItem();
                String semestre = (String) comboSemestre.getSelectedItem();

                controller.turmaAtualizar(
                    codigo,
                    disciplina != null ? disciplina.getCodigo() : "",
                    curso != null ? curso.getCodigo() : "",
                    professor != null ? professor.getIdProfessor() : "",
                    txtAno.getText(),
                    semestre,
                    txtCapacidade.getText()
                );
                atualizarTabelaTurmas();
                JOptionPane.showMessageDialog(this, "Turma atualizada!");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma turma na tabela.");
            }
        });

        btnRemover.addActionListener(e -> {
            int row = tabelaTurmas.getSelectedRow();
            if (row >= 0) {
                String codigo = modeloTurmas.getValueAt(row, 0).toString();
                int ok = JOptionPane.showConfirmDialog(this, "Remover turma " + codigo + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (ok == JOptionPane.YES_OPTION) {
                    controller.turmaRemover(codigo);
                    atualizarTabelaTurmas();
                    JOptionPane.showMessageDialog(this, "Turma removida!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma turma na tabela.");
            }
        });

        actions.add(btnAdd);
        actions.add(btnEditar);
        actions.add(btnRemover);

        JPanel formWrap = new JPanel(new BorderLayout());
        formWrap.setOpaque(false);
        formWrap.add(form, BorderLayout.CENTER);
        formWrap.add(actions, BorderLayout.SOUTH);

        JPanel cardForm = buildCard("Cadastro / Edição", formWrap);

        JPanel grid = new JPanel(new GridLayout(2, 1, 12, 12));
        grid.setOpaque(false);
        grid.add(cardTable);
        grid.add(cardForm);

        root.add(grid, BorderLayout.CENTER);
        return root;
    }



    private JPanel criarPainelDisciplina() {
        JPanel painel = new JPanel(new BorderLayout());

        // 🔹 Tabela de Disciplinas
        String[] colunas = {"Código", "Nome", "Curso", "Professor"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        tabelaDisciplinas = new JTable(modelo);
        painel.add(new JScrollPane(tabelaDisciplinas), BorderLayout.CENTER);

        // 🔹 Formulário de cadastro de disciplina
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));

        JTextField campoCodigo = new JTextField();
        JTextField campoNome = new JTextField();

        // 🔹 ComboBox de Curso
        JComboBox<Curso> comboCurso = criarComboCursos();

        // 🔹 ComboBox de Professor
        JComboBox<Professor> comboProfessor = criarComboProfessores();

        form.add(new JLabel("Código:"));
        form.add(campoCodigo);

        form.add(new JLabel("Nome:"));
        form.add(campoNome);

        form.add(new JLabel("Curso:"));
        form.add(comboCurso);

        form.add(new JLabel("Professor:"));
        form.add(comboProfessor);

        painel.add(form, BorderLayout.NORTH);

        // 🔹 Botões de ação
        JPanel botoes = new JPanel();

        JButton btnCriar = new JButton("Criar");
        btnCriar.addActionListener(e -> {
            Curso curso = (Curso) comboCurso.getSelectedItem();
            Professor professor = (Professor) comboProfessor.getSelectedItem();

            controller.disciplinaCriar(
                campoCodigo.getText(),
                campoNome.getText(),
                (curso != null) ? curso.getCodigo() : "",
                (professor != null) ? professor.getIdProfessor() : ""
            );
            atualizarTabelaDisciplinas();
        });

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> {
            int row = tabelaDisciplinas.getSelectedRow();
            if (row != -1) {
                Curso curso = (Curso) comboCurso.getSelectedItem();
                Professor professor = (Professor) comboProfessor.getSelectedItem();

                controller.disciplinaAtualizar(
                    campoCodigo.getText(),
                    campoNome.getText(),
                    (curso != null) ? curso.getCodigo() : "",
                    (professor != null) ? professor.getIdProfessor() : ""
                );
                atualizarTabelaDisciplinas();
            }
        });

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> {
            int row = tabelaDisciplinas.getSelectedRow();
            if (row != -1) {
                String codigo = (String) tabelaDisciplinas.getValueAt(row, 0);
                controller.disciplinaRemover(codigo);
                atualizarTabelaDisciplinas();
            }
        });

        botoes.add(btnCriar);
        botoes.add(btnAtualizar);
        botoes.add(btnExcluir);
        painel.add(botoes, BorderLayout.SOUTH);

        return painel;
    }


    private JPanel criarPainelRelatorios() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setOpaque(false);

        txtRelatorio = new JTextArea();
        txtRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtRelatorio.setForeground(TEXT);
        txtRelatorio.setBorder(new EmptyBorder(12, 12, 12, 12));
        JScrollPane sp = new JScrollPane(txtRelatorio);
        sp.getViewport().setBackground(Color.WHITE);

        JPanel cardArea = buildCard("Relatório", sp);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JTextField txtTipo = field();
        txtTipo.setColumns(20);
        txtTipo.setToolTipText("Ex.: alunos por curso | professores | turmas");

        JButton btnGerar = createButton("Gerar", PRIMARY, Color.WHITE);
        JButton btnExportar = createButton("Exportar .txt", ACCENT, Color.WHITE);

        btnGerar.addActionListener(e -> txtRelatorio.setText(controller.gerarRelatorio(txtTipo.getText())));

        btnExportar.addActionListener(e -> {
            try (FileWriter writer = new FileWriter("relatorio.txt")) {
                writer.write(txtRelatorio.getText());
                JOptionPane.showMessageDialog(this, "Relatório exportado com sucesso!\nArquivo: relatorio.txt");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar relatório: " + ex.getMessage());
            }
        });

        actions.add(new JLabel("Tipo de Relatório:"));
        actions.add(txtTipo);
        actions.add(btnGerar);
        actions.add(btnExportar);

        JPanel cardActions = buildCard("Gerar / Exportar", actions);

        JPanel grid = new JPanel(new GridLayout(2, 1, 12, 12));
        grid.setOpaque(false);
        grid.add(cardArea);
        grid.add(cardActions);

        root.add(grid, BorderLayout.CENTER);
        return root;
    }

    // ==================== ATUALIZAÇÃO DAS TABELAS ====================
    private void atualizarTabelaAlunos() {
        modeloAlunos.setRowCount(0);
        for (Aluno a : controller.alunoListar()) {
            String curso = (a.getCurso() != null) ? a.getCurso().getCodigo() : "";
            modeloAlunos.addRow(new Object[]{a.getMatricula(), a.getNome(), curso, a.getEmail(), a.getTelefone()});
        }
    }

    private void atualizarTabelaProfessores() {
        modeloProfessores.setRowCount(0);
        for (Professor p : controller.professorListar()) {
            modeloProfessores.addRow(new Object[]{p.getIdProfessor(), p.getNome(), p.getDisciplinaPrincipal(), p.getEmail(), p.getSalario()});
        }
    }

    private void atualizarTabelaTurmas() {
        modeloTurmas.setRowCount(0);
        for (Turma t : controller.turmaListar()) {
            modeloTurmas.addRow(new Object[]{t.getIdTurma(), t.getDisciplina(), t.getCurso(), t.getProfessor(), t.getAno(), t.getSemestre(), t.getCapacidade()});
        }
    }

    private void atualizarTabelaDisciplinas() {
        modeloDisciplinas.setRowCount(0);
        for (Disciplina d : controller.disciplinaListar()) {
            modeloDisciplinas.addRow(new Object[]{d.getCodigo(), d.getNome(), d.getCurso()});
        }
    }

    // ================== COMBOBOXES ==================

    // Cursos
    private JComboBox<Curso> criarComboCursos() {
        JComboBox<Curso> comboCurso = new JComboBox<>();
        for (Curso c : controller.cursoListar()) {
            comboCurso.addItem(c);
        }
        return comboCurso;
    }

    // Disciplinas
    private JComboBox<Disciplina> criarComboDisciplinas() {
        JComboBox<Disciplina> comboDisciplina = new JComboBox<>();
        for (Disciplina d : controller.disciplinaListar()) {
            comboDisciplina.addItem(d);
        }
        return comboDisciplina;
    }

    // Turmas
    private JComboBox<Turma> criarComboTurmas() {
        JComboBox<Turma> comboTurma = new JComboBox<>();
        for (Turma t : controller.turmaListar()) {
            comboTurma.addItem(t);
        }
        return comboTurma;
    }

    // Semestres 
    private JComboBox<String> criarComboSemestres() {
        return new JComboBox<>(new String[]{
            "1º Semestre", "2º Semestre", "3º Semestre", "4º Semestre",
            "5º Semestre", "6º Semestre", "7º Semestre", "8º Semestre"
        });
    }

    //  Criar ComboBox de Professores
    private JComboBox<Professor> criarComboProfessores() {
        JComboBox<Professor> combo = new JComboBox<>();
        combo.removeAllItems();
        for (Professor p : controller.professorListar()) {
            combo.addItem(p);
        }
        return combo;
    }




}
