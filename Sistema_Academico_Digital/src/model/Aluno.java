package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa implements Serializable {

    private String matricula;
    private Curso curso;
    private String tipoAluno; // "BOLSEIRO" ou "NAO_BOLSEIRO"
    private List<Disciplina> disciplinas;
    private List<Nota> notas; 
    private String senha;




    // Construtor vazio
    public Aluno() {
        super("", 0, "", "", "", ""); // chama os atribiitos e metodos da classe pessoa
        this.matricula = "";
        this.curso = null;
        this.tipoAluno = "NAO_BOLSEIRO";
        this.disciplinas = new ArrayList<>();
        this.notas = new ArrayList<>(); //  inicializa a lista de Notas
        
    }
    

    // Construtor completo
    public Aluno(String nome, int idade, String numeroDocumento, String morada,
                 String email, String telefone, String matricula,
                 Curso curso, String tipoAluno, List<Disciplina> disciplinas, List<Nota> notas, String senha) {
        super(nome, idade, numeroDocumento, morada, email, telefone);
        this.matricula = matricula;
        this.curso = curso;
        this.tipoAluno = tipoAluno;
        this.disciplinas = (disciplinas != null) ? disciplinas : new ArrayList<>();
        this.notas = (notas != null) ? notas : new ArrayList<>(); //  lista de Notas
        this.senha = "Estudante0000";
    }
    //github today

    // Getters e Setters


    
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public String getTipoAluno() { return tipoAluno; }
    public void setTipoAluno(String tipoAluno) { this.tipoAluno = tipoAluno; }

    public List<Disciplina> getDisciplinas() { return disciplinas; }
    public void setDisciplinas(List<Disciplina> disciplinas) { this.disciplinas = disciplinas; }

    public List<Nota> getNotas() { return notas; }
    public void setNotas(List<Nota> notas) { this.notas = notas; }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
