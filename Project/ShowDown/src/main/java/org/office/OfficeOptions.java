package org.office;


import org.misc.Login;
import org.misc.Validate;

import java.util.*;

public class OfficeOptions {

    static Scanner scan = new Scanner(System.in);




    public void optionsOffice () {

        String[] allOptions = new String[6];


        allOptions[0] = "1. Release Students List";
        allOptions[1] = "2. Release Faculty List";
        allOptions[2] = "3. Edit Course Catalogue";
        allOptions[3] = "4. Release/Terminate Semester";
        allOptions[4] = "5. Generate Student Grade-sheet";
        allOptions[5] = "6. Return to Main Menu";

        System.out.println("Office Options:-");
        for (int i = 0; i < allOptions.length; i++){
            System.out.println(allOptions[i]);
        }

        Validate myvar = new Validate();
        String in = myvar.validInput(allOptions.length);

        switch(in) {

            case "1" :

                UploadStudentList myVar = new UploadStudentList();
                myVar.dataUpload();
                break;

            case"2":

                UploadFacultyList myVar2 = new UploadFacultyList();
                myVar2.listUpload();
                break;

            case "3":

                CourseCatalogue myVar3 = new CourseCatalogue();
                myVar3.courseList();
                break;

            case"4":

                Semester myVar4 = new Semester();
                myVar4.semRelease();
                break;

            case"5":

                GradeSheet myVar5 = new GradeSheet();
                myVar5.generateSheet();


            case "6":

                Login myVar6 = new Login();
                myVar6.doLogin();



        }









    }
}
