/*
 * Esta e a classe pessoa, ela e' a classe Mae onde varias outras classes Herdam dela
 * Esta classe contem Atributos, costrutores e Gettter's e Setter's
 * As classes herdeiras sao : Classe "Aluno" e classe "Professor"
 * 
 */
package model;

import java.io.Serializable;

public class Pessoa implements Serializable {

    //atributos na classe "pessoa""
    private String nome;
    private int idade;
    private String numeroDocumento;
    private String morada;
    private String email;
    private String telefone;

    // construtor da classe
    public Pessoa(String nome, int idade, String numeroDocumento, String morada, String email, String telefone) {
        this.nome = nome;
        this.idade = idade;
        this.numeroDocumento = numeroDocumento;
        this.morada = morada;
        this.email = email;
        this.telefone = telefone;
    }

    //Gettter's e Setter's da classe "Pessoa"

    public String getNome() { //Serve acessar o nome, entao usamos ela quando queremos cmar tudo que tem na variavela se r usada
        return nome;
    }


    public void setNome(String nome) { //
        this.nome = nome;
    }


    public int getIdade() { // para obter a idade tanto do Estudante e como do Professor, pois sao eles que herdam de pessoa
        return idade;
    }


    public void setIdade(int idade) {
        this.idade = idade;
    }


    public String getNumeroDocumento() {
        return numeroDocumento;
    }


    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }


    public String getMorada() {
        return morada;
    }


    public void setMorada(String morada) {
        this.morada = morada;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getTelefone() {
        return telefone;
    }


    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    
    
}
