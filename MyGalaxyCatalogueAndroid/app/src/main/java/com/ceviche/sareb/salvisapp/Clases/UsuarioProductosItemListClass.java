package com.ceviche.sareb.salvisapp.Clases;

public class UsuarioProductosItemListClass {

    private String titulo;
    private String imagen;
    private String descripcion;
    private String precio;
    private String categoria;
    private String estado;

    private String nombreUsuarioCreador;
    private String fotoUsuarioCreador;
    private String usuarioCreadorUid;

    private String productoid;

    public UsuarioProductosItemListClass() {

    }

    public UsuarioProductosItemListClass(String titulo, String imagen, String descripcion, String precio, String categoria, String estado, String nombreUsuarioCreador, String fotoUsuarioCreador, String usuarioCreadorUid, String productoid) {
        this.titulo = titulo;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.estado = estado;
        this.nombreUsuarioCreador = nombreUsuarioCreador;
        this.fotoUsuarioCreador = fotoUsuarioCreador;
        this.usuarioCreadorUid = usuarioCreadorUid;
        this.productoid = productoid;
    }

    public String getProductoid() {
        return productoid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUsuarioCreadorUid() {
        return usuarioCreadorUid;
    }

    public void setUsuarioCreadorUid(String usuarioCreadorUid) {
        this.usuarioCreadorUid = usuarioCreadorUid;
    }

    public String getimagen() {
        return imagen;
    }

    public void setimagen(String imagen) {
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreUsuarioCreador() {
        return nombreUsuarioCreador;
    }

    public void setNombreUsuarioCreadore(String nombreUsuarioCreador) {
        this.nombreUsuarioCreador = nombreUsuarioCreador;
    }

    public String getFotoUsuarioCreador() {
        return fotoUsuarioCreador;
    }

    public void setFotoUsuarioCreador(String fotoUsuarioCreador) {
        this.fotoUsuarioCreador = fotoUsuarioCreador;
    }
}
