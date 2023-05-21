package org.student;

import org.misc.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ViewGrades {


    public void gradeView(String mail) {

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
                StudentOptions temp = new StudentOptions();
                temp.optionsStudent();
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

        StudentOptions temp1 = new StudentOptions();
        temp1.optionsStudent();
    }
}
