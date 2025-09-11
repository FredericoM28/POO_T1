package model;

import java.io.Serializable;

public class Nota implements Serializable {
    private String disciplina;
    private double valor;

    public Nota(String disciplina, double valor) {
        this.disciplina = disciplina;
        this.valor = valor;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
