package org.office;

import org.misc.Connect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class StudentGrades {
    Scanner scan = new Scanner(System.in);
    Connect conn = new Connect();
    public void gradeView () {
        System.out.print("Enter student email: ");
        String studentMail = scan.nextLine();

        String sql1 = "SELECT email FROM students WHERE email = ?";

        try {

            PreparedStatement stmt1 = conn.Database().prepareStatement(sql1,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setString(1, studentMail);
            ResultSet rs1 = stmt1.executeQuery();

            if (!rs1.next()) {
                System.out.println("Invalid Input! Student does not exist");
                OfficeOptions temp1 = new OfficeOptions();
                temp1.optionsOffice();
            }
            else {


                grades(studentMail);

            }
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    public void grades(String mail) {


        Scanner scan = new Scanner(System.in);
        Connect conn = new Connect();

        System.out.print("Enter Semester ID: ");
        String semID = "";
        semID = scan.nextLine();
        String courseCode = "";
        String grade = "";



        String sql = "SELECT course_code,grade FROM student_courses WHERE semester_id = ? AND student_email = ?";
        ResultSet rs1 = null;
        try {
            PreparedStatement stmt1 = conn.Database().prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setString(1,semID);
            stmt1.setString(2,mail);

            rs1 = stmt1.executeQuery();

            if (!rs1.next()) {
                System.out.println("No courses done for semesterID: "+semID);
                OfficeOptions temp = new OfficeOptions();
                temp.optionsOffice();
            }
            else {
                rs1.previous();
                System.out.println("Course Code | Grade");
                System.out.println("-------------------");
                while (rs1.next()) {
                    courseCode = rs1.getString(1);
                    grade = rs1.getString(2);

                    System.out.println(courseCode + "   | " + grade);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        OfficeOptions temp1 = new OfficeOptions();
        temp1.optionsOffice();
    }

}
