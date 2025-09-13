package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Professor extends Pessoa implements Serializable {

    private String idProfessor;
    private String disciplinaPrincipal;
    private double salario;
    private List<Turma> turmas;
    private String senhaProf;

   public String getSenhaProf() { return senhaProf; }
  public void setSenhaProf(String senhaProf) { this.senhaProf = senhaProf; }


    // Construtor vazio
    public Professor() {
        super("", 0, "", "", "", ""); // chama explicitamente o construtor de Pessoa
        this.idProfessor = "";
        this.disciplinaPrincipal = "";
        this.salario = 0.0;
        this.turmas = new ArrayList<>();
        this.senhaProf = "Professor0000";
    }

    // Construtor completo
    public Professor(String nome, int idade, String numeroDocumento, String morada,
                     String email, String telefone, String idProfessor,
                     String disciplinaPrincipal, double salario, List<Turma> turmas, String senhaProf) {
        super(nome, idade, numeroDocumento, morada, email, telefone);
        this.idProfessor = idProfessor;
        this.disciplinaPrincipal = disciplinaPrincipal;
        this.salario = salario;
        this.turmas = (turmas != null) ? turmas : new ArrayList<>();
        this.senhaProf = senhaProf;
    }

    // Getters e Setters

    
    public String getIdProfessor() { return idProfessor; }
    @Override
    public String toString() {
        return "Professor [idProfessor=" + idProfessor + ", disciplinaPrincipal=" + disciplinaPrincipal + ", salario="
                + salario + ", turmas=" + turmas + ", senhaProf=" + senhaProf + ", getTurmas()=" + getTurmas()
                + ", toString()=" + super.toString() + "]";
    }
    public void setIdProfessor(String idProfessor) { this.idProfessor = idProfessor; }

    public String getDisciplinaPrincipal() { return disciplinaPrincipal; }
    public void setDisciplinaPrincipal(String disciplinaPrincipal) { this.disciplinaPrincipal = disciplinaPrincipal; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    public List<Turma> getTurmas() { return turmas; }
    public void setTurmas(List<Turma> turmas) { this.turmas = turmas; }
}
