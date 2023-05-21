package org.student;


import org.misc.Connect;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UpdatePass {

    Scanner scan = new Scanner(System.in);
    public void passUpdate(String mail) {

        System.out.print("Enter new Password: ");
        String sql = "UPDATE students SET password = ? WHERE email = ?";
        String sql2 = "UPDATE current_student SET password = ? WHERE email = ?";
        String newPass = scan.nextLine();

        Connect conn = new Connect();
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        try{
            stmt = conn.Database().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1,newPass);
            stmt.setString(2,mail);

            stmt.executeUpdate();

            stmt2 = conn.Database().prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt2.setString(1,newPass);
            stmt2.setString(2,mail);

            stmt2.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("Password has been updated successfully");

        StudentOptions temp = new StudentOptions();
        temp.optionsStudent();
    }
}
