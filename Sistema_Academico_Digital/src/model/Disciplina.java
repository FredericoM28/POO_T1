package model;

import java.io.Serializable;

public class Disciplina implements Serializable {
    

    private String codigo;
    private String nome;
    private String curso;  // curso associado
    private String idProfessor;

    public Disciplina(String codigo, String nome, String curso, String idProfessor) {
        this.codigo = codigo;
        this.nome = nome;
        this.curso = curso;
        this.idProfessor = idProfessor;
    }
    
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }


    
}
