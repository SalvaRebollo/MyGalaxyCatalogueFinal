package salvi.rb.myClasses;

import java.io.Serializable;

public class ProductPojo implements Serializable {

    private String idProduct;
    private String uidUser;
    private String tituloProduct;
    private String descProduct;
    private String precioProduct;
    private String imgProduct;
    private String categProduct;
    private String estadoProduct;

    public ProductPojo(String idProduct, String uidUser, String tituloProduct, String descProduct, String precioProduct, String imgProduct, String categProduct, String estadoProduct) {
        this.idProduct = idProduct;
        this.uidUser = uidUser;
        this.tituloProduct = tituloProduct;
        this.descProduct = descProduct;
        this.precioProduct = precioProduct;
        this.imgProduct = imgProduct;
        this.categProduct = categProduct;
        this.estadoProduct = estadoProduct;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public String getTituloProduct() {
        return tituloProduct;
    }

    public void setTituloProduct(String tituloProduct) {
        this.tituloProduct = tituloProduct;
    }

    public String getDescProduct() {
        return descProduct;
    }

    public void setDescProduct(String descProduct) {
        this.descProduct = descProduct;
    }

    public String getPrecioProduct() {
        return precioProduct;
    }

    public void setPrecioProduct(String precioProduct) {
        this.precioProduct = precioProduct;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getCategProduct() {
        return categProduct;
    }

    public void setCategProduct(String categProduct) {
        this.categProduct = categProduct;
    }

    public String getEstadoProduct() {
        return estadoProduct;
    }

    public void setEstadoProduct(String estadoProduct) {
        this.estadoProduct = estadoProduct;
    }
}
