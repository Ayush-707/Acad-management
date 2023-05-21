package org.student;

import org.misc.Connect;
import org.misc.Login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CurrentCGPA {

    Connect conn = new Connect();
    String credits = "";

    public double viewCGPA(String e_mail) {

        String sql1 = "SELECT id FROM semester WHERE status = '0' ";
        ResultSet rs1 = null;
        String[] id = new String[500];
        int i = -1;
        int p = -1;
        int earnedCredits = 0,earnedPoints = 0;

        try{
            Statement stmt1 = conn.Database().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs1 = stmt1.executeQuery(sql1);

            if(!rs1.next()) {
                System.out.println("No semester has been completed. Cannot compute CGPA");
                Login temp1 = new Login();
                temp1.doLogin();
            }
            rs1.beforeFirst();

            while(rs1.next()) {

                i++;
                id[i] =  rs1.getString(1);

            }

            for (int j = 0; j <= i; j++) {

                String semesterID = id[j];
                String[] courseCode = new String[100];
                String[] obtainedGrade = new String[100];
                var obtainedPoints = new int[100];

                String sql2 = "SELECT course_code, grade FROM student_courses WHERE student_email = ? AND semester_id = ?";

                    PreparedStatement stmt2 = conn.Database().prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

                    stmt2.setString(1,e_mail);
                    stmt2.setString(2,semesterID);


                    ResultSet rs2 = stmt2.executeQuery();

                    if (!rs2.next()) {

                        continue;
                    }

                    rs2.previous();
                    ;

                    while( rs2.next()) {
                        p++;
                        courseCode[p] = rs2.getString("course_code");
                        obtainedGrade[p] = rs2.getString("grade");

                        String sql3 = "SELECT credits FROM course_catalog WHERE course_code = ?";

                        PreparedStatement stmt3 = conn.Database().prepareStatement(sql3,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        stmt3.setString(1,courseCode[p]);

                        ResultSet rs3 = stmt3.executeQuery() ;

                        if (rs3.next()) {
                            credits = rs3.getString("credits");
                        }

                        switch (obtainedGrade[p]) {
                            case "A" -> obtainedPoints[p] = 10;
                            case "A-" -> obtainedPoints[p] = 9;
                            case "B" -> obtainedPoints[p] = 8;
                            case "B-" -> obtainedPoints[p] = 7;
                            case "C" -> obtainedPoints[p] = 6;
                            case "C-" -> obtainedPoints[p] = 5;
                            case "D" -> obtainedPoints[p] = 4;
                            case "E" -> obtainedPoints[p] = 2;
                            case "F" -> obtainedPoints[p] = 0;
                        }

                        if (!obtainedGrade[p].equals("E") && !obtainedGrade[p].equals("F")){
                            earnedCredits += Integer.parseInt(credits);
                            earnedPoints += obtainedPoints[p];
                        }

                    }

            }
        } catch(Exception e){
            e.printStackTrace();
        }

        double CGPA = ((double)earnedPoints)/earnedCredits;
        String currCG = Double.toString(CGPA);

        String sql5 = "UPDATE students SET current_cgpa = ? WHERE email = ?";
        LoggedinMail temp5 = new LoggedinMail();
        String stMail = temp5.getEmail();

        try {

            PreparedStatement stmt5 = conn.Database().prepareStatement(sql5);
            stmt5.setString(1,currCG);
            stmt5.setString(2,stMail);
            stmt5.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(CGPA);
        return CGPA;

    }
}
