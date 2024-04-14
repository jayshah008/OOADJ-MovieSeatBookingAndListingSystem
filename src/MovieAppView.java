package src.view;

import src.database.*;
import src.handler.*;
import src.model.*;
import src.model.enums.*;
import src.controller.*;


public class MovieAppView extends MainView {

    private StaffSystemConfigView staffView;

    private String errorMessage;

    public MovieAppView() {
        this.errorMessage = "";
    }

    public void printMenu() {

        MainView.printBoilerPlate("Welcome to MOBLIMA!");
        MainView.printMenuContent("""

                01. Login.
                02. Register.
                03. Exit the program.
                """);
    }


    public void printAgeGroup() {
        MainView.printBoilerPlate("What is your age group?");
        MainView.printMenuContent("""

                01. Adult.
                02. Child.
                03. Senior Citizen.
                04. Student.
                """);
    }


    public void appContent() {
        int choice = -1;
        Database db = new Database();
        String username;
        String password;

        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            choice = InputHandler.intHandler();

            switch (choice) {
                case 1:
                    UIHandler.clearScreen();
                    System.out.println("Please enter your username: ");
                    username = InputHandler.stringHandler();
                    UIHandler.clearScreen();
                    System.out.println("Please enter your password: ");
                    password = InputHandler.stringHandler();
                    Object user = UserManager.login(username, password);
                    if (user instanceof MovieGoer) {
                        MovieGoer movieGoer = (MovieGoer) user;
                        MovieMenuView menu = new MovieMenuView(movieGoer);
                        this.errorMessage = "";
                        menu.appContent();
                    } else if (user instanceof CinemaStaff) {
                        this.staffView = new StaffSystemConfigView((CinemaStaff) user);
                        this.errorMessage = "";
                        this.staffView.appContent();
                    } else {
                        this.errorMessage = "Error! Invalid username or password! Please try again!";
                    }
                    break;
                case 2:
                    UIHandler.clearScreen();
                    System.out.println("Please enter a unique username");
                    username = InputHandler.stringHandler();
                    UIHandler.clearScreen();
                    System.out.println("Please enter a password");
                    password = InputHandler.stringHandler();
                    if (!UserManager.checkUniqueUser(username)) {
                        this.errorMessage = "Username has been taken!";
                        break;
                    }

                    int choice1 = -1;
                    MovieGoerAge movieGoerAge = MovieGoerAge.Adult;
                    UIHandler.clearScreen();
                    this.printAgeGroup();
                    choice1 = InputHandler.intHandler();
                    switch (choice1) {
                        case 1:
                            movieGoerAge = MovieGoerAge.Adult;
                            break;
                        case 2:
                            movieGoerAge = MovieGoerAge.Child;
                            break;
                        case 3:
                            movieGoerAge = MovieGoerAge.SeniorCitizen;
                            break;
                        case 4:
                            movieGoerAge = MovieGoerAge.Student;
                            break;
                    }

                    UIHandler.clearScreen();
                    System.out.println("Enter your name: ");
                    String name = InputHandler.stringHandler();
                    UIHandler.clearScreen();
                    System.out.println("Enter your email: ");
                    String email = InputHandler.stringHandler();
                    UIHandler.clearScreen();
                    System.out.println("Enter your mobile number: ");
                    String mobileNum = InputHandler.stringHandler();

                    MovieGoer movieGoer = UserManager.register(movieGoerAge, name, username, password, email,
                            mobileNum);
                    MovieMenuView menu = new MovieMenuView(movieGoer);
                    this.errorMessage = "";
                    menu.appContent();
                    break;

                case 3:
                    return;

                default:
                    this.errorMessage = "Error! Please enter a valid input!";
            }
        } while (true);
    }
}
