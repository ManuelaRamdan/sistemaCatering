/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemadecatering;


/**
 *
 * @author MANUELA
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Control c = new Control();
        try {
            c.ejecutar();
        } catch (NullPointerException e) {
            EntradaSalida.mostrarString(e.getMessage());
        }
    }
    
}
