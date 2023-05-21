package org.misc;

import java.util.*;

public class Validate {
    public String validInput(int size) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Input: ");
        String in = "";
        in = scan.nextLine();

        List<String> validInputs = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String element = Integer.toString(i+1);
            validInputs.add(i,element);
        }

        while(!validInputs.contains(in)){
            System.out.println("Invalid Input! Please Try again");
            System.out.print("Input: ");
            in = scan.nextLine();
        }
        return in;



    }
}
