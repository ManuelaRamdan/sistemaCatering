
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadecatering;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author MANUELA
 */
public class Coordinador extends Persona implements Serializable {

    private int codCoordinador;

    public Coordinador(String u, String p, int codCoordinador) {
        setUsuario(u);
        setPassword(p);
        this.codCoordinador = codCoordinador;
    }

    @Override
    public boolean proceder(SistemaCatering sistemaCatering) {
        char opc = 0;
        boolean seguir = true;

        do {
            System.out.println("--- SISTEMA DE CATERING ---");
            opc = EntradaSalida.leerChar(
                    "OPCIONES DEL COORDINADOR\n"
                    + "[1] Dar de alta, baja o modificar  una reserva\n"
                    + "[2] Dar de alta, baja o modificar un cliente\n"
                    + "[3] Mostrar reservas\n"
                    + "[4] Mostrar clientes\n"
                    + "[5] Consultar por un cliente\n"
                    + "[6] Salir de este menu\n" + "opc: ");
            switch (opc) {
                case '1':// dar de alta una reserva

                    abmReserva(sistemaCatering);
                    break;
                case '2':
                    GestorAbm abm = new GestorAbm();
                    abm.abmCliente(sistemaCatering);
                    break;
                case '3':// Mostrar reserva 
                    if (sistemaCatering.calcularCantidadReservaExistentes() > 0) {

                        for (int i = 0; i < sistemaCatering.getReservas().size(); i++) {
                            Reserva reserva = sistemaCatering.getReservas().get(i);
                            reserva.mostrar();
                        }
                    } else {
                        System.out.println("No hay reservas");
                    }
                    break;
                case '4':// Mostrar los clientes
                    //mostrar los clientes

                    for (int i = 0; i < sistemaCatering.getPersonas().size(); i++) {
                        Persona persona = sistemaCatering.getPersonas().get(i);
                        if (persona instanceof Cliente) {
                            Cliente cliente = (Cliente) persona;
                            cliente.mostrar();
                        }
                    }

                    break;
                case '5':// consultar un cliente

                    int codCliente = EntradaSalida.leerEntero("Ingrese el codigo del cliente a consultar:");
                    Cliente c = Verificador.verificarCliente(sistemaCatering, codCliente);

                    c.mostrar();

                case '6':// salir del menu
                    seguir = true;
                    break;

                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';
            }
            if (opc >= '1' && opc <= '5') {
                try {
                    sistemaCatering.serializar("catering.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } //ver

        } while (opc != '6');

        return seguir;

    }

    @Override
    public void mostrar() {

        System.out.println("Coordinador - Codigo: " + codCoordinador + "\n"
                + "Usuarrio:" + this.getUsuario() + "\n"
                + "Contrasena:" + this.getPassword() + "\n");
    }

    private void abmReserva(SistemaCatering sC) {
        boolean seguirOperando = true;
        while (seguirOperando) {
            char opc = EntradaSalida.leerChar("[1] Dar de alta una reserva\n"
                    + "[2] Dar de baja una reserva\n"
                    + "[3] Modificar una reserva\n opc:");

            switch (opc) {
                case '1':// dar de alta una reserva
                    Reserva r = darAltaReserva(sC);
                    Cliente clienteEncontrado = sC.buscarClientePorCodigo(r.getCodCliente());
                    clienteEncontrado.agregarReserva(r);
                    sC.getReservas().add(r);
                    break;
                case '2':// dar de baja
                    darBajaReserva(sC);
                    break;
                case '3':// modificar
                    modificarReserva(sC);
                    break;
                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';
            }

            seguirOperando = EntradaSalida.leerBoolean("Desea dar de alta, baja o modificar otra reserva? [s/n]");
        }

    }

    private Reserva darAltaReserva(SistemaCatering sC) {

        int codCliente = EntradaSalida.leerEntero("Ingrese el codigo del cliente:");
        Cliente c = Verificador.verificarCliente(sC, codCliente);
        Reserva reserva = new Reserva();

        if (c != null) {
            reserva.setCodCliente(codCliente);
            reserva.setCodReserva(sC.buscarUltimoCodReserva() + 1);
            System.out.println("Fecha de inicio del evento:");
            LocalDateTime fechaInicio = EntradaSalida.leerFecha();
            fechaInicio = Verificador.verificarFecha(fechaInicio);
            reserva.setFechaInicioEvento(fechaInicio);

            System.out.println("Fecha del fin del evento:");
            LocalDateTime fechaFin = EntradaSalida.leerFecha();
            fechaFin = Verificador.verificarFecha(fechaFin);
            reserva.setFechaFinEvento(fechaFin);

            ArrayList<Servicio> serviciosElegidos = new ArrayList<>();
            serviciosElegidos = seleccionarServicios(serviciosElegidos, sC, fechaInicio, fechaFin);
            reserva.setServicios(serviciosElegidos);

            String restirccionesDieteticas = EntradaSalida.leerString("Restricciones del cliente:");
            reserva.setRestirccionesDieteticas(restirccionesDieteticas);

            String preferencias = EntradaSalida.leerString("Preferencias del cliente:");
            reserva.setPreferenciaCliente(preferencias);

            String tipoServicio = EntradaSalida.leerString("Ingrese el tipo de servicio: ");
            reserva.setTipoServicio(tipoServicio);

            int cantPersonas = EntradaSalida.leerEntero("Ingrese la cantidad de personas que va a asistir al evento");
            int precioTotal = calcPrecioTotal(serviciosElegidos, cantPersonas);
            reserva.setPrecio(precioTotal);

            String modoDeReserva = EntradaSalida.leerString("Ingrese el modo de reserva [telefono / mail / presencial]:");
            modoDeReserva = Verificador.verificarSiModoReservaAseptable(modoDeReserva);
            reserva.setModoDeReserva(modoDeReserva);

            Domicilio domicilioDeEntrega = EntradaSalida.leerDomicilioEntrega();
            reserva.setDireccionDeEntrega(domicilioDeEntrega);
            boolean estaEntregado = false;
        } else {
            System.out.println("No existe un cliente con ese Codigo");
        }

        return reserva;

    }

    private ArrayList<Servicio> seleccionarServicios(ArrayList<Servicio> servicios, SistemaCatering sC, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        boolean seguirEligiendo = true;
        while (seguirEligiendo) {
            mostrarServiciosDisponibles(sC, fechaInicio, fechaFin, servicios);
            String nomServicioElegido = EntradaSalida.leerString("Ingrese el nombre del que desea reservar:");
            Servicio s = Verificador.verificarServicio(sC, nomServicioElegido);
            if (s != null) {
                for (int j = 0; j < s.getMenus().size(); j++) {
                    Menu m = s.getMenus().get(j);
                    m.mostrar();
                }

                String nomMenuElegido = EntradaSalida.leerString("Ingrese el nombre del menu deseado: ");
                Menu m = Verificador.verificarMenu(nomMenuElegido, s);
                if (m != null) {
                    Servicio servicioSeleccionado = new Servicio(s.getNombreServicio());
                    servicioSeleccionado.agregarMenu(m);
                    servicios.add(servicioSeleccionado);
                }

            }

            seguirEligiendo = EntradaSalida.leerBoolean("Desea seleccionar otro servicio? [s/n]");
        }

        return servicios;

    }

    private void mostrarServiciosDisponibles(SistemaCatering sC, LocalDateTime fechaInicio, LocalDateTime fechaFin, ArrayList<Servicio> servicios) {
        EntradaSalida.mostrarString("Los servicios disponibles:");
        int hayServiciosDisponibles = 0;
        for (Servicio s : sC.getServicios()) {
            if (sC.estaDisponibleServicio(s.getNombreServicio(), fechaInicio, fechaFin)) {
                if (elegidoAntes(s, servicios) == false) {
                    s.mostrar();
                }
                hayServiciosDisponibles++;
            }
        }
        if (hayServiciosDisponibles == 0) {
            System.out.println("No hay servicios disponibles para la fecha elegida");
        }
    }

    private boolean elegidoAntes(Servicio servicioActual, ArrayList<Servicio> servicios) {

        for (Servicio s : servicios) {
//            System.out.println(s.coincidenNomServicio(servicioActual.getNombreServicio()) + " / " + s.getNombreServicio() + " / " + servicioActual.getNombreServicio());
            if (s.coincidenNomServicio(servicioActual.getNombreServicio())) {
                return true;
            }
        }
        return false;
    }

    private int calcPrecioTotal(ArrayList<Servicio> servicios, int cantPersonas) {
        int montoTotal = 0;
        for (Servicio s : servicios) {
            for (Menu m : s.getMenus()) {
                montoTotal += m.getPrecio();
            }

        }

        return montoTotal = montoTotal * cantPersonas;
    }

    private void darBajaReserva(SistemaCatering sC) {

        boolean seguirEliminando = true;
        int cantReservas = sC.getReservas().size();
        while (seguirEliminando && cantReservas != 0) {
            cantReservas = sC.getReservas().size();
            for (int i = 0; i < cantReservas; i++) {
                Reserva r = sC.getReservas().get(i);
                r.mostrar();
            }

            int codReserva = EntradaSalida.leerEntero("Ingrese el codigo de la reserva que desea");
            Reserva r = sC.buscarReserva(codReserva);

            if (r != null) {
                int opc = EntradaSalida.leerEntero("Que desea eliminar?\n"
                        + "[1] Dar de baja una reserva\n"
                        + "[2] Dar de baja un servicio\n opc: \n");

                if (opc == 1) {
                    boolean seguro = EntradaSalida.leerBoolean("Seguro queres eliminar la reserva? [s/n]");
                    if (seguro) {
                        sC.getReservas().remove(r);
                        System.out.println("Se elimino perfectamente");
                    }
                } else if (opc == 2) {
                    if (r.getServicios().size() > 1) {
                        eliminarServicioReserva(r.getServicios(), sC);
                    } else {
                        System.out.println("No se puede eliminar un servicio de esta reserva ya que solo tiene un servicio reservado");
                    }

                } else {
                    System.out.println("Opcion invalida");
                }
            } else {
                System.out.println("No existe una reservs con ese codigo");

            }

            seguirEliminando = EntradaSalida.leerBoolean("Desea eliminar otra reserva[s/n] ?");

        }

        if (cantReservas == 0) {
            System.out.println("No hay ninguna reserva en el sistema");
        }

        /**/
    }

    private void eliminarServicioReserva(ArrayList<Servicio> servicios, SistemaCatering sC) {
        System.out.println("Servicios selccionados: \n");
        for (Servicio s : servicios) {
            s.mostrar();
        }

        String nomServicioElegido = EntradaSalida.leerString("Ingrese el nombre del servicio que desar eliminar:");
        Servicio s = sC.buscarServicio(nomServicioElegido);

        if (s != null) {
            GestorAbm gestorAbm = new GestorAbm();
            gestorAbm.eliminarServicioCompleto(servicios, s);

        }
    }

    private void modificarReserva(SistemaCatering sC) {
        boolean seguirModificando = true;
        int cantReservas = sC.calcularCantidadReservaExistentes();
        while (seguirModificando && cantReservas != 0) {
            for (int i = 0; i < cantReservas; i++) {
                Reserva r = sC.getReservas().get(i);
                r.mostrar();
            }

            int codReserva = EntradaSalida.leerEntero("Ingrese el codigo de la reserva a modificar");
            Reserva r = sC.buscarReserva(codReserva);
            if (r != null) {
                opcModificarReserva(r, sC);
            } else {
                System.out.println("No existe una reserva con ese codigo");

            }

            seguirModificando = EntradaSalida.leerBoolean("Desea modificar otra reserva [s/n] ?");

        }

        if (cantReservas == 0) {
            System.out.println("No hay ninguna reserva en el sistema");
        }
    }

    private void opcModificarReserva(Reserva r, SistemaCatering sC) {
        boolean seguirModificando = true;
        while (seguirModificando) {
            char opc = EntradaSalida.leerChar("Que quiere modificar? "
                    + "[1] Codigo del cliente\n"
                    + "[2] Fecha de inicio del evento\n"
                    + "[3] Fech de fin del evento\n"
                    + "[4] Servicios seleccionados\n"
                    + "[5] Restricciones Dieteticas del cliente\n"
                    + "[6] Preferencias del cliente\n"
                    + "[7] Tipo de servicio\n"
                    + "[8] Cantidad de personas\n"
                    + "[9] Modo de reserva\n"
                    + "[10] Direccion de evento\n opc:");

            switch (opc) {
                case '1':// codCliente
                    System.out.println("Codigo del cliente actual: " + r.getCodCliente());
                    int codCliente = EntradaSalida.leerEntero("Ingrese el codigo del cliente nuevo:");
                    Cliente c = sC.buscarClientePorCodigo(codCliente);
                    if (c != null) {
                        r.setCodCliente(codCliente);
                    } else {
                        System.out.println("No existe un cliente con ese codigo");
                    }
                    break;

                case '2':// fecha inicio
                    System.out.println("Fecha de inicio actual: ");
                    r.mostrarFecha(r.getFechaInicioEvento());
                    System.out.println("Fecha de inicio del evento nueva:");
                    LocalDateTime fechaInicio = EntradaSalida.leerFecha();
                    fechaInicio = Verificador.verificarFecha(fechaInicio);
                    r.setFechaInicioEvento(fechaInicio);
                    break;
                case '3':// fecha fin
                    System.out.println("Fecha de fin actual: ");
                    r.mostrarFecha(r.getFechaFinEvento());
                    System.out.println("Fecha del fin del evento nueva:");
                    LocalDateTime fechaFin = EntradaSalida.leerFecha();
                    fechaFin = Verificador.verificarFecha(fechaFin);
                    r.setFechaFinEvento(fechaFin);
                    break;
                case '4':// servicios
                    modificarServicioSellecionado(r, sC, r.getServicios());
                    break;
                case '5':// restricciosnes
                    System.out.println("Restricciones dieteticas del cliente actual: " + r.getRestirccionesDieteticas());

                    String restriccionNueva = EntradaSalida.leerString("Ingrese las nuevas restricciones dieteticas del cliente:");
                    r.setRestirccionesDieteticas(restriccionNueva);
                    break;
                case '6'://preferencias
                    System.out.println("Preferencias del cliente actual: " + r.getPreferenciaCliente());

                    String preferenciaNueva = EntradaSalida.leerString("Ingrese las nuevas preferencias del cliente:");
                    r.setPreferenciaCliente(preferenciaNueva);

                    break;
                case '7':// tipo de servicio
                    System.out.println("El tipo de servicio del cliente actual: " + r.getTipoServicio());

                    String tipoServicioNuevo = EntradaSalida.leerString("Ingrese el nuevo tipo del cliente:");
                    r.setTipoServicio(tipoServicioNuevo);

                    break;
                case '8'://cant de personas
                    System.out.println("La cantidad de personas actual es: " + r.getCantidadPersonas());
                    int cantPersonaNueva = EntradaSalida.leerEntero("Ingrese la nueva cantidad de personas: ");
                    r.setCantidadPresonas(cantPersonaNueva);
                    break;
                case '9'://modo de reserva
                    System.out.println("El modo de reserva actual: " + r.getModoDeReserva());

                    String modoReservaNuevo = EntradaSalida.leerString("Ingrese el nuevo modo de reserva:");
                    r.setModoDeReserva(modoReservaNuevo);
                    break;

                case 10://direccion del evento
                    System.out.println("La dirreccion del evento actal es: " + r.getDireccionDeEntrega());
                    Domicilio direccionNueva = EntradaSalida.leerDomicilioEntrega();
                    r.setDireccionDeEntrega(direccionNueva);
                    break;
                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';
            }
            seguirModificando = EntradaSalida.leerBoolean("Desea seguir modificando algo de esta reserva [s/n] ?");
        }
    }

    private void modificarServicioSellecionado(Reserva r, SistemaCatering sC, ArrayList<Servicio> servicios) {
        boolean seguirModificando = true;

        while (seguirModificando) {

            int opc = EntradaSalida.leerEntero(
                    "[1] Agregar un servicio a la reserva\n"
                    + "[2] Modificar el menu elegido\n opc:");

            if (opc == 1) {
                servicios = seleccionarServicios(servicios, sC, r.getFechaInicioEvento(), r.getFechaFinEvento());
            } else if (opc == 2) {
                System.out.println("Servicios selccionados: \n");
                for (Servicio s : servicios) {
                    s.mostrar();
                }

                String nomServicioElegido = EntradaSalida.leerString("Ingrese el nombre del servicio que desar eliminar:");
                Servicio s = r.buscarServicioReservados(nomServicioElegido);

                if (s != null) {

                    modificarMenuSeleccionado(s, sC);

                }

            } else {
                System.out.println("Error. Opcion invalida");
            }

            seguirModificando = EntradaSalida.leerBoolean("Desea modificar otro servicio seleccionado: [s/n]");
        }
    }

    private Menu obtenerMenuReservado(ArrayList<Menu> menus) {
        Menu menu = new Menu();
        for (Menu m : menus) {
            menu = m;
        }
        return menu;
    }

    private void modificarMenuSeleccionado(Servicio servicioReservado, SistemaCatering sC) {
        Menu menuReservadoActual = obtenerMenuReservado(servicioReservado.getMenus());
        Servicio servicioCompleto = sC.buscarServicio(servicioReservado.getNombreServicio());

        if (servicioCompleto != null) {
            ArrayList<Menu> menusDisponibles = servicioCompleto.getMenus();
            menusDisponibles.remove(menuReservadoActual);
            System.out.println("Menu disponibles: ");
            for (Menu m : menusDisponibles) {
                m.mostrar();
            }

            String nomMenuNuevo = EntradaSalida.leerString("Ingrese el nuevo menu que desea reservar:");
            Menu menuReservadoNuevo = servicioCompleto.buscarMenu(nomMenuNuevo);

            if (menuReservadoNuevo != null) {

                servicioReservado.getMenus().remove(menuReservadoActual);
                servicioReservado.agregarMenu(menuReservadoNuevo);
            }

        }

    }

    public int getCodCoordinador() {
        return codCoordinador;
    }

    public void setCodCoordinador(int codCoordinador) {
        this.codCoordinador = codCoordinador;
    }

    boolean coincideCodigoCoordinador(int codigo) {
        return codCoordinador == codigo;
    }

}
