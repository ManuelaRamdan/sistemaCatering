/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadecatering;

/**
 *
 * @author MANUELA
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SistemaCatering implements Serializable {

    private ArrayList<Servicio> servicios;
    private ArrayList<Persona> personas;
    private ArrayList<Reserva> reservas;

    public SistemaCatering() {
        servicios = new ArrayList<Servicio>();
        personas = new ArrayList<Persona>();
        reservas = new ArrayList<Reserva>();
    }

    public SistemaCatering deSerializar(String a) throws IOException, ClassNotFoundException {//mostrar
        FileInputStream f = new FileInputStream(a);//se usa para crear ebjetImput
        ObjectInputStream o = new ObjectInputStream(f);
        SistemaCatering s = (SistemaCatering) o.readObject();// lee un objeto sistema de inscripcion
        o.close();
        f.close();
        return s;
    }

    public void serializar(String a) throws IOException {//guardar
        FileOutputStream f = new FileOutputStream(a);// sabe grabar pero no grabar objetos
        ObjectOutputStream o = new ObjectOutputStream(f);// se lo paso pq este si sabe grabar objetos, si o si tiene que ser un fileOutputStrem porque su constructor no recibe un String
        o.writeObject(this);// escribi el objeto , this que es el sistema de objetos
        o.close();//se cierra
        f.close();//se cierra

    }

    public void pantallaCatering() {
        EntradaSalida.mostrarString("Bienvenido/a al sistema");
    }
//----------------------- persona ------------------------

    public Persona buscarPersona(String datos) {
        for (Persona p : personas) {
            if (p.coincidenUsrPwd(datos)) {
                return p;
            }
        }
        return null;
    }

    public Cliente buscarClientePorCodigo(int codigo) {
        for (Persona persona : personas) {
            if (persona instanceof Cliente) {
                Cliente cliente = (Cliente) persona;
                if (cliente.coincideCodigoCliente(codigo)) {
                    return cliente;
                }
            }
        }
        return null; // Si no se encuentra el cliente
    }

    public int buscarUltimoCodCliente() {
        int ultimoCodigo = 0;
        for (Persona persona : personas) {
            if (persona instanceof Cliente) {
                Cliente cliente = (Cliente) persona;
                ultimoCodigo = cliente.getCodCliente();
            }
        }
        return ultimoCodigo;
    }

    public Coordinador buscarCoordiPorCodigo(int codigo) {
        for (Persona persona : personas) {
            if (persona instanceof Coordinador) {
                Coordinador coordi = (Coordinador) persona;
                if (coordi.coincideCodigoCoordinador(codigo)) {
                    return coordi;
                }
            }
        }
        return null; // Si no se encuentra el cliente
    }

    public int buscarUltimoCodCoordi() {
        int ultimoCodigo = 0;
        for (Persona persona : personas) {
            if (persona instanceof Coordinador) {
                Coordinador coordi = (Coordinador) persona;
                ultimoCodigo = coordi.getCodCoordinador();
            }
        }
        return ultimoCodigo;
    }
//----------------------------------------------------------------
//---------------------- servicio ----------------------------

    public Servicio buscarServicio(String datos) {
        for (Servicio s : servicios) {
            if (s.coincidenNomServicio(datos)) {
                return s;
            }
        }
        return null;
    }

//-------------------------------------------------------------------
//----------------------- reserva  -----------------------------------------
    public boolean estaDisponibleServicio(String nombre, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        for (Reserva r1 : reservas) {
            LocalDateTime inicioReserva = r1.getFechaInicioEvento();
            LocalDateTime finReserva = r1.getFechaFinEvento();
            for (Servicio s1 : r1.getServicios()) {
                if (s1.coincidenNomServicio(nombre) && !((finReserva.isBefore(fechaInicio) || 
                        inicioReserva.isAfter(fechaFin)))&& !(finReserva.equals(fechaInicio)|| inicioReserva.equals(fechaFin))) {
                    return false; // servicio disponible
                }
            }
        }
        return true;
    }

    public Reserva buscarReserva(int codigo) {
        for (Reserva r : reservas) {
            if (r.coincideCodReserva(codigo)) {
                return r;
            }
        }
        return null;
    }

    public int buscarUltimoCodReserva() {
        int ultimoCodigo = 0;
        for (Reserva r : reservas) {

            ultimoCodigo = r.getCodReserva();

        }
        return ultimoCodigo;
    }

    public int calcularCantidadReservaExistentes() {
        return reservas.size();
    }
//------------------------------------------------------------------------
//------------------------- set y get ----------------------------------------

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(ArrayList<Persona> personas) {
        this.personas = personas;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

}
