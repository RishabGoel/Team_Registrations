package com.example.rishab.teamregistrations;

/**
 * Created by Rishab on 23-01-2016.
 */

public class MyStringValidator  {       //  Class containing the funciton to validate name and entry number
    public static String validate(String name,String entry_no) {    //  function to validate name and entry number

        String error_name_string = "", error_entry_no_string = "", name_temp = "";

        int length1 = name.length();
        int length2 = entry_no.length();

        for (int i = 0; i < length1; i++) { //  function to search for invalid symbols in the name
            if ((name.charAt(i) <= 'Z' && name.charAt(i) >= 'A') || (name.charAt(i) <= 'z' && name.charAt(i) >= 'a') || (name.charAt(i) == ' ') || name.charAt(i) == '.'||name.charAt(i)=='\'')
                ;
            else
                error_name_string = "Invalid Symbol in the name\n";
        }

        for (int i = 1; i < length1; i++) { //  searching multiple periods in name
            if (name.charAt(i) == '.' && name.charAt(i - 1) == '.')
                error_name_string = "name can't contain multiple consecutive periods(.)";
        }

        try {
            //  searching for period in the beginning of the name
            if (name.charAt(0) == '.')
                error_name_string = "name can't begin with a period(.)";

            int i = 0;
            while (i < length1 && name.charAt(i) == ' ')    //Removing the spaces from the beginning of the name
                i++;


            for (; i < length1; ) {     //Removing multiple spaces in between the name using a temporary variable name_temp
                name_temp += name.charAt(i);
                if (name.charAt(i) == ' ') {
                    while (i < length1 && name.charAt(i) == ' ')
                        i++;
                } else i++;
            }

            name = "";
            name += name_temp;


            if (name_temp == "")    //checking for Empty name string
                error_name_string = "name can't be Empty";


            /* Checking of Entry number is done in subsequent steps */

            if (entry_no.length() != 11) {  //  check for correct length
                error_entry_no_string = "Invalid Entry Number Format";
                return error_name_string + "\n" + error_entry_no_string;
            }

            String entry_no_temp = "";  //  temporary string variable to convert uppercase characters in entry number to lowercase

            for (i = 0; i < 4; i++)
                entry_no_temp += entry_no.charAt(i);

            int temp;   //  temporary int variable to retrive uppercase characters in entry_no and convert them to lowercase ones

            for (i = 4; i < 7; i++) {
                if (entry_no.charAt(i) >= 'A' && entry_no.charAt(i) <= 'Z')
                    temp = (entry_no.charAt(i) - 'A' + 'a');
                else
                    temp = entry_no.charAt(i);
                entry_no_temp += (char) temp;
            }

            for (i = 7; i < 11; i++)
                entry_no_temp += entry_no.charAt(i);

            entry_no = entry_no_temp;

            for (i = 0; i < 4; i++)
                if (entry_no.charAt(i) < '0' || entry_no.charAt(i) > '9')
                    error_entry_no_string = "Invalid Entry Number Format";


            for (i = 4; i < 6; i++)
                if (entry_no.charAt(i) < 'a' || entry_no.charAt(i) > 'z')
                    error_entry_no_string = "Invalid Entry Number Format";


            if ((entry_no.charAt(6) < 'a' || entry_no.charAt(6) > 'z') && (entry_no.charAt(5) < '0' || entry_no.charAt(6) > '9'))
                error_entry_no_string = "Invalid Entry Number Format";

            for (i = 7; i < 11; i++)
                if (entry_no.charAt(i) < '0' || entry_no.charAt(i) > '9')
                    error_entry_no_string = "Invalid Entry Number Format";

            /* checking for validity of entry number */

            if (entry_no.charAt(0) != '2' || entry_no.charAt(1) != '0' || (entry_no.charAt(2) > '1') || (entry_no.charAt(2) == '1' && entry_no.charAt(3) > '5'))
                error_entry_no_string = "Invalid Entry Number";

            if (error_name_string == "") return error_entry_no_string;
            else return error_name_string + "\n" + error_entry_no_string;
        }

        catch (IndexOutOfBoundsException e){
            return "Data not Entered";
        }
    }
}
