package model;

import java.io.Serializable;
import java.util.List;

public class Turma  implements Serializable{
    private String idTurma;
    private String disciplina;
    private String curso;
    private String professor;
    private String ano;
    private String semestre;
    private int capacidade;
    private List<Aluno> alunos;

    
    public Turma(String idTurma, String disciplina, String curso, String professor, String ano, String semestre,
            int capacidade, List<Aluno> alunos) {
        this.idTurma = idTurma;
        this.disciplina = disciplina;
        this.curso = curso;
        this.professor = professor;
        this.ano = ano;
        this.semestre = semestre;
        this.capacidade = capacidade;
        this.alunos = alunos;
    }


    @Override
    public String toString() {
        return "Turma [idTurma=" + idTurma + ", disciplina=" + disciplina + ", curso=" + curso + ", professor="
                + professor + ", ano=" + ano + ", semestre=" + semestre + ", capacidade=" + capacidade + ", alunos="
                + alunos + ", getIdTurma()=" + getIdTurma() + ", getDisciplina()=" + getDisciplina() + ", getCurso()="
                + getCurso() + ", getProfessor()=" + getProfessor() + ", getAno()=" + getAno() + ", getSemestre()="
                + getSemestre() + ", getCapacidade()=" + getCapacidade() + ", getAlunos()=" + getAlunos()
                + ", toString()=" + super.toString() + "]";
    }


    public Turma() {
        
    }


    public String getIdTurma() {
        return idTurma;
    }


    public void setIdTurma(String idTurma) {
        this.idTurma = idTurma;
    }


    public String getDisciplina() {
        return disciplina;
    }


    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }


    public String getCurso() {
        return curso;
    }


    public void setCurso(String curso) {
        this.curso = curso;
    }


    public String getProfessor() {
        return professor;
    }


    public void setProfessor(String professor) {
        this.professor = professor;
    }


    public String getAno() {
        return ano;
    }


    public void setAno(String ano) {
        this.ano = ano;
    }


    public String getSemestre() {
        return semestre;
    }


    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }


    public int getCapacidade() {
        return capacidade;
    }


    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }


    public List<Aluno> getAlunos() {
        return alunos;
    }


    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    

}
