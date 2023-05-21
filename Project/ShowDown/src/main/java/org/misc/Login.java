package org.misc;

import org.office.OfficeAuth;
import org.student.StudentAuth;
import org.teacher.FacultyAuth;




public class Login {
    
    
    public void doLogin () {



        String[] allOptions = new String[3];

        allOptions[0] = "1. Academic Office";
        allOptions[1] = "2. Student";
        allOptions[2] = "3. Faculty";


        System.out.println("User Type?");

        for (String allOption : allOptions) {
            System.out.println(allOption);
        }

        Validate myVar = new Validate();
        String in = myVar.validInput(allOptions.length);

        switch (in) {
            case "1" -> {
                OfficeAuth var2 = new OfficeAuth();
                var2.passwordAuth();
            }
            case "2" -> {
                StudentAuth var3 = new StudentAuth();
                var3.passAuth();
            }
            case "3" -> {
                FacultyAuth var4 = new FacultyAuth();
                var4.passAuth();
            }
            default -> {

            }
        }


    }    
}
