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
class Servicio implements Serializable {

    private ArrayList<Menu> menus;
    private String nombreServicio;

    Servicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
        this.menus = new ArrayList<Menu>();
    }

    void agregarMenu(Menu menu) {
        menus.add(menu);
    }

    public void mostrar() {
        System.out.println("Nombre del servicio:" + nombreServicio + "\n");
        for (int i = 0; i < menus.size(); i++) {
            menus.get(i).mostrar();
        }
        System.out.println("\n");
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public Menu buscarMenu(String datos) {
        for (Menu m : menus) {
            if (m.coincidenNomMenu(datos)) {
                return m;
            }
        }
        return null;
    }

    boolean coincidenNomServicio(String nombre) {
        return nombreServicio.equals(nombre);
    }

}
