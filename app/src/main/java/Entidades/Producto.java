package Entidades;

import android.graphics.Bitmap;

public class Producto {
    private int id;
    private Comercio comercio;
    private String nombre;
    private String descripcion;
    private Float precio;
    private int stock;
    private boolean estado;
    private String img;
    private Bitmap bitmapImage;

    public Producto(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public Float getPrecio() {
        return precio;
    }

    public Bitmap getBitmapImage() {
        return bitmapImage;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
