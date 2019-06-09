package salvi.rb.myPages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import salvi.rb.myClasses.ProductUserTotalInfoPojo;
import salvi.rb.myComponents.PanelCardProduct;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AllTheProductsWebPage extends WebPage {
    private static final long serialVersionUID = 1L;

    public AllTheProductsWebPage(final PageParameters parameters) throws SQLException, ClassNotFoundException {
        super(parameters);
        // TODO Add your page's components here


    }


    @Override
    protected void onInitialize() {
        super.onInitialize();
        // TODO Add your page's components here
        Label title = new Label("title", "MyGalaxyCatalogue Products List");
        add(title);

        // Load productsJoinUsersAL from BBDD
        List<ProductUserTotalInfoPojo> listaDatosEnObjeto = BbddConection();

        System.out.println("\n----------------------------\nListaDatosEnObjeto:\n" + listaDatosEnObjeto);

        List<PanelCardProduct> listaPanelCardProduct = ListOfObjectToListPanelCardProduct(listaDatosEnObjeto);

        System.out.println("\n----------------------------\nlistaPanelCardProduct:\n" + listaPanelCardProduct);


        add(new ListView<PanelCardProduct>("productsPanels", listaPanelCardProduct) {
            @Override
            protected void populateItem(ListItem<PanelCardProduct> item) {
                item.add(new PanelCardProduct(
                        "panelX",
                        new Model<String>(item.getModelObject().getImgProduct()),
                        new Model<String>(item.getModelObject().getTituloProduct()),
                        new Model<String>(item.getModelObject().getFotoPerfilUser()),
                        new Model<String>(item.getModelObject().getNomUser()),
                        new Model<String>(item.getModelObject().getDescProduct()),
                        new Model<String>(item.getModelObject().getPrecioProduct()),
                        new Model<String>(item.getModelObject().getEstadoProduct()),
                        new Model<String>(item.getModelObject().getCategProduct()),
                        new Model<String>(item.getModelObject().getEmailUser()),
                        new Model<String>(item.getModelObject().getMetContactoUser())


                ));
            }
        });


    }

    /**
     * === BbddConection() ===
     * Este metodo llama a la bbdd para obtener la info de la tabla y archivarla en un ArrayList
     *
     * @return Una lista con todo el contenido de la bbdd
     */

    public List<ProductUserTotalInfoPojo> BbddConection() {

        //HashMap<Integer, String> columnas = new HashMap<Integer, String>();

        List<ProductUserTotalInfoPojo> productsJoinUsersList = new ArrayList<ProductUserTotalInfoPojo>();
        ProductUserTotalInfoPojo productUserTotalInfoPojoRabbit = new ProductUserTotalInfoPojo();

        String query = "SELECT p.FechaCreacion, p.IdProduct, p.TituloProduct, u.UidUser, u.NomUser, u.ApeUser, p.DescProduct, p.PrecioProduct, p.ImgProduct, p.CategProduct, p.EstadoProduct, u.DirUser, u.CiuUser, u.PaisUser, u.EmailUser, u.FotoPerfilUser, u.BioUser, u.MetContactoUser FROM usuario u INNER JOIN producto p ON u.UidUser = p.UidUser ORDER BY p.FechaCreacion DESC";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/XigWZvmqYw", "XigWZvmqYw", "RP78oRmbYV");
            Statement n = conexion.createStatement();
            ResultSet listProductsJoinUsersRS = n.executeQuery(query);


            while (listProductsJoinUsersRS.next()) {

                productUserTotalInfoPojoRabbit.setIdProduct(listProductsJoinUsersRS.getString("IdProduct"));
                productUserTotalInfoPojoRabbit.setTituloProduct(listProductsJoinUsersRS.getString("TituloProduct"));
                productUserTotalInfoPojoRabbit.setUidUser(listProductsJoinUsersRS.getString("UidUser"));
                productUserTotalInfoPojoRabbit.setNomUser(listProductsJoinUsersRS.getString("NomUser"));
                productUserTotalInfoPojoRabbit.setApeUser(listProductsJoinUsersRS.getString("ApeUser"));
                productUserTotalInfoPojoRabbit.setDescProduct(listProductsJoinUsersRS.getString("DescProduct"));
                productUserTotalInfoPojoRabbit.setPrecioProduct(listProductsJoinUsersRS.getString("PrecioProduct"));
                productUserTotalInfoPojoRabbit.setImgProduct(listProductsJoinUsersRS.getString("ImgProduct"));
                //productUserTotalInfoPojoRabbit.setImgProduct("<img src=\"https://via.placeholder.com/150\" class=\"card-img-top\" alt=\"...\">");
                productUserTotalInfoPojoRabbit.setCategProduct(listProductsJoinUsersRS.getString("CategProduct"));
                productUserTotalInfoPojoRabbit.setEstadoProduct(listProductsJoinUsersRS.getString("EstadoProduct"));
                productUserTotalInfoPojoRabbit.setDirUser(listProductsJoinUsersRS.getString("DirUser"));
                productUserTotalInfoPojoRabbit.setCiuUser(listProductsJoinUsersRS.getString("CiuUser"));
                productUserTotalInfoPojoRabbit.setPaisUser(listProductsJoinUsersRS.getString("PaisUser"));
                productUserTotalInfoPojoRabbit.setEmailUser(listProductsJoinUsersRS.getString("EmailUser"));
                //productUserTotalInfoPojoRabbit.setFotoPerfilUser(listProductsJoinUsersRS.getString("FotoPerfilUser"));
                productUserTotalInfoPojoRabbit.setFotoPerfilUser("<img src=\"https://via.placeholder.com/150\" class=\"card-img-top\" alt=\"...\">");
                productUserTotalInfoPojoRabbit.setBioUser(listProductsJoinUsersRS.getString("BioUser"));
                productUserTotalInfoPojoRabbit.setMetContactoUser(listProductsJoinUsersRS.getString("MetContactoUser"));

                productsJoinUsersList.add(productUserTotalInfoPojoRabbit);

                // Reseteamos la fila para volver a rellenarse
                productUserTotalInfoPojoRabbit = new ProductUserTotalInfoPojo();


            }

            System.out.println("LISTA ProductUserTotalInfoPojo creada ");

        } catch (ClassNotFoundException e) {
            System.out.println("CLASS NOT FOUND EXCEPTION UHHH");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQLEXCEPTION UHHH");
            e.printStackTrace();
        }

        return productsJoinUsersList;

    } //BbddConection()

    /**
     * === ListOfObjectToListPanelCardProduct(List<ProductUserTotalInfoPojo> listProductUserTotalInfoPojo) ===
     * Este metodo transformará un arrayList del objeto ProductUserTotalInfoPojo en un ArrayList de paneles ya maquetados para su iteracion
     *
     * @return Una lista con todo el contenido de la bbdd TRANSFORMADA EN PANELES
     */
    public List<PanelCardProduct> ListOfObjectToListPanelCardProduct(final List<ProductUserTotalInfoPojo> listProductUserTotalInfoPojo) {

        List<PanelCardProduct> listPanelCardProduct = new ArrayList<PanelCardProduct>();


        int i = 0;
        String wickectIDs = "panelCardProduct" + i;

        for (ProductUserTotalInfoPojo productUserTotalInfoPojoN : listProductUserTotalInfoPojo) {

            final int finalI = i;

            if(listProductUserTotalInfoPojo.get(finalI).getImgProduct().equals("SINFOTO")){

                listPanelCardProduct.add(
                        new PanelCardProduct(
                                wickectIDs,
                                new Model<String>("https://via.placeholder.com/150"),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getTituloProduct()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getFotoPerfilUser()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getNomUser()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getDescProduct()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getPrecioProduct()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getEstadoProduct()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getCategProduct()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getEmailUser()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getMetContactoUser())
                        )
                );

            } else{
                listPanelCardProduct.add(
                        new PanelCardProduct(
                                wickectIDs,
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getImgProduct()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getTituloProduct()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getFotoPerfilUser()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getNomUser()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getDescProduct()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getPrecioProduct()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getEstadoProduct()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getCategProduct()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getEmailUser()),
                                new Model<String>(listProductUserTotalInfoPojo.get(finalI).getMetContactoUser())
                        )
                );
            }



            i++;
            wickectIDs = "panelCardProduct" + i;
        }


        return listPanelCardProduct;

    }


}
