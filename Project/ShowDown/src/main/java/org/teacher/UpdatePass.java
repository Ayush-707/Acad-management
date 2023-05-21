package org.teacher;

import org.misc.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UpdatePass {

    Scanner scan = new Scanner(System.in);
    public void passUpdate(String mail) {

        System.out.print("Enter new Password: ");
        String sql = "UPDATE faculty SET password = ? WHERE email = ?";
        String newPass = scan.nextLine();

        Connect conn = new Connect();
        PreparedStatement stmt = null;
        try{
            stmt = conn.Database().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1,newPass);
            stmt.setString(2,mail);

            stmt.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("Password has been updated successfully");

        FacultyOptions temp = new FacultyOptions();
        temp.optionsFaculty();
    }
}
