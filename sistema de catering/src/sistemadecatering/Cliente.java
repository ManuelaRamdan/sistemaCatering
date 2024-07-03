/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadecatering;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author MANUELA
 */
public class Cliente extends Persona implements Serializable {

    private int codCliente;
    private String nombre;
    private String apellido;
    private int telReferencia;
    private String email;
    private ArrayList<Reserva> reservas;

    public Cliente(String u, String p, int codCliente, String nombre, String apellido, int telReferencia, String email) {
        setUsuario(u);
        setPassword(p);
        this.codCliente = codCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telReferencia = telReferencia;
        this.email = email;
        reservas = new ArrayList<Reserva>();

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelReferencia() {
        return telReferencia;
    }

    public void setTelReferencia(int telReferencia) {
        this.telReferencia = telReferencia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    void agregarReserva(Reserva r) {
        reservas.add(r);
    }

    // ver
    @Override
    public boolean proceder(SistemaCatering sistemaCatering) {
        char opc = 0;
        boolean seguir = true;

        do {
            System.out.println("--- SISTEMA DE CATERING ---");
            opc = EntradaSalida.leerChar(
                    "OPCIONES DEL CLIENTE\n"
                    + "[1] Mostrar sus datos\n"
                    + "[2] Mostrar sus reservas\n"
                    + "[3]  Salir de este menu\n" + "opc: ");
            switch (opc) {
                case '1':// Mostrar sus datos
                    mostrar();
                    break;

                case '2':// Mostrar sus reserva
                    Reserva r = sistemaCatering.buscarReserva(codCliente);
                    if (r == null) {
                        System.out.println("No tiene reservas");
                    } else {
                        r.mostrar();
                    }

                    break;
                case '3'://salir del menu

                    seguir = true;
                    break;

                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';
            }

        } while (opc != '3');

        return seguir;
    }

    @Override
    public void mostrar() {
        System.out.println("Cliente - codigo :" + codCliente + "\n"
                + "- Nombre:" + nombre + "\n"
                + "- Apellido:" + apellido + "\n"
                + "- Telefono de referencia:" + telReferencia + "\n"
                + "- Email:" + email + "\n"
                + "- Usuarrio:" + this.getUsuario() + "\n"
                + "- Contrasena:" + this.getPassword() + "\n"
        );

        System.out.println("Reservas:");
        if (reservas.size() == 0) {
            EntradaSalida.mostrarString("No tiene reservas");
        } else {
            for (Reserva r : reservas) {
                r.mostrar();
            }
        }

    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    boolean coincideCodigoCliente(int codigo) {
        return codCliente == codigo;
    }

}
