package org.teacher;

import org.misc.Connect;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CourseOfferDelete {

    Scanner scan = new Scanner(System.in);
    Connect conn = new Connect();

    LoggedinMail nen = new LoggedinMail();
    String teacher = nen.getEmail();
    public void deleteOfferCourse() {

        String in = "";

        do {

            System.out.println("1.Offer course\n2.Rollback offered course");
            System.out.print("Input: ");
            in = scan.nextLine();



            if (!in.equals("1") && !in.equals("2")) {
                System.out.println("Invalid Input! Press try again");
            }

        } while (!in.equals("1") && !in.equals("2"));

        if ( in.equals("1"))
            courseOffer();

        if ( in.equals("2"))
            courseRollback();


    }

    private void courseOffer() {

        String code,minCGPA,semID;
        int y = 0;


//
//
//

        System.out.print("Semester ID: ");
        semID = scan.nextLine();

        String sql = "SELECT id FROM semester WHERE status = '1' ";


        try{
            Statement stmt = conn.Database().createStatement();
            ResultSet rs1 = stmt.executeQuery(sql);
            String runningSem = "";

            while(rs1.next()) {
                runningSem = rs1.getString("id");

                if( runningSem.equals(semID)) {
                    y++;
                }
            }

            if ( y == 0 ) {
                System.out.println("This semester is not running currently");
                deleteOfferCourse();
            }

        } catch ( Exception e) {
            e.printStackTrace();
        }

        System.out.print("Course code: ");
        code = scan.nextLine();

        ResultSet rs = null;

        String sql3 = "SELECT * FROM course_catalog WHERE course_code = ?";
        try {
            PreparedStatement stmt1 = conn.Database().prepareStatement(sql3,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setString(1,code);

            rs = stmt1.executeQuery();

            if (!rs.next()) {
                System.out.println("This course does not exist in the catalogue");
                FacultyOptions temp = new FacultyOptions();
                temp.optionsFaculty();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.print("Minimum CGPA: ");
        minCGPA = scan.nextLine();

        String sql2 = "INSERT INTO offered_courses VALUES (?,?,?,?)";

        try {
            PreparedStatement myStmt2 = conn.Database().prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            myStmt2.setString(1, teacher);
            myStmt2.setString(2, code);
            myStmt2.setString(3, semID);
            myStmt2.setString(4, minCGPA);
            myStmt2.executeUpdate();

        } catch ( Exception e) {
            e.printStackTrace();
        }

        System.out.println("Course has been offered successfully");
        FacultyOptions temp2 = new FacultyOptions();
        temp2.optionsFaculty();
    }

    private void courseRollback() {

        String myCode;

        System.out.print("Course code: ");

        myCode = scan.nextLine();
        ResultSet rs = null;
        String sql3 = "SELECT * FROM offered_courses WHERE course_code = ? AND faculty_email = ?";
        try {
            PreparedStatement stmt1 = conn.Database().prepareStatement(sql3,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setString(1,myCode);
            stmt1.setString(2,teacher);


            rs = stmt1.executeQuery();

            if (!rs.next()) {
                System.out.println("You have not offered this course");
                FacultyOptions temp = new FacultyOptions();
                temp.optionsFaculty();
            }

            else {

                String sql2 = "DELETE FROM `offered_courses` WHERE `course_code` = ? AND faculty_email = ?";


                PreparedStatement myStmt2 = conn.Database().prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

                myStmt2.setString(1, myCode);
                myStmt2.setString(2,teacher);

                myStmt2.executeUpdate();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Course has been deleted from the list of offered courses");
        FacultyOptions temp = new FacultyOptions();
        temp.optionsFaculty();
    }
}
