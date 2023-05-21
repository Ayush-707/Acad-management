package org.student;

import org.misc.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class CourseEnrollment {

    Scanner scan = new Scanner(System.in);
    Connect conn = new Connect();
    public void enrollCourse() {

        String in = "";

        do {

            System.out.println("1.Register\n2.De-Register");
            System.out.print("Input: ");
            in = scan.nextLine();



            if (!in.equals("1") && !in.equals("2")) {
                System.out.println("Invalid Input! Press try again");
            }

        } while (!in.equals("1") && !in.equals("2"));

        if ( in.equals("1"))
            courseRegister();

        if ( in.equals("2"))
            courseDeregister();

    }



    private void courseRegister() {

        String code = "",id = "",min_cg="",user_cg="";

        System.out.print("Course code: ");
        code = scan.nextLine();

        ResultSet rs3 = null,rs4=null,rs5=null,rs6=null,rs7=null;
        int y = 0,statusCount = 0;

        String sql3 = "SELECT * FROM offered_courses WHERE course_code = ?";
        String sql4 = "SELECT  semester_id FROM offered_courses WHERE course_code = ?";
        String sql5 = "SELECT min_cgpa FROM offered_courses WHERE course_code = ?";
        String sql6 = "SELECT current_cgpa FROM students WHERE email = ?";
        String sql7 = "SELECT status FROM semester WHERE id = ?";


        try {

            PreparedStatement stmt3 = conn.Database().prepareStatement(sql3,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            PreparedStatement stmt4 = conn.Database().prepareStatement(sql4,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            PreparedStatement stmt5 = conn.Database().prepareStatement(sql5,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            PreparedStatement stmt6 = conn.Database().prepareStatement(sql6,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            PreparedStatement stmt7 = conn.Database().prepareStatement(sql7,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            stmt3.setString(1,code);
            stmt4.setString(1,code);
            stmt5.setString(1,code);


            rs3 = stmt3.executeQuery();

            while (rs3.next()){
                String course = rs3.getString("course_code");
                if (course.equals(code)){
                    y++;
                }
            }

            if ( y == 0) {
                System.out.println("This course has not been offered yet");
                StudentOptions temp = new StudentOptions();

                temp.optionsStudent();
            }

            rs4 = stmt4.executeQuery();

            if(rs4.next()) {
                id = rs4.getString(1);

            }

            stmt7.setString(1,id);
            rs7 = stmt7.executeQuery();

            while(rs7.next()) {

                String status = rs7.getString("status");
                if(status.equals("1")){
                    statusCount++;
                    break;
                }
            }



            if (statusCount == 0) {
                System.out.println("The associated semester has already ended");
                StudentOptions myVar = new StudentOptions();
                myVar.optionsStudent();
            }

            rs5 = stmt5.executeQuery();
            if(rs5.next()) {
                min_cg = rs5.getString("min_cgpa");
            }

            LoggedinMail var1 = new LoggedinMail();
            String mailID = var1.getEmail();

            stmt6.setString(1,mailID);

            rs6 = stmt6.executeQuery();

            if(rs6.next()) {
                user_cg = rs6.getString(1);
            }

            Double min_gpa = Double.parseDouble(min_cg);
            Double user_gpa = Double.parseDouble(user_cg);

            if ( user_gpa < min_gpa) {
                System.out.println("You are not eligible to register for this course");
                StudentOptions mytemp = new StudentOptions();
                mytemp.optionsStudent();
            }

            else {
                String sql9 = "SELECT student_email,course_code FROM student_courses WHERE student_email = ? AND course_code = ?";
                PreparedStatement stmt9 = conn.Database().prepareStatement(sql9,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

                stmt9.setString(1,mailID);
                stmt9.setString(2,code);

                ResultSet rs9 = stmt9.executeQuery();

                while(rs9.next()) {
                    String stu = rs9.getString("student_email");
                    String tempCode = rs9.getString("course_code");

                    if ( stu.equals(mailID) && tempCode.equals(code)) {
                        System.out.println("You have already registered for this course");
                        StudentOptions temp9 = new StudentOptions();
                        temp9.optionsStudent();
                    }
                }
            }

            String sql8 = "INSERT INTO student_courses( student_email, course_code, semester_id ) VALUES (?,?,?)";

            PreparedStatement stmt8 = conn.Database().prepareStatement(sql8,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            stmt8.setString(1,mailID);
            stmt8.setString(2,code);
            stmt8.setString(3,id);

            stmt8.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("You have successfully registered for the desired course");
        StudentOptions temp6 = new StudentOptions();
        temp6.optionsStudent();
    }

    private void courseDeregister() {

        String courseCode = "",temp="";
        int y = 0;

        System.out.print("Course Code: ");
        courseCode = scan.nextLine();

        String sql1 = "SELECT * FROM student_courses WHERE course_code = ?";
        try{
            PreparedStatement stmt1 = conn.Database().prepareStatement(sql1,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setString(1,courseCode);

            ResultSet rs1 = stmt1.executeQuery();

            while(rs1.next()) {
                temp = rs1.getString("course_code");
                if ( temp.equals(courseCode)) {
                    y++;
                }
            }

            if ( y == 0 ) {
                System.out.println("You are not registered for this course");
                StudentOptions var5 = new StudentOptions();
                var5.optionsStudent();
            }

            String sql2 =  "DELETE FROM student_courses WHERE course_code = ?";
            PreparedStatement stmt2 = conn.Database().prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            stmt2.setString(1,courseCode);

            stmt2.executeUpdate();


        } catch ( Exception e) {
            e.printStackTrace();
        }

        System.out.println("You have been deregistered for the course: " + courseCode );
        StudentOptions temp6 = new StudentOptions();
        temp6.optionsStudent();

    }


}
