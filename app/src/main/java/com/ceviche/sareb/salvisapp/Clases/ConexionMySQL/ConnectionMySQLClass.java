package com.ceviche.sareb.salvisapp.Clases.ConexionMySQL;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQLClass {

    String classDb = "com.mysql.jdbc.Driver";
    String urlDb = "jdbc:mysql://remotemysql.com:3306/XigWZvmqYw";
    String usrDb = "XigWZvmqYw";
    String pwdDb = "RP78oRmbYV";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;

        try {

            Class.forName(classDb);

            conn = DriverManager.getConnection(urlDb, usrDb, pwdDb);

            conn = DriverManager.getConnection(ConnURL);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("ERROClassNotFoundException", e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("ERROSQLException", e.getMessage());
        }

        return conn;
    }


}
