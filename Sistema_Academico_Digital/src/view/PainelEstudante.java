package view;

import controller.SistemaController;
import model.Aluno;
import model.Fatura;       
import model.Nota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PainelEstudante extends JPanel {

    private JTable tabelaMensalidades, tabelaNotas;

    public PainelEstudante(SistemaController controller, Aluno aluno){
        setLayout(new BorderLayout(10,10));
        setBackground(new Color(245,247,250));

        // Painel de perfil
        JPanel painelPerfil = new JPanel(new GridLayout(5,2,10,10));
        painelPerfil.setBorder(BorderFactory.createTitledBorder("Dados Pessoais"));
        painelPerfil.setBackground(new Color(245,247,250));

        painelPerfil.add(new JLabel("Nome:")); 
        painelPerfil.add(campo(aluno.getNome()));

        painelPerfil.add(new JLabel("Idade:")); 
        painelPerfil.add(campo(String.valueOf(aluno.getIdade())));

        painelPerfil.add(new JLabel("Curso:")); 
        painelPerfil.add(campo(aluno.getCurso() != null ? aluno.getCurso().getNome() : ""));

        painelPerfil.add(new JLabel("Matrícula:")); 
        painelPerfil.add(campo(aluno.getMatricula()));

        painelPerfil.add(new JLabel("Email:")); 
        painelPerfil.add(campo(aluno.getEmail()));

        // Abas
        JTabbedPane abas = new JTabbedPane();

        // Tabela de Mensalidades
        DefaultTableModel modelMens = new DefaultTableModel(new String[]{"Mês","Valor","Estado"},0);
        tabelaMensalidades = new JTable(modelMens);

        for(Fatura f : controller.getMensalidades(aluno)){  // usa Fatura se o controller retornar List<Fatura>
            modelMens.addRow(new Object[]{f.getMes(), f.getValor(), f.getEstado()});
        }
        abas.addTab("Mensalidades", new JScrollPane(tabelaMensalidades));

        // Tabela de Notas
        DefaultTableModel modelNotas = new DefaultTableModel(new String[]{"Disciplina","Nota"},0);
        tabelaNotas = new JTable(modelNotas);

        for(Nota n : controller.getNotas(aluno)){
            modelNotas.addRow(new Object[]{n.getDisciplina(), n.getValor()});
        }
        abas.addTab("Notas", new JScrollPane(tabelaNotas));

        add(painelPerfil, BorderLayout.NORTH);
        add(abas, BorderLayout.CENTER);
    }

    private JTextField campo(String texto){
        JTextField t = new JTextField(texto);
        t.setEditable(false);
        return t;
    }
}
