import Exceptions.*;
import FileManagerPackage.Storage;
import PrintMessages.UI;
import Tasks.*;
import UserInputs.Parser;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * The Duke class represents the main entry point for the ThawBot application.
 * It initializes the necessary components and starts the program.
 */
public class Duke {
    private UI ui;

    public static void main(String[] args) {
        UI ui = new UI();
        ui.printsGreeting();
        startProgramme(ui);

    }

    /**
     * Starts the ThawBot program by initializing necessary components and handling user input.
     *
     * @param ui The UI object for printing messages to the user.
     */
    private static void startProgramme(UI ui) {
        try {
            Scanner input = new Scanner(System.in);
            File f = Storage.getFile();
            Scanner s = new Scanner(f);
            TaskList taskList = new TaskList(s);

            Parser.startListening(input, taskList.taskList, ui);
        } catch (ThawException e) {
            ui.handleError(e);
        } catch (IOException e) {
            System.out.println("     An error occurred while reading file: " + e.getMessage());
        }
    }
}
