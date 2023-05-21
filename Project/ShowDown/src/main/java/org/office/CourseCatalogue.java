package org.office;

import org.misc.Connect;
import org.misc.InputValidation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class CourseCatalogue {

    Scanner scan = new Scanner(System.in);
    Connect conn = new Connect();

    public void courseList () {


        String in = "";

        do {

            System.out.println("1.Insert new course\n2.Delete an existing course");
            System.out.print("Input: ");
            in = scan.nextLine();



            if (!in.equals("1") && !in.equals("2")) {
                System.out.println("Invalid Input! Press try again");
            }

        } while (!in.equals("1") && !in.equals("2"));

        if ( in.equals("1"))
            courseInsert();

        if ( in.equals("2"))
            courseDelete();


    }

    public void courseInsert() {

        String code,name,l,t,p,s,c,type;


        System.out.print("Course code: ");
        code = scan.nextLine();

        System.out.print("Course name: ");
        name = scan.nextLine();

        System.out.print("Lecture: ");
        l = scan.nextLine();

        System.out.print("Tutorial: ");
        t = scan.nextLine();

        System.out.print("Practical: ");
        p = scan.nextLine();

        System.out.print("Study: ");
        s = scan.nextLine();

        System.out.print("Credits: ");
        c = scan.nextLine();

        System.out.print("Type (Core or Elective): ");
        type = scan.nextLine();


        String sql2 = "INSERT INTO course_catalog VALUES (?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement myStmt2 = conn.Database().prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            myStmt2.setString(1, code);
            myStmt2.setString(2, name);
            myStmt2.setString(3, l);
            myStmt2.setString(4, t);
            myStmt2.setString(5, p);
            myStmt2.setString(6, s);
            myStmt2.setString(7, c);
            myStmt2.setString(8,type);

            myStmt2.executeUpdate();

        } catch ( Exception e) {
            e.printStackTrace();
        }

        System.out.println("Course has been inserted successfully");
        OfficeOptions temp2 = new OfficeOptions();
        temp2.optionsOffice();

    }

    private void courseDelete() {

        String myCode;

        System.out.print("Course code: ");

        myCode = scan.nextLine();
        ResultSet rs = null;
        String sql3 = "SELECT * FROM course_catalog WHERE course_code = ?";
        try {
            PreparedStatement stmt1 = conn.Database().prepareStatement(sql3,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setString(1,myCode);

            rs = stmt1.executeQuery();

            if (rs.next() == false) {
                System.out.println("This course does not exist");
                OfficeOptions temp = new OfficeOptions();
                temp.optionsOffice();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }





        String sql2 = "DELETE FROM `course_catalog` WHERE `course_code` = ?";

        try {
            PreparedStatement myStmt2 = conn.Database().prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            myStmt2.setString(1, myCode);

            myStmt2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Course has been deleted from the catalogue");
        OfficeOptions temp = new OfficeOptions();
        temp.optionsOffice();
    }


}
