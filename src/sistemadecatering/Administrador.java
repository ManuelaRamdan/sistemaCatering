/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadecatering;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


/**
 *
 * @author MANUELA
 */
public class Administrador extends Persona implements Serializable {

    public Administrador(String u, String p) {
        setUsuario(u);
        setPassword(p);
    }

    @Override
    public boolean proceder(SistemaCatering sistemaCatering) {
        char opc = 0;
        boolean seguir = true;
        ArrayList<Servicio> ListadoServicio = sistemaCatering.getServicios();
        do {
            System.out.println("--- SISTEMA CATERING ---");
            opc = EntradaSalida.leerChar(
                    "OPCIONES DEL ADMINISTRADOR\n"
                    + "[1] Dar alta, baja o modificar un servicio\n"
                    + "[2] Dar blta, baja o modificar un coordinador\n"
                    + "[3] Dar alta, baja o modificar un cliente\n"
                    + "[4] Mostrar el contenido del sistema\n"
                    + "[5] Salir de este menu\n"
                    + "[6] Salir del sistema\n" + "opc: ");
            GestorAbm abm = new GestorAbm();
            switch (opc) {
                case '1':// ABM servicio
                    
                    abm.abmServicio(ListadoServicio, sistemaCatering);
                    

                    break;

                case '2':// ABM coordinador
                    abmCoordinador(sistemaCatering);

                    break;
                case '3':// ABM cliente
                    abm.abmCliente(sistemaCatering);
//                    abmCliente(sistemaCatering);
                    break;
                case '4':// mostrar los datos del sistema
                    System.out.println("\n ------------------------------------");
                    System.out.println("Servicios\n");
                    ArrayList<Servicio> vecServicios = sistemaCatering.getServicios();
                    for (int i = 0; i < vecServicios.size(); i++) {
                        vecServicios.get(i).mostrar();
                        System.out.println("----------------------------------\n");
                    }
                    System.out.println("\n----------------------------------------------\n");
                    System.out.println("Personas\n");
                    ArrayList<Persona> vecPersonas = sistemaCatering.getPersonas();
                    for (int i = 0; i < vecPersonas.size(); i++) {
                        vecPersonas.get(i).mostrar();
                    }

                case '5':// eliminar algun servicio
                    seguir = true;
                    break;
                case '6':// 
                    seguir = false;
                    break;
                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';
            }
            if (opc >= '1' && opc <= '4') {
                try {
                    sistemaCatering.serializar("catering.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } //ver

        } while (opc != '5' && opc != '6');

        return seguir;
    }

    @Override
    public void mostrar() {
        System.out.println("Administrador - Usuario: " + this.getUsuario());
        System.out.println("Password: " + this.getPassword());
    }
//----------------------------------------------------------------------------

//-------------------------------------------------------------------------------------
// ------------------------ abm coordinador -----------------------------------------
    private void abmCoordinador(SistemaCatering sistemaCatering) {
        boolean seguirOperando = true;
        while (seguirOperando) {

            char opc = EntradaSalida.leerChar(
                    "[1] Dar de alta a un coordinador\n"
                    + "[2] Dar de baja a un coordinador\n"
                    + "[3] Modificar a un coordinador \n" + "opc: ");

            switch (opc) {
                case '1':// dar de alta
                    altaCoordinador(sistemaCatering);

                    break;

                case '2':// dar de baja
                    bajaCoordinador(sistemaCatering);
                    break;
                case '3'://modificar
                    modificarCoordinador(sistemaCatering);
                    break;

                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';
            }

            seguirOperando = EntradaSalida.leerBoolean("Desea dar de alta, baja o modificar otro coordinador[s/n]?");
        }

    }
//--------------------- alta coordinador -------------------------------------

    private int calcCantCoordi(SistemaCatering sC) {
        int cont = 0;
        for (int i = 0; i < sC.getPersonas().size(); i++) {
            Persona persona = sC.getPersonas().get(i);
            if (persona instanceof Coordinador) {
                cont++;
            }
        }

        return cont;
    }
    
    private void altaCoordinador(SistemaCatering sC) {
        int cantCoordinadores = 0;
        boolean seguirDandoAlta = true;
        GestorAbm abm = new GestorAbm();
        while (seguirDandoAlta) {
            cantCoordinadores = calcCantCoordi(sC);

            String usuario = abm.crearUsuario();
            String contra = abm.crearContra();
            Persona p = sC.buscarPersona(usuario + ":" + contra);
            if (p != null) {
                EntradaSalida.mostrarString("ERROR: El usuario ya figura en el sistema");
            } else {
                p = new Coordinador(usuario, contra, sC.buscarUltimoCodCoordi() + 1);
                sC.getPersonas().add(p);
                EntradaSalida.mostrarString("Se incorporo el coordinador al sistema de manera exitosa");
            }

            seguirDandoAlta = EntradaSalida.leerBoolean("Queres dar de alta otro Coordinador? [s/n]");
        }

    }

    private void mostrarCoordinador(SistemaCatering sC) {
        for (int i = 0; i < sC.getPersonas().size(); i++) {
            Persona persona = sC.getPersonas().get(i);
            if (persona instanceof Coordinador) {
                Coordinador coordinador = (Coordinador) persona;
                coordinador.mostrar();
            }
        }
    }
//------------------------ baja coordinador -------------------------------------

    private void bajaCoordinador(SistemaCatering sC) {
        boolean seguirOperando = true;
        int cantCoordinador = calcCantCoordi(sC);
        GestorAbm abm = new GestorAbm();
        while (seguirOperando && cantCoordinador != 0) {

            mostrarCoordinador(sC);
            int codCoordinador = EntradaSalida.leerEntero("Ingrese el codigo del Coordinador a eliminar");
            Persona p = sC.buscarCoordiPorCodigo(codCoordinador);
            if (p != null) {
                abm.eliminarPersona(p, sC);
            } else {
                System.out.println("No existe un coordinador con ese codigo");

            }

            seguirOperando = EntradaSalida.leerBoolean("Desea eliminar otro coordinador [s/n]?");
        }

        if (cantCoordinador == 0) {
            System.out.println("No hay ningun Cliente en el sistema");
        }

    }

//-------------------------- modificar coordinador ------------------------------------
    private void opcModificarCoordi(Coordinador c) {
        boolean seguirOperando = true;
        while (seguirOperando) {
            int opc = EntradaSalida.leerEntero("Que quiere modificar? \n"
                    + "[1] Nombre de usuario\n"
                    + "[2] Password\n"+"opc: ");

            if (opc == 1) {
                String usuarioCordi = EntradaSalida.leerString("Ingrese el nuevo usuario:");
                c.setUsuario(usuarioCordi);
            } else if (opc == 2) {
                String contraCordi = EntradaSalida.leerPassword("Ingrese la nueva password:");
                c.setPassword(contraCordi);
            } else {
                System.out.println("Opcion invalida");
            }

            seguirOperando = EntradaSalida.leerBoolean("Desea modificar otra cosa del coordinador [s/n]?");
        }

    }

    private void modificarCoordinador(SistemaCatering sC) {
        boolean seguirOperando = true;
        int cantCoordinador = calcCantCoordi(sC);
        while (seguirOperando && cantCoordinador != 0) {

            mostrarCoordinador(sC);
            int codCoordinador = EntradaSalida.leerEntero("Ingrese el codigo del Coordinador a modificar");
            Coordinador p = sC.buscarCoordiPorCodigo(codCoordinador);
            //--------------------Si quiere modificar el usuario o la contra ---------------------------------------
            opcModificarCoordi(p);

            seguirOperando = EntradaSalida.leerBoolean("Desea modificar otro coordinador [s/n]?");
        }

        if (cantCoordinador == 0) {
            System.out.println("No hay ningun Coordinador en el sistema");
        }

    }
//-------------------------------------------------------------------------------------

}
