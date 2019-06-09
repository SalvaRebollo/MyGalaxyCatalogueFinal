package salvi.rb;

import org.apache.wicket.markup.html.form.FormComponentLabel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OldPublicMainProducts extends WebPage {
    private static final long serialVersionUID = 1L;

    public OldPublicMainProducts(final PageParameters parameters) throws SQLException, ClassNotFoundException {
        super(parameters);


        // TODO Add your page's components here


        BbddConection();


    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        // TODO Add your page's components here

       /* for (int i = 0; i < 7; i++) {
            String confirmation = "producto[" + i + "]";

            //final IModel<LocalDate> dateModel = new PropertyModel<>(getModel(), confirmation + ".date");

            final TextField<String> textField = newInvoiceableHours(confirmation + ".invoiceableHours", i, dateModel);

            final FormComponentLabel label = new FormComponentLabel(confirmation + ".label", textField);
            label.add(new WeekendOrHolidayBehavior(dateModel));

            textField.setLabel(new DatumModel(dateModel, "EE d.M", i + 1));
            label.add(textField);
            form.add(label);
        }*/









        // Load usuariosAL from BBDD
        List<String[]> usuariosAL = BbddConection();

        // Create a data provider with the usuariosAL array list
        ListDataProvider<String[]> listDataProvider = new ListDataProvider<String[]>(usuariosAL);



        // Create an instance of DataView and fetching with the data provider "listDataProvider", we put the id 'rows' to call it in markup file
        DataView<String[]> dataView = CreateDataView(listDataProvider);


        // And them adding the dataView component to the application
        add(dataView);

        // With this addition we can move arround the dataView of usuariosAL page per page to view all the data
        // Only need puts a id and them call in the markup html file
        add(new PagingNavigator("pagingNavigator", dataView));

    }



    /**
     * === CreateDataView() ===
     * Este metodo contiene parte de la logica para crear el DataView
     *
     * @param dataProvider
     * @return Un DataView ya creado
     */
    public DataView<String[]> CreateDataView(ListDataProvider<String[]> dataProvider) {
        // Create an instance of DataView and fetching with the data provider "listDataProvider", we put the id 'rows' to call it in markup file
        DataView<String[]> dataView = new DataView<String[]>("rows", dataProvider) {

            // Method of the implemented DataView to fecth data and show the list
            @Override
            protected void populateItem(Item<String[]> item) {

                // Array of Strings Arrays to get row by row the paralell splited line (strings array)
                String[] usuariosArr = item.getModelObject();

                // Create an instance of RepeatingView to iterate all lines in the table and call by the id 'dataRow'
                RepeatingView repeatingView = new RepeatingView("dataRow");

                // Fecth the repeatingView instance in loop (to iterate line by line)
                for (int i = 0; i < usuariosArr.length; i++) {
                    repeatingView.add(new Label(repeatingView.newChildId(), usuariosArr[i]).setEscapeModelStrings(false));
                }

                // And finally add the repeatingView to our item
                item.add(repeatingView);
            }

        };

        // Setting the total of lines by page that show the table
        dataView.setItemsPerPage(15);

        return dataView;
    } // CreateDataView()

    /**
     * === BbddConection() ===
     * Este metodo llama a la bbdd para obtener la info de la tabla y archivarla en un ArrayList
     *
     * @return Una lista con todo el contenido de la bbdd
     */
    public List<String[]> BbddConection() {

        //HashMap<Integer, String> columnas = new HashMap<Integer, String>();

        List<String[]> usuariosList = new ArrayList<String[]>();
        String[] usuarioN = new String[10];

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/XigWZvmqYw", "XigWZvmqYw", "RP78oRmbYV");
            Statement n = conexion.createStatement();
            ResultSet listaUsersRS = n.executeQuery("SELECT * FROM usuario");


            ArrayList<String> listaAtributosAL = new ArrayList<String>(); //LISTA
            listaAtributosAL.add("UidUser");
            listaAtributosAL.add("NomUser");
            listaAtributosAL.add("ApeUser");
            listaAtributosAL.add("DirUser");
            listaAtributosAL.add("CiuUser");
            listaAtributosAL.add("PaisUser");
            listaAtributosAL.add("EmailUser");
            listaAtributosAL.add("FotoPerfilUser");
            listaAtributosAL.add("BioUser");
            listaAtributosAL.add("MetContactoUser");

            System.out.println("[DEBUGG] Tamaño listaAtributosAL.size()= " + listaAtributosAL.size());
            ArrayList<HashMap> filas = new ArrayList<HashMap>();
            //columnas = new HashMap<Integer, String>();

            while (listaUsersRS.next()) {

                for (int i = 0; i < listaAtributosAL.size(); i++) {

                    // SI ES FOTO DE PERFIL QUE SE PONGA LA ETIQUETA IMG
                    if (listaAtributosAL.get(i).equals("FotoPerfilUser")){
                        usuarioN[i] = "<img src=\"https://via.placeholder.com/150\" class=\"card-img-top\" alt=\"...\">";
                        //usuarioN[i] = "<img src=\""+listaUsersRS.getString(listaAtributosAL.get(i)+"\" class=\"card-img-top\" alt=\"...\">";
                    } else{
                        usuarioN[i] = listaUsersRS.getString(listaAtributosAL.get(i));
                    }

                    //columnas.put(i, "<td class='text-center'>" + listaUsersRS.getString(listaAtributosAL.get(i)) + "</td>");
                    System.out.println("[DEBUGG] Añadida columna [" + listaAtributosAL.get(i) + "] a la fila de usuarioN: - " + listaUsersRS.getString(listaAtributosAL.get(i)));
                }

                System.out.println(usuarioN);
                usuariosList.add(usuarioN);

                System.out.println("[DEBUGG] HASHMAP COLUMNA AÑADIDA AL ARRAYLIST DE FILAS");

                // Reseteamos la fila para volver a rellenarse
                usuarioN = new String[10];

                //columnas = new HashMap();
                System.out.println("[DEBUGG] CREADO NUEVA FILA");

            }


        } catch (ClassNotFoundException e) {
            System.out.println("CLASS NOT FOUND EXCEPTION UHHH");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQLEXCEPTION UHHH");
            e.printStackTrace();
        }

        return usuariosList;


    } //BbddConection()


}
