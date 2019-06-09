package salvi.rb.myClasses.noUso;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseConnection {




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




