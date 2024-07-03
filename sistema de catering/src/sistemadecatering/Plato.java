/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadecatering;

import java.io.Serializable;

/**
 *
 * @author MANUELA
 */
class Plato implements Serializable {

    private String nombre;

    public Plato() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void mostrar() {
        System.out.println("\t -" + nombre);
    }

    boolean coincidenNomPlato(String nombrePlato) {
        return nombre.equals(nombrePlato);
    }

}
