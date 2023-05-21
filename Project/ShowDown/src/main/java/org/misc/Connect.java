package org.misc;

import java.sql.*;
public class Connect {

    public Connection Database () throws ClassNotFoundException {
        String user,pass,url;

        Connection conn = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            url = "jdbc:mysql://localhost:3306/academics";
            user = "root";
            pass = "";
            conn = DriverManager.getConnection(url,user,pass);


        } catch (Exception e) {

            e.printStackTrace();
        }

        return conn;
    }
}


