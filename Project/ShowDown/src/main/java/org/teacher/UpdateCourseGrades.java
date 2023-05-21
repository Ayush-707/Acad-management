package org.teacher;

import org.misc.Connect;


import javax.sql.rowset.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UpdateCourseGrades {

    Scanner scan = new Scanner(System.in);
    Connect conn = new Connect();
    LoggedinMail teach = new LoggedinMail();
    String teacherMail = teach.getEmail();

    String filepath = "E:\\BTECH 3RD YEAR\\2nd Sem\\Software Engineering\\Mini Project\\Project\\ShowDown\\src\\main\\resources\\grades.csv";


    public ResultSet checkStudentCourses() {   //Checks if students are enrolled in course or not

        System.out.print("Enter Course Code: ");
        String courseCode = scan.nextLine();
        String sql = "SELECT * FROM `student_courses` WHERE `course_code` = ?";

        try {
            PreparedStatement stmt = conn.Database().prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1,courseCode);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next()) {
                System.out.println("No student has been enrolled in course: " + courseCode);
                checkStudentCourses();
            }

            else {
                return rs;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }

    public void insertGrades() {

        ResultSet rs = null;
        try {
            rs = checkStudentCourses();

            rs.beforeFirst();

            while(rs.next()) {
                String semId = rs.getString("semester_id");
                String sql = "SELECT status FROM semester WHERE id = ?";
                PreparedStatement stmt = conn.Database().prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                stmt.setString(1,semId);
                ResultSet rs1 = stmt.executeQuery();

                while (rs1.next()) {
                    String status = rs1.getString("status");
                    if (status.equals("0")) {
                        BufferedReader lineReader = new BufferedReader(new FileReader(filepath));

                        String lineText = null;
                        int count = 0;

                        lineReader.readLine();

                        while ((lineText = lineReader.readLine()) != null) {
                            String[] data = lineText.split(",");
                            String email = data[0];
                            String grade = data[1];

                            String reqEmail = rs.getString("student_email");

                            if (reqEmail.equals(email)) {
                                String sql5 = "UPDATE `student_courses` SET `grade` = ? WHERE `student_email` = ? AND `course_code` = ? ";
                                String coder = rs.getString("course_code");
                                stmt = conn.Database().prepareStatement(sql5);
                                stmt.setString(1,grade);
                                stmt.setString(2,reqEmail);
                                stmt.setString(3,coder);

                                stmt.addBatch();
                                assert stmt != null;
                                stmt.executeBatch();

//                        if(count%batchSize == 0){
//                            stmt.executeBatch();
//                        }

                            }

                        }
                        lineReader.close();

                    }
                }
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }


    }

//    public void insertGrades () {
//        ResultSet rs = null;
//
//        int batchSize = 20;
//        PreparedStatement stmt = null;
//        try {
////            rs1 = checkSemesterStatus();
////            RowSetFactory factory = RowSetProvider.newFactory();
////            CachedRowSet rs = factory.createCachedRowSet();
//
//
//            rs.beforeFirst();
//
//            while (rs.next()) {
//
//                BufferedReader lineReader = new BufferedReader(new FileReader(filepath));
//
//                String lineText = null;
//                int count = 0;
//
//                lineReader.readLine();
//
//                while ((lineText = lineReader.readLine()) != null) {
//                    String[] data = lineText.split(",");
//                    String email = data[0];
//                    String grade = data[1];
//
//                    String reqEmail = rs.getString("student_email");
//
//                    if (reqEmail.equals(email)) {
//                        String sql = "UPDATE `student_courses` SET `grade` = ? WHERE `student_email` = ? AND `course_code` = ? ";
//                        String coder = rs.getString("course_code");
//                        stmt = conn.Database().prepareStatement(sql);
//                        stmt.setString(1,grade);
//                        stmt.setString(2,reqEmail);
//                        stmt.setString(3,coder);
//
//                        stmt.addBatch();
//
////                        if(count%batchSize == 0){
////                            stmt.executeBatch();
////                        }
//
//                    }
//
//                }
//                lineReader.close();
//                assert stmt != null;
//                stmt.executeBatch();
//            }
//
//
//        } catch ( Exception e) {
//            e.printStackTrace();
//        }
//    }


    public void gradeUpdate () {

        insertGrades();
        System.out.println("Grades have been inserted");
        FacultyOptions var4 = new FacultyOptions();
        var4.optionsFaculty();

    }
}
