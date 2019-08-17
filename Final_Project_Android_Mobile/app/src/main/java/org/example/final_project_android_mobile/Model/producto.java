package org.example.final_project_android_mobile.Model;

public class producto {

    String ID;
    int imagen;
    String descripcion;
    String precio;

    public producto() {
    }

    public producto(String ID, int imagen, String descripcion, String precio) {
        this.ID = ID;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
