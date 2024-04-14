package src.view;

import src.handler.*;

/**
 * View for staffs to select which system setting is to be configured
 * System settings include movie ticket pricings, holidays and movie goer
 * permissions
 * 
 * @author Jonathan Ng
 * @version 1.0
 */
public class StaffConfigSettingsView {
    /**
     * StaffConfigPriceView to be displayed
     */
    private StaffConfigPriceView staffConfigPriceView;

    /**
     * StaffConfigHolidayView to be displayed
     */
    private StaffConfigHolidayView staffConfigHolidayView;

    /**
     * StaffConfigPermissionsView to be displayed
     */
    private StaffConfigPermissionsView staffConfigPermissionsView;

    /**
     * Error message of this view
     */
    private String errorMessage;

    /**
     * Constructor
     */
    public StaffConfigSettingsView() {
        this.errorMessage = "";
    }

    /**
     * Method to print the boiler plate
     */
    public void printMenu() {
        MainView.printBoilerPlate("Configure System Settings");
        MainView.printMenuContent("""

                Select the system settings to be configured

                01. Configure pricings.
                02. Configure holidays.
                03. Configure movie goer permissions
                04. Return.
                """);
    }

    /**
     * Method to call printMenu and queries the cinema staff on which system setting
     * is to be configured
     * Upon successful choice, cinema staff will be redirected to either
     * StaffConfigPriceView to configure price, StaffConfigHolidayView to configure
     * holidays, StaffConfigPermissionsView to configure movie goer permissions
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
                    this.staffConfigPriceView = new StaffConfigPriceView();
                    this.errorMessage = "";
                    this.staffConfigPriceView.appContent();
                    break;
                case 2:
                    this.staffConfigHolidayView = new StaffConfigHolidayView();
                    this.errorMessage = "";
                    this.staffConfigHolidayView.appContent();
                    break;
                case 3:
                    this.staffConfigPermissionsView = new StaffConfigPermissionsView();
                    this.errorMessage = "";
                    this.staffConfigPermissionsView.appContent();
                    break;
                case 4:
                    this.errorMessage = "";
                    return;
                default:
                    this.errorMessage = "Error! Please enter a valid input!";
                    continue;
            }
        } while (true);
    }
}