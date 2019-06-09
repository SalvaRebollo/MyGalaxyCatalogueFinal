package salvi.rb.myComponents;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.ContextRelativeResource;
import salvi.rb.myClasses.ProductUserTotalInfoPojo;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PanelMasonryProduct extends Panel {


    /**
     * Aqui van las variables que llevara la carta, eligiré las que serán visibles en la parte visual, y me traere el objeto entero
     * con su 17 atributos para poder elegir, luego en el constructor tendré que hacer todas las vinculaciones
     */
    private String imgProduct;
    private String tituloProduct;
    private String fotoPerfilUser;
    private String nomUser;
    private String descProduct;
    private String precioProduct;
    private String estadoProduct;
    private String categProduct;
    private String emailUser;
    private String metContactoUser;


    /**
     * @param id
     * @see Component#Component(String)
     */
    public PanelMasonryProduct(String id, Model<String> imgProduct, Model<String> tituloProduct, Model<String> fotoPerfilUser, Model<String> nomUser, Model<String> descProduct, Model<String> precioProduct, Model<String> estadoProduct, Model<String> categProduct, Model<String> emailUser, Model<String> metContactoUser) {
        super(id);
        this.imgProduct = imgProduct.getObject();
        this.tituloProduct = tituloProduct.getObject();
        this.fotoPerfilUser = fotoPerfilUser.getObject();
        this.nomUser = nomUser.getObject();
        this.descProduct = descProduct.getObject();
        this.precioProduct = precioProduct.getObject();
        this.estadoProduct = estadoProduct.getObject();
        this.categProduct = categProduct.getObject();
        this.emailUser = emailUser.getObject();
        this.metContactoUser = metContactoUser.getObject();






        Image imagenProducto = new Image("imagenProducto", "");
        imagenProducto.add(new AttributeModifier("src", imgProduct));
        imagenProducto.add(new AttributeModifier("title", tituloProduct));
        add(imagenProducto);


        add(new Label("tituloProducto", tituloProduct));
        add(new Label("descripcionProducto", descProduct));
        add(new Label("usuarioCreador", nomUser));

        Image fotoPerfilUse = new Image("fotoPerfilUser", "");
        fotoPerfilUse.add(new AttributeModifier("src", fotoPerfilUser));
        fotoPerfilUse.add(new AttributeModifier("title", nomUser));
        add(fotoPerfilUse);

        add(new Label("precioProducto", precioProduct));
        add(new Label("estadoProducto", estadoProduct));
        add(new Label("categoriaProducto", categProduct));

        //add(new Label("fotoPerfilUser", fotoPerfilUser));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        // TODO Add your page's components here








        // Load productsJoinUsersAL from BBDD





        //List<PanelCardProduct> listaPanelesBbdd = ListToListPanelCardProduct(BbddConection());

        //System.out.println(listaPanelesBbdd);


        /*add(new ListView<PanelCardProduct>("productsPanelListView", listaPanelesBbdd) {
            @Override
            protected void populateItem(ListItem<PanelCardProduct> item) {
                item.add(new PanelCardProduct("tituloProducto", new PropertyModel(item.getModel(), "titulo")));
            }
        });*/


    }

   @Override
   public String toString() { return "PanelCardProduct{" + "\nidPanel='" + getId() + '\'' + "\nimgProduct='" + imgProduct + '\'' + "\ntituloProduct='" + tituloProduct + '\'' + "\nfotoPerfilUser='" + fotoPerfilUser + '\'' + "\nnomUser='" + nomUser + '\'' + "\ndescProduct='" + descProduct + '\'' + "\nprecioProduct='" + precioProduct + '\'' + "\nestadoProduct='" + estadoProduct + '\'' + "\ncategProduct='" + categProduct + '\'' + "\nemailUser='" + emailUser + '\'' + "\nmetContactoUser='" + metContactoUser + '\'' + "\n}\n\n"; }


    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getTituloProduct() {
        return tituloProduct;
    }

    public void setTituloProduct(String tituloProduct) {
        this.tituloProduct = tituloProduct;
    }

    public String getFotoPerfilUser() {
        return fotoPerfilUser;
    }

    public void setFotoPerfilUser(String fotoPerfilUser) {
        this.fotoPerfilUser = fotoPerfilUser;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
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

    public String getEstadoProduct() {
        return estadoProduct;
    }

    public void setEstadoProduct(String estadoProduct) {
        this.estadoProduct = estadoProduct;
    }

    public String getCategProduct() {
        return categProduct;
    }

    public void setCategProduct(String categProduct) {
        this.categProduct = categProduct;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getMetContactoUser() {
        return metContactoUser;
    }

    public void setMetContactoUser(String metContactoUser) {
        this.metContactoUser = metContactoUser;
    }
}
