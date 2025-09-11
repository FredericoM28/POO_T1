package controller;

import model.*;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class SistemaController {

    // Array List's principais
    private List<Aluno> alunos = new ArrayList<>();
    private List<Professor> professores = new ArrayList<>();
    private List<Turma> turmas = new ArrayList<>();
    private List<Disciplina> disciplinas = new ArrayList<>();
    private List<Curso> cursos = new ArrayList<>();
    private List<Fatura> faturas = new ArrayList<>();

    // Mensalidade base
    private double mensalidadeBase = 1000.0;

    public SistemaController() {

         File pasta = new File("dados");
            if (!pasta.exists()) {
        pasta.mkdirs(); }


        alunos = FicheiroObjeto.carregar(ARQ_ALUNOS);
        professores = FicheiroObjeto.carregar(ARQ_PROFESSORES);
        turmas = FicheiroObjeto.carregar(ARQ_TURMAS);
        disciplinas = FicheiroObjeto.carregar(ARQ_DISCIPLINAS);
        cursos = FicheiroObjeto.carregar(ARQ_CURSOS);
        faturas = FicheiroObjeto.carregar(ARQ_FATURAS);


        // Pré-carregar cursos e disciplinas
  if (cursos.isEmpty() && disciplinas.isEmpty()) {
        // ------------------ Cursos ------------------
        cursoCriar("INF", "Informática");
        cursoCriar("ECO", "Economia");
        cursoCriar("DIR", "Direito");
        cursoCriar("MAT", "Matemática");
        cursoCriar("GEO", "Geologia");
        cursoCriar("CON", "Contabilidade");
        cursoCriar("GES", "Gestão de Empresas");
        cursoCriar("MED", "Medicina");

        // ------------------ Disciplinas de Informatica ------------------
        disciplinaCriar("POO", "Programação Orientada a Objetos", "INF", "");
        disciplinaCriar("FP", "Fundamentos de Programação", "INF", "");
        disciplinaCriar("ESTB", "Estatística Básica", "INF", "");
        disciplinaCriar("MAT1", "Análise Matemática I", "INF", "");
        disciplinaCriar("MAT2", "Análise Matemática II", "INF", "");
        disciplinaCriar("EDA", "Estruturas de Dados e Algoritmos", "INF", "");
        disciplinaCriar("ADS", "Análise e Desenvolvimento de Sistemas", "INF", "");
        disciplinaCriar("RED", "Redes de Computadores", "INF", "");

        // ------------------ Disciplinas de Economia ------------------
        disciplinaCriar("MIC", "Microeconomia", "ECO", "");
        disciplinaCriar("MAC", "Macroeconomia", "ECO", "");
        disciplinaCriar("EST", "Estatística Econômica", "ECO", "");
        disciplinaCriar("FIN", "Finanças Públicas", "ECO", "");

        // ------------------ Disciplinas de Direito ------------------
        disciplinaCriar("DIRCIV", "Direito Civil", "DIR", "");
        disciplinaCriar("DIRPEN", "Direito Penal", "DIR", "");
        disciplinaCriar("DIRADM", "Direito Administrativo", "DIR", "");
        disciplinaCriar("DIRCON", "Direito Constitucional", "DIR", "");

        // ------------------ Disciplinas de Matemática ------------------
        disciplinaCriar("ALG", "Álgebra Linear", "MAT", "");
        disciplinaCriar("ANA1", "Análise I", "MAT", "");
        disciplinaCriar("ANA2", "Análise II", "MAT", "");
        disciplinaCriar("PROB", "Probabilidade e Estatística", "MAT", "");

        // ------------------ Disciplinas de Geologia ------------------
        disciplinaCriar("MIN", "Mineralogia", "GEO", "");
        disciplinaCriar("PET", "Petrologia", "GEO", "");
        disciplinaCriar("GEOF", "Geofísica", "GEO", "");
        disciplinaCriar("GEOQ", "Geoquímica", "GEO", "");

        // ------------------ Disciplinas de Contabilidade ------------------
        disciplinaCriar("CONT1", "Contabilidade Geral", "CON", "");
        disciplinaCriar("CONT2", "Contabilidade Avançada", "CON", "");
        disciplinaCriar("AUD", "Auditoria", "CON", "");
        disciplinaCriar("FISC", "Fiscalidade", "CON", "");

        // ------------------ Disciplinas de Gestão de Empresas ------------------
        disciplinaCriar("ADM", "Administração", "GES", "");
        disciplinaCriar("MKT", "Marketing", "GES", "");
        disciplinaCriar("RH", "Recursos Humanos", "GES", "");
        disciplinaCriar("FINEMP", "Finanças Empresariais", "GES", "");

        // ------------------ Disciplinas de Medicina ------------------
        disciplinaCriar("ANAT", "Anatomia", "MED", "");
        disciplinaCriar("FISIO", "Fisiologia", "MED", "");
        disciplinaCriar("PAT", "Patologia", "MED", "");
        disciplinaCriar("FAR", "Farmacologia", "MED", "");
        disciplinaCriar("CIR", "Cirurgia", "MED","");
    }

    }

    // 
    private int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }

    private double parseDoubleSafe(String s, double def) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return def; }
    }

    // ---------------------- ALUNO ----------------------
    public void alunoCriar(String matricula, String nome, String codigoCurso, String email, String telefone, String idadeStr) {
        int idade = parseIntSafe(idadeStr, 0);
        Curso curso = cursoBuscar(codigoCurso);
        if (curso == null) {
            curso = new Curso(codigoCurso, codigoCurso, new ArrayList<>());
            cursos.add(curso);
            saveCursos();
        }
        Aluno a = new Aluno();
        a.setMatricula(matricula);
        a.setNome(nome);
        a.setIdade(idade);
        a.setEmail(email);
        a.setTelefone(telefone);
        a.setCurso(curso);
        a.setTipoAluno("NAO_BOLSEIRO");
        a.setDisciplinas(new ArrayList<>());
        a.setNotas(new ArrayList<>());
        alunos.add(a);
        saveAlunos();
    }

    public void alunoAtualizar(String matricula, String nome, String codigoCurso, String email, String telefone, String idadeStr) {
        Aluno a = alunoBuscar(matricula);
        if (a != null) {
            int idade = parseIntSafe(idadeStr, a.getIdade());
            Curso curso = cursoBuscar(codigoCurso);
            if (curso == null) {
                curso = new Curso(codigoCurso, codigoCurso, new ArrayList<>());
                cursos.add(curso);
                saveCursos();
            }
            a.setNome(nome);
            a.setIdade(idade);
            a.setEmail(email);
            a.setTelefone(telefone);
            a.setCurso(curso);
            saveAlunos();
        }
    }

    public void alunoRemover(String matricula) {
        alunos.removeIf(a -> a.getMatricula().equals(matricula));
        saveAlunos();

        faturas.removeIf(f -> f.getMatriculaAluno().equals(matricula));
        saveFaturas();

        for (Turma t : turmas) t.getAlunos().removeIf(al -> al.getMatricula().equals(matricula));
        saveTurmas();
    }

    public Aluno alunoBuscar(String matricula) {
        return alunos.stream().filter(a -> a.getMatricula().equals(matricula)).findFirst().orElse(null);
    }

    public List<Aluno> alunoListar() {
        return new ArrayList<>(alunos);
    }

    public void definirTipoAluno(String matricula, String tipo) {
        Aluno a = alunoBuscar(matricula);
        if (a != null) {
            a.setTipoAluno(tipo);
            saveAlunos();
        }
    }

    // ---------------------- PROFESSOR ----------------------
    public void professorCriar(String id, String nome, String email, String disciplinaPrincipal, String salarioStr) {
        double salario = parseDoubleSafe(salarioStr, 0.0);
        Professor p = new Professor();
        p.setIdProfessor(id);
        p.setNome(nome);
        p.setEmail(email);
        p.setDisciplinaPrincipal(disciplinaPrincipal);
        p.setSalario(salario);
        p.setTurmas(new ArrayList<>());
        professores.add(p);
        saveProfessores();
    }

    public void professorAtualizar(String id, String nome, String email, String disciplinaPrincipal, String salarioStr) {
        Professor p = professorBuscar(id);
        if (p != null) {
            double salario = parseDoubleSafe(salarioStr, p.getSalario());
            p.setNome(nome);
            p.setEmail(email);
            p.setDisciplinaPrincipal(disciplinaPrincipal);
            p.setSalario(salario);
            saveProfessores();
        }
    }

    public void professorRemover(String id) {
        professores.removeIf(p -> p.getIdProfessor().equals(id));
        saveProfessores();

        for (Turma t : turmas) {
            if (t.getProfessor() != null && t.getProfessor().equalsIgnoreCase(id)) t.setProfessor("");
        }
        saveTurmas();
    }

    public Professor professorBuscar(String id) {
        return professores.stream().filter(p -> p.getIdProfessor().equals(id)).findFirst().orElse(null);
    }

    public List<Professor> professorListar() {
        return new ArrayList<>(professores);
    }

    // ---------------------- CURSO ----------------------
    public void cursoCriar(String codigo, String nome) {
        if (cursoBuscar(codigo) == null) {
            cursos.add(new Curso(codigo, nome, new ArrayList<>()));
            saveCursos();
        }
    }

    public Curso cursoBuscar(String codigo) {
        return cursos.stream().filter(c -> c.getCodigo().equals(codigo)).findFirst().orElse(null);
    }

    public List<Curso> cursoListar() {
        return new ArrayList<>(cursos);
    }

    public void cursoRemover(String codigo) {
        cursos.removeIf(c -> c.getCodigo().equals(codigo));
        saveCursos();
    }

    // ---------------------- DISCIPLINA ----------------------
     public void disciplinaCriar(String codigo, String nome, String cursoCodigo, String idProfessor) {
        disciplinas.add(new Disciplina(codigo, nome, cursoCodigo, idProfessor ));
        saveDisciplinas();
    }

    /*public void disciplinaCriar(String codigo, String nome, String cursoCodigo, String idProfessor) {
      Disciplina d = new Disciplina(codigo, nome, cursoCodigo, idProfessor);
      Disciplinas.add(d);
    }*/

    public void disciplinaAtualizar(String codigo, String nome, String cursoCodigo, String idProfessor) {
    for (Disciplina d : disciplinas) {
        if (d.getCodigo().equals(codigo)) {
            d.setNome(nome);
            d.setCodigo(codigo);
            d.setCurso(idProfessor);
            break;
        }
    }
}

    public Disciplina disciplinaBuscar(String codigo) {
        return disciplinas.stream().filter(d -> d.getCodigo().equals(codigo)).findFirst().orElse(null);
    }

    public void disciplinaRemover(String codigo) {
        disciplinas.removeIf(d -> d.getCodigo().equals(codigo));
        saveDisciplinas();
    }

    public List<Disciplina> disciplinaListar() {
        return new ArrayList<>(disciplinas);
    }

    // ---------------------- TURMA ----------------------
    public void turmaCriar(String id, String disciplina, String curso, String professor, String ano, String semestre, String capacidadeStr) {
        int cap = parseIntSafe(capacidadeStr, 0);
        Turma t = new Turma();
        t.setIdTurma(id);
        t.setDisciplina(disciplina);
        t.setCurso(curso);
        t.setProfessor(professor);
        t.setAno(ano);
        t.setSemestre(semestre);
        t.setCapacidade(cap);
        t.setAlunos(new ArrayList<>());
        turmas.add(t);
        saveTurmas();
    }

    public List<Turma> getTurmasProfessor(Professor p) {
        if (p == null) return new ArrayList<>();
        return turmas.stream()
                .filter(t -> t.getProfessor().equals(p.getIdProfessor()))
                .collect(Collectors.toList());
    }

    public void turmaAtualizar(String id, String disciplina, String curso, String professor, String ano, String semestre, String capacidadeStr) {
        int cap = parseIntSafe(capacidadeStr, 0);
        Turma t = turmaBuscar(id);
        if (t != null) {
            t.setDisciplina(disciplina);
            t.setCurso(curso);
            t.setProfessor(professor);
            t.setAno(ano);
            t.setSemestre(semestre);
            t.setCapacidade(cap);
            saveTurmas();
        }
    }

    public void turmaRemover(String id) {
        turmas.removeIf(t -> t.getIdTurma().equals(id));
        saveTurmas();
    }

    public Turma turmaBuscar(String id) {
        return turmas.stream().filter(t -> t.getIdTurma().equals(id)).findFirst().orElse(null);
    }

    public List<Turma> turmaListar() {
        return new ArrayList<>(turmas);
    }

    public void turmaMatricularAluno(String idTurma, String matriculaAluno) {
        Turma t = turmaBuscar(idTurma);
        Aluno a = alunoBuscar(matriculaAluno);
        if (t != null && a != null && !t.getAlunos().contains(a)) {
            t.getAlunos().add(a);
            saveTurmas();
        }
    }

    public void turmaRemoverAluno(String idTurma, String matriculaAluno) {
        Turma t = turmaBuscar(idTurma);
        if (t != null) {
            t.getAlunos().removeIf(a -> a.getMatricula().equals(matriculaAluno));
            saveTurmas();
        }
    }

    // ---------------------- MATRICULAS  ----------------------
    public List<Matricula> listarMatriculasAluno(String matricula) {
        List<Matricula> lista = new ArrayList<>();
        for (Turma t : turmas) {
            boolean presente = t.getAlunos().stream().anyMatch(a -> a.getMatricula().equals(matricula));
            if (presente) lista.add(new Matricula(t.getIdTurma(), t.getDisciplina(), t.getAno(), t.getSemestre(), "Matriculado"));
        }
        return lista;
    }

    // ---------------------- FATURAS ----------------------
    public void gerarFatura(String matricula, String mes, double valor) {
        Fatura f = new Fatura();
        f.setIdFatura(UUID.randomUUID().toString());
        f.setMatriculaAluno(matricula);
        f.setMes(mes);
        f.setValor(valor);
        f.setEstado("Pendente");
        faturas.add(f);
        saveFaturas();
    }

    public List<Fatura> getMensalidades(Aluno a) {
        if (a == null) return new ArrayList<>();
        return faturas.stream()
                .filter(f -> f.getMatriculaAluno().equals(a.getMatricula()))
                .collect(Collectors.toList());
    }

    public void registrarPagamento(String idFatura) {
        Fatura f = faturas.stream().filter(ff -> ff.getIdFatura().equals(idFatura)).findFirst().orElse(null);
        if (f != null) {
            f.setEstado("Pago");
            saveFaturas();
        }
    }

    public List<Fatura> listarFaturasDoAluno(String matricula) {
        return faturas.stream().filter(f -> f.getMatriculaAluno().equals(matricula)).collect(Collectors.toList());
    }

    // Gerar fatura de acordo com as regras de bolseiro/não-bolseiro
public void gerarFaturaAluno(Aluno aluno, String mes, double valorBase) {
    double notaFinal = calcularNotaFinal(aluno);
    double valorAPagar;

    if (aluno.getTipoAluno().equalsIgnoreCase("BOLSEIRO")) {
        if (notaFinal >= 16) {
            valorAPagar = 0; // isento
        } else {
            valorAPagar = valorBase / 2; // paga metade
        }
    } else { // NÃO-BOLSEIRO
        valorAPagar = valorBase; // paga sempre
    }

    gerarFatura(aluno.getMatricula(), mes, valorAPagar);
}

// Gerar faturas para todos os alunos
public void gerarFaturasTodos(String mes, double valorBase) {
    for (Aluno a : alunos) {
        gerarFaturaAluno(a, mes, valorBase);
    }
}

// Relatório recursivo de faturas
public String relatorioFaturas(List<Aluno> lista, int i) {
    if (i >= lista.size()) return "";
    Aluno a = lista.get(i);
    StringBuilder sb = new StringBuilder();
    sb.append("Aluno: ").append(a.getNome()).append(" (").append(a.getMatricula()).append(")\n");
    List<Fatura> faturasAluno = listarFaturasDoAluno(a.getMatricula());
    for (Fatura f : faturasAluno) {
        sb.append("  - ").append(f.getMes())
          .append(" | Valor: ").append(f.getValor())
          .append(" | Estado: ").append(f.getEstado()).append("\n");
    }
    sb.append("\n");
    return sb.toString() + relatorioFaturas(lista, i + 1);
}

public void gerarMensalidade(Aluno a, String mes, double valor) {
    gerarFatura(a.getMatricula(), mes, valor);
}

public List<Fatura> listarMensalidadesDoAluno(Aluno a) {
    return getMensalidades(a);
}


// Calcular nota final de um aluno
private double calcularNotaFinal(Aluno aluno) {
    if (aluno.getNotas() == null || aluno.getNotas().isEmpty()) return 0;
    double soma = 0;
    for (Nota n : aluno.getNotas()) {
        soma += n.getValor();
    }
    return soma / aluno.getNotas().size();
}

// Listar notas de um aluno
public List<Nota> listarNotasAluno(Aluno aluno) {
    return aluno.getNotas();
}

// Registrar ou atualizar nota
public void registrarNota(Aluno aluno, String disciplina, double valor) {
    if (aluno == null || disciplina == null) return;

    Nota existente = aluno.getNotas().stream()
            .filter(n -> n.getDisciplina().equalsIgnoreCase(disciplina))
            .findFirst()
            .orElse(null);

    if (existente != null) {
        existente.setValor(valor);
    } else {
        aluno.getNotas().add(new Nota(disciplina, valor));
    }
    saveAlunos();
}

// Calcular média final do aluno
public double calcularMediaFinal(Aluno aluno) {
    if (aluno == null || aluno.getNotas().isEmpty()) return 0.0;
    return aluno.getNotas().stream().mapToDouble(Nota::getValor).average().orElse(0.0);
}

// Verificar dispensa acadêmica e financeira
public String verificarSituacaoAluno(Aluno aluno) {
    if (aluno == null) return "Aluno inválido";

    double media = calcularMediaFinal(aluno);
    String tipo = aluno.getTipoAluno();

    if ("BOLSEIRO".equalsIgnoreCase(tipo)) {
        if (media >= 17) return "Dispensado (Académico + Financeiro)";
        else if (media >= 10) return "Aprovado (Paga metade da mensalidade)";
        else return "Reprovado";
    } else { // NÃO-BOLSEIRO
        if (media >= 15) return "Dispensado Académico (Mas paga mensalidade)";
        else if (media >= 10) return "Aprovado (Paga mensalidade)";
        else return "Reprovado";
    }
}



    // ---------------------- RELATÓRIOS ----------------------
    public String gerarRelatorio(String tipo) {
        StringBuilder sb = new StringBuilder();
        switch (tipo.toLowerCase()) {
            case "alunos por curso":
                for (Curso c : cursos) {
                    sb.append("Curso: ").append(c.getNome()).append("\n");
                    for (Aluno a : alunos) if (a.getCurso() != null && a.getCurso().getCodigo().equals(c.getCodigo()))
                        sb.append("  ").append(a.getMatricula()).append(" - ").append(a.getNome()).append("\n");
                }
                break;
            case "professores":
                for (Professor p : professores) sb.append(p.getIdProfessor()).append(" - ").append(p.getNome()).append(" - ").append(p.getDisciplinaPrincipal()).append("\n");
                break;
            case "turmas":
                for (Turma t : turmas) sb.append(t.getIdTurma()).append(" - ").append(t.getDisciplina()).append(" - ").append(t.getCurso()).append(" - ").append(t.getProfessor()).append("\n");
                break;
            default: sb.append("Tipo de relatório inválido!");
        }
        return sb.toString();
    }

    // ---------------------- LOGIN ----------------------
    public Aluno loginAluno(String email, String senha) {
    return alunos.stream()
            .filter(a -> a.getEmail() != null && a.getEmail().equalsIgnoreCase(email) &&
                    a.getSenha() != null && a.getSenha().equals(senha))
            .findFirst()
            .orElse(null);
}

public Professor loginProfessor(String email, String senhaProf) {
    return professores.stream()
            .filter(p -> p.getEmail() != null && p.getEmail().equalsIgnoreCase(email) &&
                    p.getSenhaProf() != null && p.getSenhaProf().equals(senhaProf))
            .findFirst()
            .orElse(null);
}

    
    public Professor professorLogin(String email, String senha) { return loginProfessor(email, senha);
        
     }

    public boolean loginGestaoAcademica(String email, String senha) {
        return "admin@sistema.com".equalsIgnoreCase(email) && "admin123".equals(senha);
    }
    public boolean gestaoLogin(String email, String senha) { return loginGestaoAcademica(email, senha); }

    // metodos para  NOTAS 
    public void salvarNotas(JTable tabela, Professor professor) {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        int linhas = modelo.getRowCount();

        for (int i = 0; i < linhas; i++) {
            String alunoNome = (String) modelo.getValueAt(i, 0);
            String disciplina = (String) modelo.getValueAt(i, 1);
            Double valor = 0.0;
            try {
                valor = Double.parseDouble(modelo.getValueAt(i, 2).toString());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido na linha " + i);
            }

            Aluno aluno = alunos.stream().filter(a -> a.getNome().equals(alunoNome)).findFirst().orElse(null);
            if (aluno != null) {
                boolean notaAtualizada = false;
                for (Nota n : aluno.getNotas()) {
                    if (n.getDisciplina().equals(disciplina)) {
                        n.setValor(valor);
                        notaAtualizada = true;
                        break;
                    }
                }
                if (!notaAtualizada) {
                    aluno.getNotas().add(new Nota(disciplina, valor));
                }
            }
        }
        saveAlunos();
        JOptionPane.showMessageDialog(null, "Notas salvas com sucesso!");
    }

    public List<Nota> getNotas(Aluno a) {
        if (a == null) return new ArrayList<>();
        return a.getNotas();
    }


    // actualiazar discipliina
    

    // ---------------------- PERSISTÊNCIA ----------------------
    private static final String ARQ_ALUNOS = "dados/alunos.dat";
    private static final String ARQ_PROFESSORES = "dados/professores.dat";
    private static final String ARQ_TURMAS = "dados/turmas.dat";
    private static final String ARQ_DISCIPLINAS = "dados/disciplinas.dat";
    private static final String ARQ_CURSOS = "dados/cursos.dat";
    private static final String ARQ_FATURAS = "dados/faturas.dat";

    private void saveAlunos()      { FicheiroObjeto.gravar(ARQ_ALUNOS,      new ArrayList<>(alunos)); }
    private void saveProfessores() { FicheiroObjeto.gravar(ARQ_PROFESSORES, new ArrayList<>(professores)); }
    private void saveTurmas()      { FicheiroObjeto.gravar(ARQ_TURMAS,      new ArrayList<>(turmas)); }
    private void saveDisciplinas() { FicheiroObjeto.gravar(ARQ_DISCIPLINAS, new ArrayList<>(disciplinas)); }
    private void saveCursos()      { FicheiroObjeto.gravar(ARQ_CURSOS,      new ArrayList<>(cursos)); }
    private void saveFaturas()     { FicheiroObjeto.gravar(ARQ_FATURAS,     new ArrayList<>(faturas)); }

    public Curso[] getCurso() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurso'");
    }

}
