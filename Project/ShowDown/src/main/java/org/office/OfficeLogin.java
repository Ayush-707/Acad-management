package org.office;

import org.misc.InputValidation;

public class OfficeLogin {

    public static void main(String[] args) {



    //public void loginOffice() {


        String pass = "",correctPass="";
        InputValidation in = new InputValidation();

        OfficePassword password = new OfficePassword();
        correctPass = password.getPass();


        System.out.println("OFFICE LOGIN:");
        System.out.println("Enter Password:-");

        in.validateInput(correctPass,pass);

        OfficeOptions temp = new OfficeOptions();





    }
}
