
/**
 * ProgramView
 */
public class ProgramView {

    public void printArray(String[] array) {
        for (String string : array) {
            System.out.println(string);
        }
    }

    public void askToAddData() {

        System.out.println(
                "Enter your:\n*First and last name\n*Telephone number\n*Date of birth(dd.mm.yyyy)\n*Gender(M/F)\n__________________________________\nDo everything in one request below:\n");
    }
}