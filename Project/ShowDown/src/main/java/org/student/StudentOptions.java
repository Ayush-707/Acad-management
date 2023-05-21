package org.student;

import org.misc.Login;
import org.misc.Validate;



import java.util.Scanner;

public class StudentOptions {


    static Scanner scan = new Scanner(System.in);




    public void optionsStudent () {

        String[] allOptions = new String[5];


        allOptions[0] = "1. Update Password";
        allOptions[1] = "2. Register/Deregister for a course";
        allOptions[2] = "3. View Grades";
        allOptions[3] = "4. View current CGPA";
        allOptions[4] = "5. Return to Main Menu";

        System.out.println("Student Options:-");
        for (int i = 0; i < allOptions.length ; i++){
            System.out.println(allOptions[i]);
        }

        Validate myvar = new Validate();
        String in = myvar.validInput(allOptions.length);

        LoggedinMail temp8 = new LoggedinMail();
        String e_mail = temp8.getEmail();

        switch(in) {

            case "1" :

                LoggedinMail temp1 = new LoggedinMail();
                String temp2 = temp1.getEmail();
                UpdatePass myVar = new UpdatePass();
                myVar.passUpdate(temp2);

                break;

            case"2":

                CourseEnrollment temp3 = new CourseEnrollment();
                temp3.enrollCourse();


                break;
            case "3":

                ViewGrades temp4 = new ViewGrades();
                temp4.gradeView(e_mail);

            case "4":


                CurrentCGPA temp5 = new CurrentCGPA();
                double CGPA = temp5.viewCGPA(e_mail);
                System.out.println("Your current CGPA is: " + CGPA);

            case "5":

                Login temp6 = new Login();
                temp6.doLogin();
                break;

        }









    }

}
