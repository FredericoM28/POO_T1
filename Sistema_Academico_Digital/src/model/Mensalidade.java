// model/Mensalidade.java
package model;

import java.io.Serializable;

public class Mensalidade implements Serializable {
    private String mes;
    private double valor;
    private String estado;

    public Mensalidade(String mes, double valor, String estado) {
        this.mes = mes;
        this.valor = valor;
        this.estado = estado;
    }

    public String getMes() { return mes; }
    public void setMes(String mes) { this.mes = mes; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
