/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadecatering;

import java.io.IOException;

/**
 *
 * @author MANUELA
 */
public class Control {

    public void ejecutar() {

        SistemaCatering sistemaCatering = new SistemaCatering();

        boolean seguir;
        try {
            sistemaCatering = sistemaCatering.deSerializar("catering.txt");
//            sistemaCatering = sistemaCatering.deSerializar("cateringDatosCargados.txt");
            seguir = EntradaSalida.leerBoolean("SISTEMA DE CATERING\nDesea ingresar? [s/n]");
        } catch (Exception e) {
            String usuario = EntradaSalida.leerString("Arranque inicial del sistema.\n"
                    + "Sr(a) Administrador(a), ingrese su nombre de usuario:");
            if (usuario.equals("")) {
                throw new NullPointerException("ERROR: El usuario no puede ser nulo.");
            }
            String password = EntradaSalida.leerPassword("Ingrese su password:");
            if (password.equals("")) {
                throw new NullPointerException("ERROR: La password no puede ser nula.");
            }
            sistemaCatering.getPersonas().add(new Administrador(usuario, password));
            try {
                sistemaCatering.serializar("catering.txt");
                EntradaSalida.mostrarString("El arranque ha sido exitoso. Ahora se debe reiniciar el sistema...");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            seguir = false;
        }

        while (seguir) {
            String usuario = EntradaSalida.leerString("Ingrese el usuario:");
            String password = EntradaSalida.leerPassword("Ingrese la password:");

            Persona p = sistemaCatering.buscarPersona(usuario + ":" + password);

            if (p == null) {
                EntradaSalida.mostrarString("ERROR: La combinacion usuario/password ingresada no es valida.");
            } else {
                seguir = p.proceder(sistemaCatering);
            }
        }
    }
}
