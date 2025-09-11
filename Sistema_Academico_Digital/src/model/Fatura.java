package model;

import java.io.Serializable;

public class Fatura implements Serializable{
    
    private String idFatura;
    private String matriculaAluno;
    private String mes;
    private double valor;
    private String estado; // "Pago", "Pendente"

    
    public Fatura(String idFatura, String matriculaAluno, String mes, double valor, String estado) {
        this.idFatura = idFatura;
        this.matriculaAluno = matriculaAluno;
        this.mes = mes;
        this.valor = valor;
        this.estado = estado;
    }


    public Fatura() {
        
    }


    public String getIdFatura() {
        return idFatura;
    }


    public void setIdFatura(String idFatura) {
        this.idFatura = idFatura;
    }


    public String getMatriculaAluno() {
        return matriculaAluno;
    }


    public void setMatriculaAluno(String matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }


    public String getMes() {
        return mes;
    }


    public void setMes(String mes) {
        this.mes = mes;
    }


    public double getValor() {
        return valor;
    }


    public void setValor(double valor) {
        this.valor = valor;
    }


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }


    
}
