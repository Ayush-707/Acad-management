package org.office;

import org.misc.Connect;
import org.student.CurrentCGPA;

import java.io.File;
import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class GradeSheet {

    Scanner scan = new Scanner(System.in);
    Connect conn = new Connect();

//    public void grades(String mail,String semID, String courseCode) {
//
//
//        Scanner scan = new Scanner(System.in);
//        Connect conn = new Connect();
//        String grade = "";
//
//
//
//        String sql = "SELECT course_code,grade FROM student_courses WHERE semester_id = ? AND student_email = ?";
//        ResultSet rs1 = null;
//        try {
//            PreparedStatement stmt1 = conn.Database().prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
//            stmt1.setString(1,semID);
//            stmt1.setString(2,mail);
//
//            rs1 = stmt1.executeQuery();
//
//            if (!rs1.next()) {
//                System.out.println("No courses done for semesterID: "+semID);
//                OfficeOptions temp = new OfficeOptions();
//                temp.optionsOffice();
//            }
//            else {
//                rs1.previous();
//                System.out.println("Course Code | Grade");
//                System.out.println("-------------------");
//                while (rs1.next()) {
//                    courseCode = rs1.getString(1);
//                    grade = rs1.getString(2);
//
//                    System.out.println(courseCode + "   | " + grade);
//                }
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        OfficeOptions temp1 = new OfficeOptions();
//        temp1.optionsOffice();
//    }

    public void generateSheet() {

        System.out.print("Enter Student Email: ");
        String student = scan.nextLine();

        String sql1 = "SELECT email, name FROM students WHERE email = ?";

        try {

            PreparedStatement stmt1 = conn.Database().prepareStatement(sql1,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setString(1,student);
            ResultSet rs1 = stmt1.executeQuery();

            if (!rs1.next()) {
                System.out.println("Invalid Input! Student does not exist");
                OfficeOptions temp1 = new OfficeOptions();
                temp1.optionsOffice();
            }

            rs1.previous();
            String name = "";

            while(rs1.next()) {
                name = rs1.getString("name");
                break;
            }

            CurrentCGPA temp = new CurrentCGPA();
            double cg = temp.viewCGPA(student);

            String cgpa = Double.toString(cg);
            String str = "E:\\BTECH 3RD YEAR\\2nd Sem\\Software Engineering\\Mini Project\\Project\\ShowDown\\src\\main\\resources\\";
            String str2 = ".txt";
            String fileName = str + student + str2;
            File myObj = new File(fileName);

            if (!myObj.createNewFile()) {
                System.out.println("Grade-sheet already exists");
            }
            else {

                FileWriter myWriter = new FileWriter(fileName);
                myWriter.write("STUDENT TRANSCRIPT:-\n\nSTUDENT NAME: " + name + "\nSTUDENT E-MAIL: " + student + "\nCURRENT CGPA: " + cgpa);
                myWriter.close();
                System.out.println("Grad-sheet created");
            }



        } catch(Exception e) {
            e.printStackTrace();
        }





    }
}
