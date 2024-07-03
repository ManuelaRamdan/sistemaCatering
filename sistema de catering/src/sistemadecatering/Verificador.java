/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadecatering;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author MANUELA
 */
public class Verificador {

    public static String verificarString(String st) {
        while (st.equals("")) {
            st = EntradaSalida.leerString("Error: Se requiere un valor valido. Ingrese nuevamente:");
        }

        return st;
    }

    public static int verificarTelefono(int numero) {
        while (!validar(numero)) {
            numero = EntradaSalida.leerEntero("Error.Numero de telefono no valido");
        }
        return numero;
    }

    public static boolean validar(int numero) {
        return numero > 1119999999 && numero < 1170000000;
    }

    public static int verificarTelefonoExiste(int numero, SistemaCatering sC) {
        boolean encontro = false;
        for (Persona persona : sC.getPersonas()) {
            if (persona instanceof Cliente) {
                Cliente cliente = (Cliente) persona;
                if (cliente.getTelReferencia() == numero) {
                    encontro = true;
                }
            }
        }

        if (encontro) {
            numero = EntradaSalida.leerEntero("Ya hay un cliente con ese telefono");
            numero = verificarTelefono(numero);

        } else {
            numero = verificarTelefono(numero);
        }

        return numero;

    }

    public static String verificarEmailExiste(String email, SistemaCatering sC) {
        boolean encontro = false;
        for (Persona persona : sC.getPersonas()) {
            if (persona instanceof Cliente) {
                Cliente cliente = (Cliente) persona;
                if (email.equals(cliente.getEmail())) {
                    encontro = true;
                }
            }
        }
        if (encontro) {
            email = EntradaSalida.leerString("Ya hay un cliente con ese mail. Ingrese otro");
            email = verificarString(email);

        }

        return email;
    }

    public static Menu verificarMenu(String nombre, Servicio s) {
        Menu menu = s.buscarMenu(nombre);
        while (menu == null) {
            nombre = EntradaSalida.leerString("Error.Ingrese un valor valido o ingrese el valor en mayuscula:");
            menu = s.buscarMenu(nombre);
        }

        return menu;
    }

    public static Plato verificarPlato(String nombre, Menu m) {
        Plato p = m.buscarPlato(nombre);

        while (p == null) {

            nombre = EntradaSalida.leerString("Error.Ingrese un valor valido o ingrese el valor en mayuscula: ");
            p = m.buscarPlato(nombre);
        }
        return p;
    }

    public static Servicio verificarServicio(SistemaCatering sC, String nombre) {
        Servicio s = sC.buscarServicio(nombre);
        while (s == null) {
            nombre = EntradaSalida.leerString("Error.Ingrese un valor valido o ingrese el valor en mayuscula: ");
            s = sC.buscarServicio(nombre);
        }
        return s;
    }

    public static Cliente verificarCliente(SistemaCatering sC, int codigo) {
        Cliente c = sC.buscarClientePorCodigo(codigo);

        while (c == null) {
            codigo = EntradaSalida.leerEntero("Error. Ingrese un codigo del cliente valido:");
            c = sC.buscarClientePorCodigo(codigo);
        }
        return c;
    }

    public static String verificarSiEstaEnMay(String dato) {
        return dato.toUpperCase();
    }

    public static String verificarSiModoReservaAseptable(String modo) {
        boolean esta = verificarModo(modo);
        
        while(!esta){
            modo = EntradaSalida.leerString("Error.Ingrese un valor valido o ingrese el valor en mayuscula:");
            esta = verificarModo(modo);
        }

        return modo;
    }
    
    public static boolean verificarModo(String modo){
        modo = verificarSiEstaEnMay(modo);
        String[] modos = {"TELEFONO", "MAIL", "PRESENCIAL"};
        boolean esta = false;

        for (String modo1 : modos) {
            if (modo1.equals(modo)) {
                esta = true;
            }
        }
        
        return esta;
    }

    public static LocalDateTime verificarFecha(LocalDateTime fechaElegida) {
        //LocalDateTime hoy = LocalDateTime.now();

        LocalDate fecha = LocalDate.of(2024, 6, 14);

        LocalTime horaElegida = LocalTime.of(16, 00);
        LocalDateTime hoy = LocalDateTime.of(fecha, horaElegida);
        while(fechaElegida.isBefore(hoy)){
            System.out.println("Error.Ingrese una fecha valida:");
            fechaElegida = EntradaSalida.leerFecha();
        }
        
        return fechaElegida;
    }
        
    
    

}
