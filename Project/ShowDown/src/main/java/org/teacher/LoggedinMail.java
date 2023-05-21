package org.teacher;

import org.misc.Connect;

import java.sql.ResultSet;
import java.sql.Statement;

public class LoggedinMail {
    //public static void main(String[] args) {


    public String getEmail() {

        String sql = "SELECT email FROM current_faculty";
        Connect conn = new Connect();
        ResultSet rs = null;
        String mailId = "";
        try {

            Statement stmt = conn.Database().createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                mailId = rs.getString("email");
                break;
            }


        } catch(Exception e) {
            e.printStackTrace();
        }

        return mailId;
    }
}
