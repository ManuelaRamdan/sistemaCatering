/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadecatering;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author MANUELA
 */
class Reserva implements Serializable {

    private int codReserva;
    private int codCliente;
    private LocalDateTime fechaInicioEvento;
    private LocalDateTime fechaFinEvento;
    private ArrayList<Servicio> servicios = new ArrayList<Servicio>();
    private String restirccionesDieteticas;
    private String preferenciaCliente;
    private String tipoServicio;
    private int cantidadPersonas;
    private int precio;
    private String modoDeReserva;
    private Domicilio direccionDeEntrega;
    private boolean estaEntregado;

    public int getCodReserva() {
        return codReserva;
    }

    public void setCodReserva(int codReserva) {
        this.codReserva = codReserva;
    }

    void agregarServicio(Servicio s) {
        servicios.add(s);
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public LocalDateTime getFechaInicioEvento() {
        return fechaInicioEvento;
    }

    public void setFechaInicioEvento(LocalDateTime fechaInicioEvento) {
        this.fechaInicioEvento = fechaInicioEvento;
    }

    public LocalDateTime getFechaFinEvento() {
        return fechaFinEvento;
    }

    public void setFechaFinEvento(LocalDateTime fechaFinEvento) {
        this.fechaFinEvento = fechaFinEvento;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }

    public String getRestirccionesDieteticas() {
        return restirccionesDieteticas;
    }

    public void setRestirccionesDieteticas(String restirccionesDieteticas) {
        this.restirccionesDieteticas = restirccionesDieteticas;
    }

    public String getPreferenciaCliente() {
        return preferenciaCliente;
    }

    public void setPreferenciaCliente(String preferenciaCliente) {
        this.preferenciaCliente = preferenciaCliente;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPresonas(int cantidadPresonas) {
        this.cantidadPersonas = cantidadPresonas;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getModoDeReserva() {
        return modoDeReserva;
    }

    public void setModoDeReserva(String modoDeReserva) {

        this.modoDeReserva = modoDeReserva;

    }

    public Domicilio getDireccionDeEntrega() {
        return direccionDeEntrega;
    }

    public void setDireccionDeEntrega(Domicilio direccionDeEntrega) {
        this.direccionDeEntrega = direccionDeEntrega;
    }

    public boolean isEstaEntregado() {
        return estaEntregado;
    }

    public void setEstaEntregado(boolean estaEntregado) {
        this.estaEntregado = estaEntregado;
    }

    public void mostrarFecha(LocalDateTime f) {
        System.out.println(f.getDayOfMonth() + "/" + f.getMonthValue() + "/" + f.getYear() + " a las " + f.getHour() + ":" + f.getMinute() + "hs \n");
    }

    public void mostrar() {
        System.out.println("Codigo de la reserva: " + codReserva + "\n"
                + "codCliente: " + codCliente + "\n");
        System.out.println("Fecha de inicio del evento: ");
        mostrarFecha(fechaInicioEvento);
        System.out.println("Fecha de fin del evento:");
        mostrarFecha(fechaFinEvento);

        System.out.println("Servicios seleccionados:");
        for (Servicio s : servicios) {
            s.mostrar();
        }
        System.out.println("Restircciones Dieteticas del cliente:" + restirccionesDieteticas);
        System.out.println("Preferencia del cliente=" + preferenciaCliente);
        System.out.println("Tipo de servicio seleccionado: " + tipoServicio);
        System.out.println("Cantidad de personas: " + cantidadPersonas);
        System.out.println("Precio total: $" + precio);
        System.out.println("Modo de reserva: " + modoDeReserva);
        direccionDeEntrega.mostrar();
        estaEntregado = verSiEstaEntregado(fechaFinEvento);
        System.out.println("Entrgado: " + estaEntregado);
        System.out.println("\n----------------------------------------\n");
    }

    private boolean verSiEstaEntregado(LocalDateTime fechaFin) {
        //LocalDateTime hoy = LocalDateTime.now();

        LocalDate fecha = LocalDate.of(2024, 6, 14);

        LocalTime horaElegida = LocalTime.of(16, 00);
        LocalDateTime hoy = LocalDateTime.of(fecha, horaElegida);
        return hoy.isAfter(fechaFin);
    }

    boolean coincideCodReserva(int codigo) {
        return codReserva == codigo;
    }

    public Servicio buscarServicioReservados(String datos) {
        for (Servicio s : servicios) {
            if (s.coincidenNomServicio(datos)) {
                return s;
            }
        }
        return null;
    }

}
