// classe do ficheiro de objecto, e a classe podemos encontrar os ObjectOurputStream e ObjectinputStream
// ficheiros serializados

package controller;

import java.io.*;
import java.util.ArrayList;

public class FicheiroObjeto {
    
    public static <T> void gravar(String caminho, ArrayList<T> lista) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminho))) {
            out.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> carregar(String caminho) {
        File file = new File(caminho);
        if (!file.exists()) return new ArrayList<>();
        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminho))) {
            return (ArrayList<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
