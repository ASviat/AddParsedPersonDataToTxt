import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * ProgramModel
 */
public class ProgramModel {

    public String[] splitedInput;
    public String myName;
    public String mySurname;
    public int myNumber;
    public Date myBirth;
    public String myGender;

    public ProgramModel() {
    }

    /**
     * Split and control the quantity 5 = (Name, Surname, Tel, DateOfBirth, Gender)
     */
    public void splitInput(Scanner myInput) {
        try {

            String[] enteredInput = myInput.nextLine().split(" ");

            System.out.println();
            if (enteredInput.length == 5) {

                this.splitedInput = enteredInput;
            }
            if (enteredInput.length < 5) {
                System.err.println("You entered less data than necessary " + enteredInput.length + " < 5");
                System.out.println("Enter data again: ");
                throw new ExceptionEnteredQuantity();
            }
            if (enteredInput.length > 5) {
                System.err.println("You entered more data than necessary " + enteredInput.length + " > 5");
                System.out.println("Enter data again: ");
                throw new ExceptionEnteredQuantity();
            }

        } catch (ExceptionEnteredQuantity e) {

            splitInput(myInput);
        }
    }

    /**
     * Finds Gender by formats "M, F, MALE, FEMALE".
     * "Snowflake" if not found any gender of above.
     */
    public void findGender() {
        for (int i = 0; i < splitedInput.length; i++) {
            if (splitedInput[i].toUpperCase().equals("M") || splitedInput[i].toUpperCase().equals("F")
                    || splitedInput[i].toUpperCase().equals("FEMALE") || splitedInput[i].toUpperCase().equals("MALE")) {
                this.myGender = splitedInput[i];
                splitedInput[i] = "-";
                break;
            }
        }
        if (this.myGender == null) {
            System.err.println("Error: No gender entered. You will be written to database as a Unique Snowflake");
            this.myGender = "Unique Snowflake".toUpperCase();
        }
    }

    /**
     * Finds birthday, splitted by "."
     */
    public void findBDay() {
        boolean found = false;
        String mBirth = "";
        for (int i = 0; i < splitedInput.length; i++) {

            char[] x = splitedInput[i].toCharArray();
            for (int j = 0; j < x.length; j++) {
                if (x[j] == '.') {
                    found = true;
                    mBirth = splitedInput[i];
                    splitedInput[i] = "-";
                    break;
                }
            }
        }
        if (found == false) {
            throw new ExceptionNoDateEntered();
        }
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        try {
            this.myBirth = format.parse(mBirth);
        } catch (ParseException e) {
            System.err.println("Error: Wrong date format or null\nUse dd.mm.yyyy");
        }
    }

    /**
     * Find telephone number in splitted input.
     */
    public void findNumber(Scanner myInput) {
        int counter = 0;
        for (int j = 0; j < splitedInput.length; j++) {
            try {

                if (!splitedInput[j].equals("-")) {
                    this.myNumber = Integer.parseInt(splitedInput[j]);
                    splitedInput[j] = "-";
                    break;
                }
            } catch (NumberFormatException e) {
                counter++;
                if (counter == 3) {
                    System.err.println("No phone entered!");
                    System.out.println("Please, add your number:\n");
                    this.myNumber = myInput.nextInt();
                } else {
                    continue;
                }
            }
        }
    }

    public void findNameAndSurname(Scanner myInput) {
        try {
            String[] splitedInputN = new String[splitedInput.length];
            splitedInputN = splitedInput;

            for (int i = 0; i < splitedInputN.length; i++) {
                if (!splitedInputN[i].equals("-")) {
                    System.out.printf("Is that you surname: %s ?\n(Y/N)\n", splitedInputN[i]);
                    String myAnswer = myInput.next();

                    if (myAnswer.toUpperCase().equals("Y")) {
                        this.mySurname = splitedInputN[i];
                        splitedInputN[i] = "-";

                        for (int j = 0; j < splitedInputN.length; j++) {
                            if (!splitedInputN[j].equals("-")) {
                                System.out.printf("Is that you name: %s ?\n(Y/N)\n", splitedInputN[j]);
                                myAnswer = myInput.next();
                                if (myAnswer.toUpperCase().equals("Y")) {
                                    this.myName = splitedInputN[j];
                                    splitedInputN[j] = "-";
                                }
                                if (myAnswer.toUpperCase().equals("N")) {
                                    continue;
                                }
                            }
                        }
                    }
                    if (!myAnswer.toUpperCase().equals("N") &
                            !myAnswer.toUpperCase().equals("Y")) {
                        throw new RuntimeException();
                    }
                    if (myAnswer.toUpperCase().equals("N")) {
                        continue;
                    }
                    if (myName != null & mySurname != null) {
                        break;
                    }
                }
            }
            if (this.myName == null || this.mySurname == null) {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            System.err.println("Error: Enter only Y for yes, N for no.");
            findNameAndSurname(myInput);
        }
    }

    public void writeToTxt() {
        try {
            FileWriter fw = new FileWriter(mySurname + ".txt", true);
            fw.append(
                    "<" + mySurname + "><" + myName + "><" + myBirth + "><" + myNumber + "><" + myGender + ">" + '\n');
            fw.close();
        } catch (IOException e) {
            System.err.println("Error in writing file");
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            for (StackTraceElement element : stackTraceElements) {
                System.out.println(element.getMethodName());
            }
        }
    }

    
}