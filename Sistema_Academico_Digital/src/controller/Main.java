package controller;

import view.TelaLogin;
import controller.SistemaController;



//Para que os Docentes tenham acesso ao sistema como admin da gestao academica deve-se introduzir credencias
// sera pelo "email" e pela "senha" 

/*
 *___Meus dados___

 * Nome:      Frederico Antonio MAdabula
 * regime:    Pos-Laboral
 * Cadeira:   POO
 * Avaliacao: Teste 1
 * 
 */


//EMAIL "admin@sistema.com"
//SENHA: "admin123"



public class Main {
    public static void main(String[] args) {
        // Cria o controller (com dados internos de teste)
        SistemaController controller = new SistemaController();

        // Instancia a TelaLogin e mostra
        new TelaLogin(controller); 
    }
}
