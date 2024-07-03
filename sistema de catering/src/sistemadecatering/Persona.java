/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadecatering;

import java.io.Serializable;

/**
 *
 * @author MANUELA
 */
public abstract class Persona implements Serializable {

    private String usuario;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public abstract boolean proceder(SistemaCatering sistemaCatering);

    public abstract void mostrar();

    boolean coincidenUsrPwd(String datos) {
        return datos.equals(usuario + ":" + password);

    }
}
