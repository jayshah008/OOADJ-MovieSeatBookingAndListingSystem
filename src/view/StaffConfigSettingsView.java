package src.view;

import src.handler.*;


public class StaffConfigSettingsView {

    private StaffConfigPriceView staffConfigPriceView;


    private StaffConfigHolidayView staffConfigHolidayView;

    private StaffConfigPermissionsView staffConfigPermissionsView;

  
    private String errorMessage;

   
    public StaffConfigSettingsView() {
        this.errorMessage = "";
    }


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