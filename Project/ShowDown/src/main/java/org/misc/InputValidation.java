package org.misc;

import java.util.Scanner;

public class InputValidation {


    public Scanner input = new Scanner(System.in);
    public String validateInput( String userInput, String expectedInput ) {


        do {

            System.out.print("Input: ");
            userInput = input.nextLine();
            if ( !userInput.equals(expectedInput)) {
                System.out.println("Invalid Input! Please try again");
            }

        } while (!userInput.equals(expectedInput));

        return userInput;

    }
}
