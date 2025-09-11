package model;

import java.io.Serializable;

public class Matricula implements Serializable {
    private String idTurma;
    private String disciplina;
    private String ano;
    private String semestre;
    private String estado; // "Matriculado", "Cancelado"

    
    public Matricula(String idTurma, String disciplina, String ano, String semestre, String estado) {
        this.idTurma = idTurma;
        this.disciplina = disciplina;
        this.ano = ano;
        this.semestre = semestre;
        this.estado = estado;
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


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }


    
}
