package com.ceviche.sareb.salvisapp.Clases;

public class Productos {

    String productoid; //La id se asigna al subirse a Firebase
    String usuarioCreadorUid; //La uid del usuario se obtiene y asignará sola al subirse a Firebase
    String titulo;
    String descripcion;
    String precio;
    String categoria;
    String estado;
    String imagen;

    String nombreUsuarioCreador;
    String fotoUsuarioCreador;


    public Productos() {
    }

    public Productos(String productoid, String usuarioCreadorUid, String titulo, String descripcion, String precio, String categoria, String estado, String imagen, String nombreUsuarioCreador, String fotoUsuarioCreador) {
        this.productoid = productoid;
        this.usuarioCreadorUid = usuarioCreadorUid;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.estado = estado;
        this.imagen = imagen;

        this.nombreUsuarioCreador = nombreUsuarioCreador;
        this.fotoUsuarioCreador = fotoUsuarioCreador;
    }

    public Productos(String titulo, String descripcion, String precio, String categoria, String estado, String imagen) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.estado = estado;
        this.imagen = imagen;
    }


    public String getProductoid() {
        return productoid;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getEstado() {
        return estado;
    }

    public String getImagen() {
        return imagen;
    }

    public String getUsuarioCreadorUid() {
        return usuarioCreadorUid;
    }

    public void setUsuarioCreadorUid(String usuarioCreadorUid) {
        this.usuarioCreadorUid = usuarioCreadorUid;
    }

    public String getNombreUsuarioCreador() {
        return nombreUsuarioCreador;
    }

    public void setNombreUsuarioCreador(String nombreUsuarioCreador) {
        this.nombreUsuarioCreador = nombreUsuarioCreador;
    }

    public String getFotoUsuarioCreador() {
        return fotoUsuarioCreador;
    }

    public void setFotoUsuarioCreador(String fotoUsuarioCreador) {
        this.fotoUsuarioCreador = fotoUsuarioCreador;
    }
}
