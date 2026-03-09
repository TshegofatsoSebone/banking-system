package com.bank.banking_system.utility;

import java.time.LocalDate;
import java.time.Period;

public class IdValidator {

    public static boolean isAdult(String idNumber) {

        String year = idNumber.substring(0,2);
        String month = idNumber.substring(2,4);
        String day = idNumber.substring(4,6);

        int fullYear = Integer.parseInt(year);

        if(fullYear <= 25){
            fullYear = 2000 + fullYear;
        }else{
            fullYear = 1900 + fullYear;
        }

        LocalDate birthDate = LocalDate.of(fullYear,
                Integer.parseInt(month),
                Integer.parseInt(day));

        int age = Period.between(birthDate, LocalDate.now()).getYears();

        return age >= 18;
    }
}