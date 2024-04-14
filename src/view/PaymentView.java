package src.view;

import src.model.*;
import src.controller.*;
import src.database.*;
import src.handler.*;


public class PaymentView extends MainView {

    private Payment payment;

    private MovieSchedule movieSchedule;

  
    private String transactionID;


    private double totalMovieTicketPrice;


    private String referenceID;


    private String errorMessage;


    public PaymentView(String cinemaCode, double totalMovieTicketPrice, MovieSchedule movieSchedule) {
        this.totalMovieTicketPrice = totalMovieTicketPrice;
        this.transactionID = PaymentManager.generateTransactionId(cinemaCode);
        this.movieSchedule = movieSchedule;
        this.errorMessage = "";
    }


    public Payment getPayment() {
        return this.payment;
    }

  
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


    public void printPaymentSuccessful() {
        System.out.println("");
        System.out.println("Payment Processing...");
        System.out.println("");
        System.out.println("Congratulations! Payment Successful!");
        System.out.println("Here is your receipt:");
        System.out.println("");
    }

    public void printReceipt(String paymentMethod) {
        // can add more criteria to be printed
        System.out.println("====================================");
        System.out.println("               MOOADJ               ");
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
