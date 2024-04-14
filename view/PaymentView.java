package src.view;

import src.model.*;
import src.controller.*;
import src.database.*;
import src.handler.*;

/**
 * View for movie goer after selecting the seats for purchase
 * 
 * @author Lee Juin, Yeek Sheng, Jerick
 * @version 1.0
 */
public class PaymentView extends MainView {
    /**
     * Payment object {@link Payment}
     */
    private Payment payment;

    /**
     * MovieSchedule object {@link MovieSchedule}
     */
    private MovieSchedule movieSchedule;

    /**
     * Payment transactionID as string
     */
    private String transactionID;

    /**
     * Total movie ticket price for all seats chosen for the movie
     */
    private double totalMovieTicketPrice;

    /**
     * ReferenceID as string
     */
    private String referenceID;

    /**
     * Error message of the view
     */
    private String errorMessage;

    /**
     * Creates a PaymentView with the cinemaCode, movieSchedule and
     * totalMovieTicketPrice
     * Generates a transactionID based on the cinemaCode
     * 
     * @param cinemaCode            string of the cinema chosen
     * @param totalMovieTicketPrice Total movie ticket price for all seats chosen
     *                              for the movie
     * @param movieSchedule         schedule chosen for the movie type
     */
    public PaymentView(String cinemaCode, double totalMovieTicketPrice, MovieSchedule movieSchedule) {
        this.totalMovieTicketPrice = totalMovieTicketPrice;
        this.transactionID = PaymentManager.generateTransactionId(cinemaCode);
        this.movieSchedule = movieSchedule;
        this.errorMessage = "";
    }

    /**
     * Gets the payment object
     * 
     * @return {@link Payment} object
     */
    public Payment getPayment() {
        return this.payment;
    }

    /**
     * Method that prints boiler plate and displays the choices that the user has to
     * pay
     */
    public void printMenu() {
        MainView.printBoilerPlate("Payment");
        MainView.printMenuContent("""

                How would you like to pay?

                01.  Card Payment
                02.  QRCode
                03.  Bank Transaction
                04.  Quit and return back
                """);
    }

    /**
     * Method that takes in the choice of the payment method, and requires user to
     * key in relevant payment details
     * User will be prompted to re-enter information if int or String keyed in is
     * invalid
     */
    public void appContent() {

        // generate Transaction ID at the moment a new Payment is created.
        // if the entered int or String is not valid, the user is prompted to re-enter
        // again until the system receive a valid value
        int choice = -1;
        UIHandler.clearScreen();
        System.out.println(this.errorMessage);
        this.printMenu();
        choice = InputHandler.intHandler();

        switch (choice) {
            case 1:
                UIHandler.clearScreen();
                System.out.println(this.errorMessage);
                MainView.printBoilerPlate("Card Payment");
                this.payment = PaymentManager.createCardPayment(this.transactionID, this.totalMovieTicketPrice);

                System.out.println("Enter your Card Number:");
                this.referenceID = InputHandler.stringHandler();
                System.out.println("Enter CCV:");
                InputHandler.intHandler();

                printPaymentSuccessful();
                printReceipt("Card Payment");
                DatabaseManager.saveUpdateToDatabase(movieSchedule.getUUID(), movieSchedule, Database.MOVIE_SCHEDULE);
                break;

            case 2:
                UIHandler.clearScreen();
                System.out.println(this.errorMessage);
                MainView.printBoilerPlate("QR Code Payment");
                this.payment = PaymentManager.createQRCodePayment(this.transactionID, this.totalMovieTicketPrice);
                System.out.println("Please Scan the QRCode:");
                System.out.println("Enter OTP Received:");
                this.referenceID = InputHandler.stringHandler();
                printPaymentSuccessful();
                printReceipt("QR Code Payment");
                DatabaseManager.saveUpdateToDatabase(movieSchedule.getUUID(), movieSchedule, Database.MOVIE_SCHEDULE);
                break;

            case 3:
                UIHandler.clearScreen();
                System.out.println(this.errorMessage);
                MainView.printBoilerPlate("Bank Transaction");
                this.payment = PaymentManager.createBankTransactioPayment(this.transactionID,
                        this.totalMovieTicketPrice);

                System.out.println("Enter Bank Account Number:");
                this.referenceID = InputHandler.stringHandler();
                printPaymentSuccessful();
                printReceipt("Bank Transaction");
                DatabaseManager.saveUpdateToDatabase(movieSchedule.getUUID(), movieSchedule, Database.MOVIE_SCHEDULE);
                break;
        }
    }

    /**
     * Method that prints out details of a successful payment
     */
    public void printPaymentSuccessful() {
        System.out.println("");
        System.out.println("Payment Processing...");
        System.out.println("");
        System.out.println("Congratulations! Payment Successful!");
        System.out.println("Here is your receipt:");
        System.out.println("");
    }

    /**
     * Method that prints out receipt containing paymentID, transactionID,
     * paymentMethod, MovieTicketPrice
     * 
     * @param paymentMethod String of Card Payment, QR Code or Bank Transaction
     */
    public void printReceipt(String paymentMethod) {
        // can add more criteria to be printed
        System.out.println("====================================");
        System.out.println("               MOBLIMA              ");
        System.out.println("");
        System.out.println("               RECEIPT              ");
        System.out.println("____________________________________");
        System.out.println("Payment ID      :     " + this.payment.getUUID());
        System.out.println("Transaction ID  :     " + this.payment.getTransactionID());
        System.out.println("Payment Method  :     " + paymentMethod);
        System.out.println("Total Amount ($):     " + this.payment.getMovieTicketPrice());
        System.out.println("____________________________________");
        System.out.println("");
        System.out.println("              THANK YOU!            ");
        System.out.println("            See you again!          ");
        System.out.println("====================================");

        MovieMenuView.exit = true;
        System.out.println("Press any key to print your movie ticket(s)");
        InputHandler.stringHandler();
    }
}
