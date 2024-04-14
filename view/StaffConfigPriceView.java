package src.view;

import src.handler.*;
import src.controller.*;

/**
 * View for cinema staffs to configure prices
 * 
 * @author Jonathan Ng
 * @version 1.0
 */
public class StaffConfigPriceView {
    /**
     * Error message of this view
     */
    private String errorMessage;

    /**
     * Constructor
     */
    public StaffConfigPriceView() {
        this.errorMessage = "";
    }

    /**
     * Method to print boiler plate for price configuration menu
     */
    public void printMenu() {
        MainView.printBoilerPlate("Price Configuration");
        MainView.printMenuContent("""

                Select the pricing to be configured

                01. Movie Type
                02. Cinema Class
                03. User Age Group
                04. Seat
                05. Special Dates (Weekends/Holidays)
                06. Quit and return back
                """);
    }

    /**
     * Method to print boiler plate for movie type pricing menu
     */
    public void printMovieTypePrice() {
        MainView.printBoilerPlate("Configure Movie Type Pricing");
        MainView.printMenuContent("""

                How would you like to configure Movie Type pricings?

                01. Configure Standard Movie Price
                02. Configure BlockbusterMoviePrice
                03. Configure 3D Movie Price
                04. Quit and return back
                    """);
    }

    /**
     * Method to print boiler plate for cinema class pricing menu
     */
    public void printCinemaClassPrice() {
        MainView.printBoilerPlate("Configure Cinema Class Pricing");
        MainView.printMenuContent("""
                How would you like to configure Cinema Class pricings?

                01. Configure Standard Cinema Price
                02. Configure IMAX Cinema Price
                03. Configure Platinum Cinema Price
                04. Quit and return back
                    """);
    }

    /**
     * Method to print boiler plate for age group class pricing menu
     */
    public void printAgeGroupPrice() {
        MainView.printBoilerPlate("Configure Age Group Pricing");
        MainView.printMenuContent("""
                How would you like to configure Age Group pricings?

                01. Configure Child Ticket Price
                02. Configure Student Ticket Price
                03. Configure Adult Ticket Price
                04. Configure Senior Citizen Ticket Price
                05. Quit and return back
                    """);
    }

    /**
     * Method to print boiler plate for special dates pricing menu
     */
    public void printSpecialDatePrice() {
        MainView.printBoilerPlate("Configure Special Dates Pricing");
        MainView.printMenuContent("""
                How would you like to configure Special Dates pricings?

                01. Configure Holiday Ticket Price
                02. Configure Weekend Ticket Price
                03. Quit and return back
                    """);
    }

    /**
     * Method that calls printMenu and queries for the price type that is to be
     * configured
     * Upon successful price type selected, it calls the relevant boiler plate and
     * queries for the specific price under the price type to be configured
     * Upon successful choice, the chosen price in the database will be updated
     * using configurePrice method by the CinemaStaffManager
     */
    public void appContent() {
        int choice = -1;

        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            choice = InputHandler.intHandler();
            switch (choice) {
                case 1:
                    do {
                        UIHandler.clearScreen();
                        System.out.println(this.errorMessage);
                        this.printMovieTypePrice();
                        int movieTypeChoice = InputHandler.intHandler();
                        if (movieTypeChoice == 1) {
                            CinemaStaffManager.configurePrice(7);
                            break;
                        } else if (movieTypeChoice == 2) {
                            CinemaStaffManager.configurePrice(5);
                            break;
                        } else if (movieTypeChoice == 3) {
                            CinemaStaffManager.configurePrice(6);
                            break;
                        } else if (movieTypeChoice == 4) {
                            return;
                        } else {
                            this.errorMessage = "Error! Please enter a valid input!";
                            continue;
                        }
                    } while (true);
                    break;

                case 2:
                    do {
                        UIHandler.clearScreen();
                        System.out.println(this.errorMessage);
                        this.printCinemaClassPrice();
                        int cinemaChoice = InputHandler.intHandler();
                        if (cinemaChoice == 1) {
                            CinemaStaffManager.configurePrice(1);
                            break;
                        } else if (cinemaChoice == 2) {
                            CinemaStaffManager.configurePrice(3);
                            break;
                        } else if (cinemaChoice == 3) {
                            CinemaStaffManager.configurePrice(2);
                            break;
                        } else if (cinemaChoice == 4) {
                            return;
                        } else {
                            this.errorMessage = "Error! Please enter a valid input!";
                            continue;
                        }
                    } while (true);
                    break;

                case 3:
                    do {
                        UIHandler.clearScreen();
                        System.out.println(this.errorMessage);
                        this.printAgeGroupPrice();
                        int ageChoice = InputHandler.intHandler();
                        if (ageChoice == 1) {
                            CinemaStaffManager.configurePrice(8);
                            break;
                        } else if (ageChoice == 2) {
                            CinemaStaffManager.configurePrice(9);
                            break;
                        } else if (ageChoice == 3) {
                            CinemaStaffManager.configurePrice(10);
                            break;
                        } else if (ageChoice == 4) {
                            CinemaStaffManager.configurePrice(11);
                            break;
                        } else if (ageChoice == 5) {
                            return;
                        } else {
                            this.errorMessage = "Error! Please enter a valid input!";
                            continue;
                        }
                    } while (true);
                    break;

                case 4:
                    CinemaStaffManager.configurePrice(4);
                    break;

                case 5:
                    do {
                        UIHandler.clearScreen();
                        System.out.println(this.errorMessage);
                        this.printSpecialDatePrice();
                        int specialDatePrice = InputHandler.intHandler();
                        if (specialDatePrice == 1) {
                            CinemaStaffManager.configurePrice(12);
                            break;
                        } else if (specialDatePrice == 2) {
                            CinemaStaffManager.configurePrice(13);
                            break;
                        } else if (specialDatePrice == 3) {
                            return;
                        } else {
                            this.errorMessage = "Error! Please enter a valid input!";
                            continue;
                        }
                    } while (true);
                    break;

                case 6:
                    this.errorMessage = "";
                    return;
                default:
                    this.errorMessage = "Error! Please enter a valid input!";
                    break;
            }
        } while (choice != 6);
    }
}
