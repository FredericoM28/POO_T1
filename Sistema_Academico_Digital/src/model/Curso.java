package model;

import java.io.Serializable;
import java.util.List;

public class Curso  implements Serializable{
    
    private String codigo;
    private String nome;
    private List<Disciplina> disciplinas;

    public Curso(String codigo, String nome, List<Disciplina> disciplinas) {
        this.codigo = codigo;
        this.nome = nome;
        this.disciplinas = disciplinas;
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
    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }
    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }


}
