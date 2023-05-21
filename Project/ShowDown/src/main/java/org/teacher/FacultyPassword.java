package org.teacher;

import org.misc.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FacultyPassword {
    Connect conn = new Connect();
    public String getFacPass(String mail) {
        String sql = "SELECT password FROM faculty WHERE email = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        String pass = "";

        try {


            stmt = conn.Database().prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1,mail);

            rs = stmt.executeQuery();

            while (rs.next()) {
                pass = rs.getString("password");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return pass;




    }
}
