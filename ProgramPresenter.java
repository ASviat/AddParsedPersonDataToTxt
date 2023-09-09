
import java.util.Scanner;

/**
 * ProgramPresenter
 */
public class ProgramPresenter {

    ProgramModel model;
    ProgramView view;

    public ProgramPresenter(ProgramView view, ProgramModel model) {
        this.view = view;
        this.model = model;
    }

    public void performAdding(Scanner myInput) {

        view.askToAddData();
        model.splitInput(myInput);
        model.findGender();
        try {
            model.findBDay();
        } catch (ExceptionNoDateEntered e) {
            System.err.println("Error: Wrong date format.\nTry again:");
            performAdding(myInput);
        }
        model.findNumber(myInput);
        model.findNameAndSurname(myInput);

        model.writeToTxt();
        System.out.println("Person successfully added");
    }


    
}