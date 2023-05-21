package org.office;

import org.misc.InputValidation;

public class OfficeAuth {

    public void passwordAuth() {
        System.out.println("Enter Academic Office Password:-");
        OfficePassword var1 = new OfficePassword();
        InputValidation var2 = new InputValidation();

        String pass = var1.getPass();

        String in = "";
        in = var2.validateInput(in,pass);

        OfficeOptions var3 = new OfficeOptions();
        var3.optionsOffice();
    }
}
