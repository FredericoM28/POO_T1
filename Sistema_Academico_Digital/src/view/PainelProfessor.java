package view;

import controller.SistemaController;
import model.Professor;
import model.Turma;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PainelProfessor extends JPanel {

    private JTable tabelaTurmas, tabelaNotas;

    public PainelProfessor(SistemaController controller, Professor professor){
        setLayout(new BorderLayout(10,10));
        setBackground(new Color(245,247,250));

        JPanel painelPerfil = new JPanel(new GridLayout(4,2,10,10));
        painelPerfil.setBorder(BorderFactory.createTitledBorder("Dados do Professor"));
        painelPerfil.setBackground(new Color(245,247,250));

        painelPerfil.add(new JLabel("Nome:")); painelPerfil.add(new JLabel(professor.getNome()));
        painelPerfil.add(new JLabel("Email:")); painelPerfil.add(new JLabel(professor.getEmail()));
        painelPerfil.add(new JLabel("Disciplina:")); painelPerfil.add(new JLabel(professor.getDisciplinaPrincipal()));
        painelPerfil.add(new JLabel("Salário:")); painelPerfil.add(new JLabel(String.valueOf(professor.getSalario())));

        JTabbedPane abas = new JTabbedPane();

        DefaultTableModel modelTurmas = new DefaultTableModel(new String[]{"ID","Disciplina","Curso"},0);
        tabelaTurmas = new JTable(modelTurmas);
        for(Turma t : controller.getTurmasProfessor(professor)){
            modelTurmas.addRow(new Object[]{t.getIdTurma(),t.getDisciplina(),t.getCurso()});
        }
        abas.addTab("Turmas", new JScrollPane(tabelaTurmas));

        DefaultTableModel modelNotas = new DefaultTableModel(new String[]{"Aluno","Disciplina","Nota"},0);
        tabelaNotas = new JTable(modelNotas);
        JButton btnSalvar = new JButton("Salvar Notas");
        btnSalvar.addActionListener(e -> controller.salvarNotas(tabelaNotas,professor));

        JPanel painelNotas = new JPanel(new BorderLayout());
        painelNotas.add(new JScrollPane(tabelaNotas), BorderLayout.CENTER);
        painelNotas.add(btnSalvar, BorderLayout.SOUTH);
        abas.addTab("Lançar Notas", painelNotas);

        add(painelPerfil, BorderLayout.NORTH);
        add(abas, BorderLayout.CENTER);
    }
}
