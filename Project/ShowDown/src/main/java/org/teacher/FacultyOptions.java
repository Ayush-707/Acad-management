package org.teacher;

import org.misc.Login;
import org.misc.Validate;


import java.util.Scanner;

public class FacultyOptions {


    static Scanner scan = new Scanner(System.in);




    public void optionsFaculty () {

        String[] allOptions = new String[5];


        allOptions[0] = "1. Update Password";
        allOptions[1] = "2. Offer/Rollback Courses";
        allOptions[2] = "3. View Student Grade";
        allOptions[3] = "4. Update Course Grades";
        allOptions[4] = "5. Return to Main Menu";

        System.out.println("Faculty Options:-");
        for (int i = 0; i < allOptions.length; i++){
            System.out.println(allOptions[i]);
        }

        Validate myvar = new Validate();
        String in = myvar.validInput(allOptions.length);

        switch(in) {

            case "1" :

                LoggedinMail temp1 = new LoggedinMail();
                String temp2 = temp1.getEmail();
                UpdatePass myVar = new UpdatePass();
                myVar.passUpdate(temp2);

                break;

            case"2":

                CourseOfferDelete temp3 = new CourseOfferDelete();
                temp3.deleteOfferCourse();
               
                break;
            case "3":

                StudentGrades temp9 = new StudentGrades();
                temp9.gradeView();

            case "4":

                UpdateCourseGrades temp5 = new UpdateCourseGrades();
                temp5.gradeUpdate();

            case "5":

                Login temp4 = new Login();
                temp4.doLogin();

        }









    }

}
