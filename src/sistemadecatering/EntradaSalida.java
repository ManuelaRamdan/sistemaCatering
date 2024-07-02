/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadecatering;

/**
 *
 * @author MANUELA
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class EntradaSalida {

    public static int leerEntero(String texto) {
        System.out.println(texto);
        Scanner escaner = new Scanner(System.in);
        int st = escaner.nextInt();
        return st;
    }

    public static char leerChar(String texto) {
        System.out.println(texto);
        Scanner escaner = new Scanner(System.in);
        String st = escaner.nextLine();
        return (st == null || st.length() == 0 ? '0' : st.charAt(0));
    }

    public static String leerString(String texto) {
        System.out.println(texto);
        Scanner escaner = new Scanner(System.in);
        String st = escaner.nextLine();
        st = Verificador.verificarString(st);
        return (st == null ? "" : st);
    }

    public static boolean leerBoolean(String texto) {
        System.out.println(texto);
        Scanner lectura = new Scanner(System.in);
        String i = lectura.nextLine();
        return 'S' == i.charAt(0) || i.charAt(0) == 's';
    }

    public static void mostrarString(String s) {
        System.out.println(s);
    }

    public static String leerPassword(String texto) {

        System.out.println(texto);
        Scanner escaner = new Scanner(System.in);
        String st = escaner.nextLine();

        return st;
    }

    public static LocalDateTime leerFecha() {

        int dia = leerEntero("Ingrese el dia:");
       
        
        int mes = leerEntero("Ingrese el mes:");
        
        int año = leerEntero("Ingrese el año:");
        LocalDate fecha = LocalDate.of(año, mes, dia);

        int hora = leerEntero("Ingrese la hora:");
        int minutos = leerEntero("Ingrese los minutos:");
        LocalTime horaElegida = LocalTime.of(hora, minutos);
        LocalDateTime f = LocalDateTime.of(fecha, horaElegida);

        return f;
    }

    public static Domicilio leerDomicilioEntrega() {
        String calle = EntradaSalida.leerString("Calle de la direccion de la entrega:");
        int altura = EntradaSalida.leerEntero("Altura de la direccion de la entrega:");
        String barrio = EntradaSalida.leerString("Barrio de la direccion de la entrega:");

        Domicilio domicilio = new Domicilio(calle, altura, barrio);

        return domicilio;
    }
}
