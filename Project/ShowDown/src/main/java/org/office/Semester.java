package org.office;

import org.misc.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Semester {

    Scanner scan = new Scanner(System.in);
    Connect conn = new Connect();
    public void semRelease() {

        String in = "";

        do {

            System.out.println("1. Start Semester\n2. Terminate Semester");
            System.out.print("Input: ");
            in = scan.nextLine();



            if (!in.equals("1") && !in.equals("2")) {
                System.out.println("Invalid Input! Press try again");
            }

        } while (!in.equals("1") && !in.equals("2"));

        if ( in.equals("1"))
            semInsert();

        if ( in.equals("2"))
            semTerminate();

    }

    private void semInsert() {

        String id,title;


        System.out.print("Semester ID: ");
        id = scan.nextLine();

        System.out.print("Semester title: ");
        title = scan.nextLine();


        String sql2 = "INSERT INTO semester VALUES (?,?,?)";

        try {
            PreparedStatement myStmt2 = conn.Database().prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            myStmt2.setString(1, id);
            myStmt2.setString(2, title);
            myStmt2.setString(3, "1");

            myStmt2.executeUpdate();

        } catch ( Exception e) {
            e.printStackTrace();
        }

        System.out.println("Semester has started successfully");
        OfficeOptions temp2 = new OfficeOptions();
        temp2.optionsOffice();

    }

    private void semTerminate() {

        String id;

        System.out.print("Semester ID: ");

        id = scan.nextLine();

        ResultSet rs = null;

        String sql3 = "SELECT * FROM semester WHERE id = ?";

        try {
            PreparedStatement stmt1 = conn.Database().prepareStatement(sql3,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setString(1,id);

            rs = stmt1.executeQuery();

            if (!rs.next()) {
                System.out.println("This semester does not exist");
                OfficeOptions temp = new OfficeOptions();
                temp.optionsOffice();
            }

            else {

                rs.beforeFirst();

                String sql2 = "UPDATE semester SET status = ? WHERE id = ?";


                PreparedStatement myStmt2 = conn.Database().prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

                myStmt2.setString(1, "0");
                myStmt2.setString(2, id);

                myStmt2.executeUpdate();

                System.out.println("Semester has been terminated.");
                OfficeOptions temp = new OfficeOptions();
                temp.optionsOffice();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
