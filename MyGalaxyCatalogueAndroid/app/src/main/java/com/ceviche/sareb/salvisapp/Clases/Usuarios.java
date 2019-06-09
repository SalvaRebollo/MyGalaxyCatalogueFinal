package com.ceviche.sareb.salvisapp.Clases;

//public class Amigos {
public class Usuarios {


    String usuarioUid = "";
    String email = "";
    String nombre = "";
    String apellidos = "";
    String fotoPerfil = "";
    String biografia = "";
    String pais = "";
    String ciudad = "";
    String direccion = "";
    String tlfContacto = "";

    public Usuarios() { // Necesario
    }

    public Usuarios(String email, String usuarioUid) {
        this.email = email;
        this.usuarioUid = usuarioUid;
        this.nombre = "";
        this.apellidos = "";
        this.fotoPerfil = "";
        this.biografia = "";
        this.pais = "";
        this.ciudad = "";
        this.direccion = "";
    }

    // USADO EN REGISTRO
    public Usuarios(String email, String usuarioUid, String nombre, String apellidos, String pais, String ciudad, String direccion, String tlfContacto) {
        this.email = email;
        this.usuarioUid = usuarioUid;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fotoPerfil = "";
        this.biografia = "";
        this.pais = pais;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.tlfContacto = tlfContacto;
    }


    // USADO EN ACTUALIZACION
    public Usuarios(String email, String usuarioUid, String nombre, String apellidos, String fotoPerfil, String biografia, String pais, String ciudad, String direccion, String tlfContacto) {
        this.email = email;
        this.usuarioUid = usuarioUid;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fotoPerfil = fotoPerfil;
        this.biografia = biografia;
        this.pais = pais;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.tlfContacto = tlfContacto;
    }


    public String getTlfContacto() {
        return tlfContacto;
    }

    public void setTlfContacto(String tlfContacto) {
        this.tlfContacto = tlfContacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUsuarioUid() {
        return usuarioUid;
    }

    public void setUsuarioUid(String usuarioUid) {
        this.usuarioUid = usuarioUid;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "usuarioUid='" + usuarioUid + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                ", biografia='" + biografia + '\'' +
                ", pais='" + pais + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", direccion='" + direccion + '\'' +
                ", tlfContacto='" + tlfContacto + '\'' +
                '}';
    }
}
//}