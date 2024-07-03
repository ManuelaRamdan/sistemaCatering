/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadecatering;

import java.util.ArrayList;

/**
 *
 * @author MANUELA
 */
public class GestorAbm {

    //------------------------------ abm servicio ------------------------------
    public void abmServicio(ArrayList<Servicio> servicios, SistemaCatering sC) {
        boolean seguirOperando = true;
        int cantServicio = 0;
        while (seguirOperando) {
            cantServicio = calcCantServicios(sC);
            char opc = EntradaSalida.leerChar(
                    "[1] Dar de alta un servicio\n"
                    + "[2] Dar de baja un servicio\n"
                    + "[3] Modificar un servicio\n" + "opc: ");

            switch (opc) {
                case '1':// dar de alta
                    servicios = cargarServicios(servicios, sC);
                    break;

                case '2':// dar de baja

                    while (seguirOperando && cantServicio != 0) {
                        Servicio servicioAEliminar = mostrarServiciosDisponibles(servicios, sC);

                        opcParaEliminar(servicios, servicioAEliminar);

                        seguirOperando = EntradaSalida.leerBoolean("Desea eliminar otro servicio[s/n]?");
                    }

                    break;
                case '3'://modificar

                    while (seguirOperando && cantServicio != 0) {
                        Servicio servicioAModificar = mostrarServiciosDisponibles(servicios, sC);

                        modificarServicio(servicioAModificar, sC);

                        seguirOperando = EntradaSalida.leerBoolean("Desea modificar otro servicio[s/n]?");
                    }
                    break;

                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';
            }

            if (cantServicio == 0 && (opc == '2' || opc == '3')) {
                System.out.println("No hay ningun servicio ingresado en el sistema, por eso, no se puede eliminar ni modificar un servicio");
            }

            seguirOperando = EntradaSalida.leerBoolean("Desea dar de alta, baja o modificacion de otro Servicio[s/n]?");
        }
    }

    private int calcCantServicios(SistemaCatering sC) {
        int cont = 0;
        for (int i = 0; i < sC.getServicios().size(); i++) {
            cont++;
        }

        return cont;
    }

    private Servicio mostrarServiciosDisponibles(ArrayList<Servicio> servicios, SistemaCatering sC) {
        System.out.println("Los nombres de los servicios disponibles son:");
        for (int i = 0; i < servicios.size(); i++) {
            System.out.println("\t -" + servicios.get(i).getNombreServicio());
        }
        String nombreServicioAModificar = EntradaSalida.leerString("Ingrese el nombre del servicio deseado:");

        Servicio servicioAModificar = Verificador.verificarServicio(sC, nombreServicioAModificar);
        if (servicioAModificar == null) {
            System.out.println("Ingrese un valor valido");
        }

        return servicioAModificar;
    }
//------------------------- alta servicio -----------------------------------------

    private ArrayList<Servicio> cargarServicios(ArrayList<Servicio> servicios, SistemaCatering sC) {
        boolean masServicio = true;
        while (masServicio) {
            String nombreServicio = EntradaSalida.leerString("Nombre servicio:");
            nombreServicio = Verificador.verificarSiEstaEnMay(nombreServicio);
            Servicio servicio = sC.buscarServicio(nombreServicio);
            if (servicio == null) {

                servicio = new Servicio(nombreServicio);
                servicio = cargarMenu(servicio);
                servicios.add(servicio);
            } else {
                System.out.println("Ya existe un servicio con ese nombre");
            }

            masServicio = EntradaSalida.leerBoolean("Quiere ingresar otro servicio? [s/n]");
        }
        return servicios;
    }

    private Servicio cargarMenu(Servicio s) {

        boolean otroMenu = true;
        while (otroMenu) {
            String nombreMenu = EntradaSalida.leerString("Nombre del menu:");
            nombreMenu = Verificador.verificarSiEstaEnMay(nombreMenu);
            Menu menu = s.buscarMenu(nombreMenu);
            if (menu == null) {
                menu = new Menu();
                menu.setNombreMenu(nombreMenu);
                System.out.println("Ingrese los platos de la entrada");
                menu = cargarPlato(menu, 'e');//plato de la entrada
                System.out.println("Ingrese los platos pricipales");
                menu = cargarPlato(menu, 'p');//plato principal
                int precioMenu = EntradaSalida.leerEntero("Ingrese el precio del menu por persona:");
                menu.setPrecio(precioMenu);
                s.agregarMenu(menu);
            } else {
                System.out.println("Ya existe un menu con ese nombre");
            }

            otroMenu = EntradaSalida.leerBoolean("Quiere ingresar otro menu? [s/n]");
        }

        return s;
    }

    private Menu cargarPlato(Menu m, char opc) {
        boolean masPlato = true;
        while (masPlato) {
            String nombrePlato = EntradaSalida.leerString("Nombre del plato:");
            nombrePlato = Verificador.verificarSiEstaEnMay(nombrePlato);
            Plato plato = m.buscarPlato(nombrePlato);
            if (plato == null) {
                plato = new Plato();
                plato.setNombre(nombrePlato);
                m.agregarPlato(plato, opc);
            } else {
                System.out.println("Ya existe un plato con ese nombre");
            }
            masPlato = EntradaSalida.leerBoolean("Quiere ingresar otro plato? [s/n]");
        }
        return m;
    }
//----------------------------- modificar Servicio -----------------------------------

    private void modificarServicio(Servicio servicioAModificar, SistemaCatering sC) {
        boolean seguirModificando = true;
        while (seguirModificando) {
            char opc = EntradaSalida.leerChar("Que desea modificar?\n "
                    + "[1] Nombre del servicio\n "
                    + "[2] Algo de los menus del servicio seleccionado \n"
                    + "[3] Agregar un menu\n opc:");

            switch (opc) {
                case '1':// cambiar el nombre
                    String nuevoNombreServicio = EntradaSalida.leerString("Ingrese el nuevo nombre del servicio");
                    nuevoNombreServicio = Verificador.verificarSiEstaEnMay(nuevoNombreServicio);
                    Servicio sExiste = sC.buscarServicio(nuevoNombreServicio);
                    if (sExiste == null) {
                        servicioAModificar.setNombreServicio(nuevoNombreServicio);
                    } else {
                        System.out.println("Ya existe un servicio con ese nombre");
                    }
                    break;

                case '2':// algo del menu

                    modificarMenuDelServicio(servicioAModificar);

                    break;
                case '3'://agregar un menu

                    servicioAModificar = cargarMenu(servicioAModificar);
                    break;

                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';
            }

            seguirModificando = EntradaSalida.leerBoolean("Quiere seguir modificando el nombre del servicio o algo de sus menus [s/n?");

        }

    }

    private void modificarMenuDelServicio(Servicio servicioAModificar) {
        EntradaSalida.mostrarString("Los menus que puede modificar son:");
        for (int i = 0; i < servicioAModificar.getMenus().size(); i++) {
            Menu m = servicioAModificar.getMenus().get(i);
            m.mostrar();
        }
        String nomMenu = EntradaSalida.leerString("Ingrese el nombre del menu deseado");
        Menu menuAModificar = Verificador.verificarMenu(nomMenu, servicioAModificar);

        boolean seguirModificando = true;
        while (seguirModificando) {

            char opc = EntradaSalida.leerChar(
                    "¿Qué desea modificar del servicio " + servicioAModificar.getNombreServicio() + "?\n"
                    + "[1] Nombre de un menu\n"
                    + "[2] El nombre de un plato\n"
                    + "[3] El precio de un menu\n"
                    + "[4] Agregar un plato a la entrada\n"
                    + "[5] Agregat un plato principal\n" + "opc: ");

            switch (opc) {
                case '1':
                    modificarNomMenu(menuAModificar, servicioAModificar);
                    break;
                case '2':
                    modificarPlatoDeUnMenu(menuAModificar);
                    break;
                case '3':
                    modificarPrecioDelMenu(menuAModificar);
                    break;
                case '4':
                    menuAModificar = cargarPlato(menuAModificar, 'e');
                    break;
                case '5':
                    menuAModificar = cargarPlato(menuAModificar, 'p');
                    break;
                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';
            }

            seguirModificando = EntradaSalida.leerBoolean("Desea modificar otra cosa del menu [s/n]?");
        }
    }

    private void modificarPrecioDelMenu(Menu m) {
        System.out.println("Precio Actual: $" + m.getPrecio());
        int nuevoPrecio = EntradaSalida.leerEntero("Ingrese el nuevo precio de este menu");
        m.setPrecio(nuevoPrecio);
    }


    private void modificarPlatoDeUnMenu(Menu m) {
        m.mostrarPlato();
        String nomPlato = EntradaSalida.leerString("Ingrese el nombre del plato que queres modificar");
        Plato platoAModificar = Verificador.verificarPlato(nomPlato, m);

        String nuevoNombrePlato = EntradaSalida.leerString("Ingrese el nuevo nombre del plato");
        nuevoNombrePlato = Verificador.verificarSiEstaEnMay(nuevoNombrePlato);
        Plato pExiste = m.buscarPlato(nuevoNombrePlato);
        if (pExiste == null) {
            platoAModificar.setNombre(nuevoNombrePlato);
        } else {
            System.out.println("Ya existe un plato con ese nombre");
        }

    }

    private void modificarNomMenu(Menu m, Servicio s) {
        String nuevoNombreMenu = EntradaSalida.leerString("Ingrese el nuevo nombre del menu");
        nuevoNombreMenu = Verificador.verificarSiEstaEnMay(nuevoNombreMenu);
        Menu mExiste = s.buscarMenu(nuevoNombreMenu);
        if (mExiste == null) {
            m.setNombreMenu(nuevoNombreMenu);
        } else {
            System.out.println("Ya existe un menu con ese nombre");
        }

    }

//--------------------------- baja servicio --------------------------------------
    private void opcParaEliminar(ArrayList<Servicio> servicios, Servicio s) {
        boolean seguirEliminando = true;
        char opc = 0;
        while (seguirEliminando) {
            opc = EntradaSalida.leerChar(
                    "OPCIONES PARA ELIMINAR\n"
                    + "[1] Eliminar un servicio completo\n"
                    + "[2] Eliminar un menu completo de un servicio\n"
                    + "[3] Eliminar un plato de un menu de un servicio\n" + "opc: ");

            switch (opc) {
                case '1':
                    eliminarServicioCompleto(servicios, s);
                    break;
                case '2':
                    eliminarMenuDeServicio(s);
                    break;
                case '3':
                    eliminarPlato(s);
                    break;
                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';

            }

            seguirEliminando = EntradaSalida.leerBoolean("Desea eliminar otra cosa del Servicio " + s.getNombreServicio() + "[s/n]?");
        }

    }

    public void eliminarServicioCompleto(ArrayList<Servicio> servicios, Servicio s) {
        boolean seguroEliminar = EntradaSalida.leerBoolean("Esta seguro que quiere eliminar el servicio " + s.getNombreServicio() + " completo [s/n]?");
        if (seguroEliminar) {
            servicios.remove(s);
            System.out.println("Servicio eliminado: " + s.getNombreServicio());
        }

    }

    private void eliminarMenuDeServicio(Servicio s) {
        EntradaSalida.mostrarString("Los menus que pueden eliminar son:");
        for (int i = 0; i < s.getMenus().size(); i++) {
            Menu m = s.getMenus().get(i);
            m.mostrar();
        }
        String nomMenuAEliminar = EntradaSalida.leerString("Ingrese el nombre del menu que queres eliminar por completo:");
        Menu menuAEliminar = Verificador.verificarMenu(nomMenuAEliminar, s);

        boolean seguroEliminar = EntradaSalida.leerBoolean("Esta seguro que quiere eliminar el menu " + menuAEliminar.getNombreMenu() + " completo [s/n]?");
        if (seguroEliminar) {
            s.getMenus().remove(menuAEliminar);
            System.out.println("Menu eliminado: " + nomMenuAEliminar);
        }

    }

    private Menu mostrarPlatosDeUnServicio(Servicio s) {
        for (int i = 0; i < s.getMenus().size(); i++) {
            Menu m = s.getMenus().get(i);
            System.out.println("Nombre menu: "+m.getNombreMenu()+"\n");
//            mostrarSoloPlatosDeUnMenu(m);
            m.mostrarPlato();
        }
        String nomMenu = EntradaSalida.leerString("Ingrese el nombre del menu donde esta el plato deseado para operar con el");
        Menu menu = Verificador.verificarMenu(nomMenu, s);

        return menu;

    }

    private void eliminarPlato(Servicio s) {
        EntradaSalida.mostrarString("Los platos que se pueden eliminar :");
        Menu menuAEliminar = mostrarPlatosDeUnServicio(s);
        menuAEliminar.mostrarPlato();
//        mostrarSoloPlatosDeUnMenu(menuAEliminar);
        String nomPlatoAEliminar = EntradaSalida.leerString("Ingrese el nombre del plato que queres eliminar por completo");
        Plato platoAEliminar = Verificador.verificarPlato(nomPlatoAEliminar, menuAEliminar);
        
        
        boolean seguroEliminar = EntradaSalida.leerBoolean("Esta seguro que quiere eliminar el plato" + platoAEliminar.getNombre() + "[s/n]?");
        if (seguroEliminar) {
            if (esPlatoEntrada(nomPlatoAEliminar, menuAEliminar.getPlatosEntrada())) {
                menuAEliminar.getPlatosEntrada().remove(platoAEliminar);
            }else{
                menuAEliminar.getPlatosPrincipal().remove(platoAEliminar);
            }

            System.out.println("Plato eliminado: " + nomPlatoAEliminar);
        }
    }
    
    private boolean esPlatoEntrada(String nombre, ArrayList<Plato> platos){
        boolean esPlatoEntrada = false;
        for(Plato plato : platos){
            if (plato.coincidenNomPlato(nombre)) {
                esPlatoEntrada = true;
            }
        }
        return esPlatoEntrada;
    }

    public String crearUsuario() {
        String usuario = EntradaSalida.leerString("Ingrese el usuario:");
        return usuario;
    }

    public String crearContra() {
        String contra = EntradaSalida.leerPassword("Ingrese la password:");
        return contra;
    }

//--------------------------------- abm cliente ---------------------------------------
    public void abmCliente(SistemaCatering sistemaCatering) {
        boolean seguirOperando = true;
        while (seguirOperando) {

            char opc = EntradaSalida.leerChar("[1] Dar de alta un cliente\n"
                    + "[2] Dar de baja un cliente\n"
                    + "[3] Modificar un cliente" + "opc: ");

            switch (opc) {
                case '1':// dar de alta
                    altaCliente(sistemaCatering);
                    break;

                case '2':// dar de baja
                    bajaCliente(sistemaCatering);
                    break;
                case '3'://modificar
                    modificarCliente(sistemaCatering);
                    break;

                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';
            }

            seguirOperando = EntradaSalida.leerBoolean("Desea dar alta, baja o modificar otro cliente [s/n]?");
        }

    }
// ---------------------- alta cliente -------------------------------

    private int calcCantCliente(SistemaCatering sC) {
        int cont = 0;
        for (int i = 0; i < sC.getPersonas().size(); i++) {
            Persona persona = sC.getPersonas().get(i);
            if (persona instanceof Cliente) {
                cont++;
            }
        }

        return cont;
    }

    private void altaCliente(SistemaCatering sC) {
        int cantCliente = 0;
        boolean seguirDandoAlta = true;
        while (seguirDandoAlta) {
            cantCliente = calcCantCliente(sC);

            String usuario = crearUsuario();
            String contra = crearContra();
            Persona p = sC.buscarPersona(usuario + ":" + contra);
            if (p != null) {
                EntradaSalida.mostrarString("ERROR: El usuario ya figura en el sistema");
            } else {
                p = cargarCliente(usuario, contra, sC.buscarUltimoCodCliente() + 1, sC);
                sC.getPersonas().add(p);
                EntradaSalida.mostrarString("Se incorporo el cliente al sistema de manera exitosa");
            }

            seguirDandoAlta = EntradaSalida.leerBoolean("Queres dar de alta otro Cliente? [s/n]");
        }
    }

    private Cliente cargarCliente(String usuario, String contra, int codCliente, SistemaCatering sC) {
        String nombre = EntradaSalida.leerString("Ingrese el nombre");
        nombre = Verificador.verificarSiEstaEnMay(nombre);

        String apellido = EntradaSalida.leerString("Ingrese el apellido");
        apellido = Verificador.verificarSiEstaEnMay(apellido);

        int telReferencia = EntradaSalida.leerEntero("Ingrese el numero de telefono (011)");
        telReferencia = Verificador.verificarTelefonoExiste(telReferencia, sC);

        String email = EntradaSalida.leerString("Ingrese el email");
        email = Verificador.verificarEmailExiste(email, sC);

        Cliente cliente = new Cliente(usuario, contra, codCliente, nombre, apellido, telReferencia, email);
        return cliente;
    }

    private void mostrarCliente(SistemaCatering sC) {
        for (int i = 0; i < sC.getPersonas().size(); i++) {
            Persona persona = sC.getPersonas().get(i);
            if (persona instanceof Cliente) {
                Cliente cliente = (Cliente) persona;
                cliente.mostrar();
            }
        }
    }
//----------------------- baja cliente ------------------------------------

    private void bajaCliente(SistemaCatering sC) {

        boolean seguirEliminando = true;
        int cantCliente = calcCantCliente(sC);
        while (seguirEliminando && cantCliente != 0) {

            mostrarCliente(sC);
            int codCliente = EntradaSalida.leerEntero("Ingrese el codigo del cliente a eliminar");
            Persona p = sC.buscarClientePorCodigo(codCliente);
            if (p != null) {
                eliminarPersona(p, sC);
            } else {
                System.out.println("No existe un cliente con ese codigo");

            }
            seguirEliminando = EntradaSalida.leerBoolean("Desea eliminar otro cliente [s/n] ?");

        }

        if (cantCliente == 0) {
            System.out.println("No hay ningun Cliente en el sistema");
        }

    }

    public void eliminarPersona(Persona p, SistemaCatering sC) {
        boolean seguro = EntradaSalida.leerBoolean("Seguro queres eliminar el cliente? [s/n]");
        if (seguro) {

            sC.getPersonas().remove(p);
            System.out.println("Se elimino perfectamente");
        }

    }
// ------------------------ modificar cliente -----------------------

    private void modificarCliente(SistemaCatering sC) {

        boolean seguirEliminando = true;
        int cantCliente = calcCantCliente(sC);
        while (seguirEliminando && cantCliente != 0) {

            mostrarCliente(sC);
            int codCliente = EntradaSalida.leerEntero("Ingrese el codigo del Cliente a modificar");
            Cliente c = Verificador.verificarCliente(sC, codCliente);
            opcModificarCliente(c, sC);
            seguirEliminando = EntradaSalida.leerBoolean("Desea modificar otro cliente [s/n] ?");

        }

        if (cantCliente == 0) {
            System.out.println("No hay ningun Cliente en el sistema");
        }
    }

    private void opcModificarCliente(Cliente c, SistemaCatering sC) {
        char opc = EntradaSalida.leerChar("Que quiere modificar? \n"
                + "[1] Nombre de usuario\n"
                + "[2] Password\n"
                + "[3] Nombre\n"
                + "[4] Apellido\n"
                + "[5] Telefono de referencia\n"
                + "[6] Email\n"
                + "opc:");

        switch (opc) {
            case '1':// usuario
                String usuario = EntradaSalida.leerString("Ingrese el nuevo usuario:");
                Persona p = sC.buscarPersona(usuario+":"+c.getPassword());
                if (p == null) {
                    c.setUsuario(usuario);
                }else{
                    System.out.println("Ya existe un usuario con ese nombre");
                }
                
                break;

            case '2':// contra
                String contra = EntradaSalida.leerPassword("Ingrese la nueva password:");
                
                c.setPassword(contra);
                break;
            case '3'://nombre
                String nombre = EntradaSalida.leerString("Ingrese el nuevo nombre de la persona:");
                nombre = Verificador.verificarSiEstaEnMay(nombre);
                c.setNombre(nombre);
                break;
            case '4'://apellido
                String apellido = EntradaSalida.leerString("Ingrese el nuevo apellido:");
                apellido = Verificador.verificarSiEstaEnMay(apellido);
                c.setApellido(apellido);
                break;
            case '5'://telefono
                int tel = EntradaSalida.leerEntero("Intrese el nuevo tel de referencia");
                tel = Verificador.verificarTelefonoExiste(tel, sC);
                c.setTelReferencia(tel);
                break;
            case '6'://email
                String email = EntradaSalida.leerString("Ingrese el nuevo email:");
                email = Verificador.verificarEmailExiste(email, sC);
                c.setEmail(email);
                break;
            default:
                EntradaSalida.mostrarString("ERROR: Opcion invalida");
                opc = '*';
        }

    }

}
