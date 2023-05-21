package org.teacher;

import org.misc.Connect;
import org.misc.InputValidation;
import org.misc.Login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class FacultyAuth {

    Scanner scan = new Scanner(System.in);

    public String getEmail() {
        System.out.println("Enter Faculty Email:-");
        System.out.print("Input: ");
        String in = "";
        in = scan.nextLine();
        return in;
    }
    public void passAuth() {


        InputValidation var2 = new InputValidation();

        String sql = "SELECT email FROM faculty";
        String in = "",mail = "",inPass = "";
        in = getEmail();

        Connect conn = new Connect();
        ResultSet rs = null;
        Statement stmt = null;
        int x = 0;
        try {
            stmt = conn.Database().createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()) {

                mail = rs.getString("email");
                if (mail.equals(in)){
                    x++;
                    break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if ( x == 0) {
            System.out.println("Faculty email not available in database");
            Login var1 = new Login();
            var1.doLogin();
        }

        if ( x == 1) {
            FacultyPassword stu = new FacultyPassword();
            InputValidation val = new InputValidation();

            String correctPass = stu.getFacPass( mail );
            System.out.println("Enter Faculty Password:-");
            inPass = val.validateInput(inPass,correctPass);

            String sql3 = "DELETE FROM current_faculty";
            String sql2 = "INSERT INTO `current_faculty` (email,password) VALUES (?,?)";
            try {

                Statement stmt3 = conn.Database().createStatement();
                stmt3.executeUpdate(sql3);

                PreparedStatement stmt1 = conn.Database().prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

                stmt1.setString(1,in);
                stmt1.setString(2,inPass);
                stmt1.executeUpdate();

            } catch (Exception e) {

                e.printStackTrace();
            }

            FacultyOptions y = new FacultyOptions();
            y.optionsFaculty();


        }

    }
}
