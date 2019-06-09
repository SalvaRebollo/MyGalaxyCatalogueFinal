package salvi.rb.myClasses;

import org.apache.wicket.model.IModel;

import java.io.Serializable;

public class ProductUserTotalInfoPojo implements Serializable{

    private String idProduct;
    private String tituloProduct;
    private String uidUser;
    private String nomUser;
    private String apeUser;
    private String descProduct;
    private String precioProduct;
    private String imgProduct;
    private String categProduct;
    private String estadoProduct;
    private String dirUser;
    private String ciuUser;
    private String paisUser;
    private String emailUser;
    private String fotoPerfilUser;
    private String bioUser;
    private String metContactoUser;


    public ProductUserTotalInfoPojo(String idProduct, String tituloProduct, String uidUser, String nomUser, String apeUser, String descProduct, String precioProduct, String imgProduct, String categProduct, String estadoProduct, String dirUser, String ciuUser, String paisUser, String emailUser, String fotoPerfilUser, String bioUser, String metContactoUser) {
        this.idProduct = idProduct;
        this.tituloProduct = tituloProduct;
        this.uidUser = uidUser;
        this.nomUser = nomUser;
        this.apeUser = apeUser;
        this.descProduct = descProduct;
        this.precioProduct = precioProduct;
        this.imgProduct = imgProduct;
        this.categProduct = categProduct;
        this.estadoProduct = estadoProduct;
        this.dirUser = dirUser;
        this.ciuUser = ciuUser;
        this.paisUser = paisUser;
        this.emailUser = emailUser;
        this.fotoPerfilUser = fotoPerfilUser;
        this.bioUser = bioUser;
        this.metContactoUser = metContactoUser;
    }

    public ProductUserTotalInfoPojo() {
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getTituloProduct() {
        return tituloProduct;
    }

    public void setTituloProduct(String tituloProduct) {
        this.tituloProduct = tituloProduct;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getApeUser() {
        return apeUser;
    }

    public void setApeUser(String apeUser) {
        this.apeUser = apeUser;
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

    public String getDirUser() {
        return dirUser;
    }

    public void setDirUser(String dirUser) {
        this.dirUser = dirUser;
    }

    public String getCiuUser() {
        return ciuUser;
    }

    public void setCiuUser(String ciuUser) {
        this.ciuUser = ciuUser;
    }

    public String getPaisUser() {
        return paisUser;
    }

    public void setPaisUser(String paisUser) {
        this.paisUser = paisUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getFotoPerfilUser() {
        return fotoPerfilUser;
    }

    public void setFotoPerfilUser(String fotoPerfilUser) {
        this.fotoPerfilUser = fotoPerfilUser;
    }

    public String getBioUser() {
        return bioUser;
    }

    public void setBioUser(String bioUser) {
        this.bioUser = bioUser;
    }

    public String getMetContactoUser() {
        return metContactoUser;
    }

    public void setMetContactoUser(String metContactoUser) {
        this.metContactoUser = metContactoUser;
    }

    @Override
    public String toString() {
        return "ProductUserTotalInfoPojo{" +
                "\nidProduct='" + idProduct + '\'' +
                "\ntituloProduct='" + tituloProduct + '\'' +
                "\nuidUser='" + uidUser + '\'' +
                "\nnomUser='" + nomUser + '\'' +
                "\napeUser='" + apeUser + '\'' +
                "\ndescProduct='" + descProduct + '\'' +
                "\nprecioProduct='" + precioProduct + '\'' +
                "\nimgProduct='" + imgProduct + '\'' +
                "\ncategProduct='" + categProduct + '\'' +
                "\nestadoProduct='" + estadoProduct + '\'' +
                "\ndirUser='" + dirUser + '\'' +
                "\nciuUser='" + ciuUser + '\'' +
                "\npaisUser='" + paisUser + '\'' +
                "\nemailUser='" + emailUser + '\'' +
                "\nfotoPerfilUser='" + fotoPerfilUser + '\'' +
                "\nbioUser='" + bioUser + '\'' +
                "\nmetContactoUser='" + metContactoUser + '\'' +
                "\n}\n\n";
    }


}
