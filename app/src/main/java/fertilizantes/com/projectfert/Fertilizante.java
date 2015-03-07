package fertilizantes.com.projectfert;

import java.io.Serializable;

/**
 * Created by Christian Msc on 31/12/2014.
 */
public class Fertilizante implements Serializable {
    private int id;
    private String nombre;
    private String presentacion;
    private String link;
    private int estado;

    public Fertilizante(int id, String nombre, String presentacion, String link, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.link = link;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
