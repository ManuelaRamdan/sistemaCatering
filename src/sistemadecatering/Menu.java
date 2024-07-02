/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadecatering;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author MANUELA
 */
class Menu implements Serializable {

    private ArrayList<Plato> platosPrincipal;
    private String nombreMenu;
    private int precio;
    private ArrayList<Plato> platosEntrada;

    public Menu() {
        platosPrincipal=new ArrayList<Plato>();
        platosEntrada=new ArrayList<Plato>();
    }

    void agregarPlato(Plato plato, char opc) {//p es plato principal y e es plato de entrada

        if (opc == 'p' || opc == 'P') {
            platosPrincipal.add(plato);
        } else if (opc == 'e' || opc == 'E') {
            platosEntrada.add(plato);
        }
    }

    public void mostrar() {
        System.out.println("Nombre del menu: " + nombreMenu);
        System.out.println("Entrada:");
        for (int i = 0; i < platosEntrada.size(); i++) {
            platosEntrada.get(i).mostrar();
        }
        System.out.println("Plato Principal:");
        for (int i = 0; i < platosPrincipal.size(); i++) {
            platosPrincipal.get(i).mostrar();
        }

        System.out.println("Precio por persona: $" + precio + "\n");
    }
    
    public void mostrarPlato(){
        System.out.println("Entrada:");
        for (Plato entrada: platosEntrada) {
            entrada.mostrar();
        }
        System.out.println("Plato Principal:");
        for(Plato principal: platosPrincipal){
            principal.mostrar();
        }
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public ArrayList<Plato> getPlatosPrincipal() {
        return platosPrincipal;
    }

    public void setPlatosPrincipal(ArrayList<Plato> platos) {
        this.platosPrincipal = platos;
    }

    public ArrayList<Plato> getPlatosEntrada() {
        return platosPrincipal;
    }

    public void setPlatosEntrada(ArrayList<Plato> platos) {
        this.platosPrincipal = platos;
    }

    public String getNombreMenu() {
        return nombreMenu;
    }

    public void setNombreMenu(String nombreMenu) {
        this.nombreMenu = nombreMenu;
    }

    public Plato buscarPlato(String datos) {

        for (Plato pE : platosEntrada) {
            if (pE.coincidenNomPlato(datos)) {
                return pE;
            }
        }

        for (Plato pP : platosPrincipal) {
            if (pP.coincidenNomPlato(datos)) {
                return pP;
            }
        }
        return null;
    }

    boolean coincidenNomMenu(String nombre) {
        return nombreMenu.equals(nombre);
    }
}
