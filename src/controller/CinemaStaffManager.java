package src.controller;

import src.model.enums.*;
import src.model.*;
import src.database.*;
import src.handler.*;
import src.view.*;
import java.util.*;

/**
 * Controller class for handling all logic related to {@link CinemaStaff}
 * 
 * @author Jonathan Ng
 * @version 1.0
 */

public class CinemaStaffManager {

    /**
     * Constructor
     */
    public CinemaStaffManager() {
    }

    /**
     * Method to create cinema staff instance and save it to database
     * 
     * @param name     is the name of the cinema staff
     * @param password is the password of the cinema staff's account used during
     *                 login
     * @param username is the username of the cinema staff's account used during
     *                 login
     * @return The created {@link CinemaStaff} object
     */

    public static CinemaStaff createCinemaStaff(String name, String password, String username) {
        String UUID = String.format("SF%04d", DatabaseHandler.generateUUID(Database.CINEMA_STAFF));
        CinemaStaff cinemaStaff = new CinemaStaff(UUID, name, password, username);
        DatabaseManager.saveUpdateToDatabase(UUID, cinemaStaff, Database.CINEMA_STAFF);
        return cinemaStaff;
    }

    /**
     * Method to add a new {@link Movie} into the database and schedule it into the
     * movie scheduler {@link MovieSchedule}
     * 
     * @param title              is the title of the new movie
     * @param movieAgeRating     is the age rating of the new movie
     * @param movieShowingStatus is the new showing status of the new movie
     * @param movieCast          is all the cast members of the new movie
     * @param director           is the name of the director of the new movie
     * @param synopsis           is the synopsis of the new movie
     * @param duration           is the length of the new movie
     * @param movieTypeChoice    is the choice of the new movie type
     * @param showingVenue       is all the showing venues of the new movie
     * @param seatingPlan        is all the seating plan of the cinema corresponding
     *                           to the cinema type
     * @param showingTime        is all the showing times of the movie
     * 
     */
    public static void movieAdder(String title, MovieAgeRating movieAgeRating, MovieShowingStatus movieShowingStatus,
            ArrayList<String> movieCast, String director, String synopsis, double duration, int movieTypeChoice,
            ArrayList<String> showingVenue, ArrayList<ArrayList<Seat>> seatingPlan, ArrayList<DateTime> showingTime) {
        Movie m;
        if (movieTypeChoice == 1) {
            m = MovieManager.createStandardMovie(title, movieAgeRating, movieShowingStatus,
                    movieCast, director, synopsis, duration);
        } else if (movieTypeChoice == 2) {
            m = MovieManager.createBlockbusterMovie(title, movieAgeRating, movieShowingStatus,
                    movieCast, director, synopsis, duration);
        } else if (movieTypeChoice == 3) {
            m = MovieManager.createThreeDMovie(title, movieAgeRating, movieShowingStatus,
                    movieCast, director, synopsis, duration);
        } else {
            System.out.println("here?");
            return;
        }
        MovieScheduleManager.createMovieSchedule(m.getUUID(), showingVenue, seatingPlan, showingTime);
        System.out.println("Movie added into database");
    }

    /**
     * Method to update the details for existing movies in the database
     * 
     * @param movie  is the movie in consideration
     * @param detail is the particular detail that is to be updated. Cinema staffs
     *               are able to configure 1. Movie Title 2. Movie Type 3. Age
     *               Rating 4. Showing Status 5. Name of movie casts 6. Name of
     *               movie director 7. Synopsis 8. Duration of movie 9. Movie
     *               Schedule (Showing venue and Showing time)
     */
    public static void updateExistingMovieDetails(Movie movie, int detail) {
        String movieUUID = movie.getUUID();
        String errorMessage = "";

        int choice = -1;
        switch (detail) {
            case 1:
                UIHandler.clearScreen();
                System.out.println(errorMessage);
                MainView.printBoilerPlate("Configure Movie Title");
                System.out.println("Enter the updated title of the movie: ");
                String newMovieName = InputHandler.stringHandler();
                movie.setMovieTitle(newMovieName);
                DatabaseManager.saveUpdateToDatabase(movieUUID, movie, Database.MOVIE);
                return;

            case 2:
                do {
                    UIHandler.clearScreen();
                    System.out.println(errorMessage);
                    MainView.printBoilerPlate("Configure Movie Type");
                    MainView.printMenuContent("""

                            Select the new movie type:

                            01. Standard Movie
                            02. Blockbuster Movie
                            03. 3D Movie
                            04. Quit and return back
                            """);
                    int newMovieType = InputHandler.intHandler();
                    String oldMovieTitle = movie.getMovieTitle();
                    MovieAgeRating oldMovieAgeRating = movie.getMovieAgeRating();
                    MovieShowingStatus oldShowingStatus = movie.getMovieShowingStatus();
                    ArrayList<String> oldMovieCast = movie.getMovieCast();
                    String oldMovieDirector = movie.getMovieDirector();
                    String oldMovieSynopsis = movie.getMovieSynopsis();
                    double oldMovieDuration = movie.getMovieDuration();

                    if (newMovieType == 1) {
                        Movie newMovie = new StandardMovie(movieUUID, oldMovieTitle, oldMovieAgeRating,
                                oldShowingStatus, oldMovieCast, oldMovieDirector, oldMovieSynopsis, oldMovieDuration);
                        DatabaseManager.saveUpdateToDatabase(movieUUID, newMovie, Database.MOVIE);
                        return;
                    } else if (newMovieType == 2) {
                        Movie newMovie = new BlockbusterMovie(movieUUID, oldMovieTitle, oldMovieAgeRating,
                                oldShowingStatus, oldMovieCast, oldMovieDirector, oldMovieSynopsis, oldMovieDuration);
                        DatabaseManager.saveUpdateToDatabase(movieUUID, newMovie, Database.MOVIE);
                        return;
                    } else if (newMovieType == 3) {
                        Movie newMovie = new ThreeDMovie(movieUUID, oldMovieTitle, oldMovieAgeRating, oldShowingStatus,
                                oldMovieCast, oldMovieDirector, oldMovieSynopsis, oldMovieDuration);
                        DatabaseManager.saveUpdateToDatabase(movieUUID, newMovie, Database.MOVIE);
                        return;
                    } else if (newMovieType == 4) {
                        return;
                    } else {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                } while (true);

            case 3:
                do {
                    UIHandler.clearScreen();
                    System.out.println(errorMessage);
                    MainView.printBoilerPlate("Configure Age Rating");
                    MainView.printMenuContent("""

                            Select the new Age Rating for the movie:

                            01. G (General)
                            02. PG (Parental Guidance)
                            03. PG13 (Parental Guidance for under 13 years old)
                            04. NC16 (No children under 16 years old)
                            05. M18 (Mature content for above 18 years old)
                            06. R21 (Restricted for above 21 years old)
                            07. Quit and return back
                            """);

                    choice = InputHandler.intHandler();
                    if (choice < 1 || choice > 7) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    if (choice == 7) {
                        return;
                    }
                    MovieAgeRating newMovieAgeRating = MovieAgeRating.values()[choice - 1];
                    movie.setMovieAgeRating(newMovieAgeRating);
                    DatabaseManager.saveUpdateToDatabase(movieUUID, movie, Database.MOVIE);
                    return;
                } while (true);

            case 4:
                do {
                    UIHandler.clearScreen();
                    System.out.println(errorMessage);
                    MainView.printBoilerPlate("Configure Showing Status");
                    MainView.printMenuContent("""

                            Select the new Showing Status for the movie:

                            01. Coming Soon
                            02. Preview
                            03. Now Showing
                            04. End of Showing
                            05. Quit and return back
                            """);

                    choice = InputHandler.intHandler();
                    if (choice < 1 || choice > 5) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    if (choice == 5) {
                        return;
                    }

                    MovieShowingStatus newShowingStatus = MovieShowingStatus.values()[choice - 1];
                    movie.setMovieShowingStatus(newShowingStatus);

                    if (newShowingStatus == MovieShowingStatus.END_OF_SHOWING) {
                        MovieSchedule movieSchedule = MovieScheduleManager.getMovieScheduleByMovie(movie);
                        MovieScheduleManager.resetMovieSchedule(movieSchedule);
                    }
                    DatabaseManager.saveUpdateToDatabase(movieUUID, movie, Database.MOVIE);
                    return;
                } while (true);

            case 5:
                do {
                    UIHandler.clearScreen();
                    System.out.println(errorMessage);
                    MainView.printBoilerPlate("Configure Casts");
                    MainView.printMenuContent("""

                            Select one of the options below to update the movie's cast:

                            01. Remove cast
                            02. Add cast
                            03. Quit and return back
                            """);

                    choice = InputHandler.intHandler();
                    if (choice < 1 || choice > 3) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }

                    if (choice == 3) {
                        return;
                    }

                    switch (choice) {
                        case 1:
                            do {
                                UIHandler.clearScreen();
                                System.out.println(errorMessage);
                                MainView.printBoilerPlate("Remove Movie Casts");
                                String content = "\nSelect the cast to be removed. (Enter Cast Number)\n";
                                for (int i = 0; i < movie.getMovieCast().size(); i++) {
                                    String payload = String.format("%02d. ", (i + 1));
                                    payload += "\t" + movie.getMovieCast().get(i) + "\n";
                                    content = content + payload;
                                }
                                String payload = String.format("%02d. ", movie.getMovieCast().size() + 1);
                                payload += "\tQuit and return back";
                                content += payload;
                                MainView.printMenuContent(content);
                                int castNumber = InputHandler.intHandler();
                                if (castNumber < 1 || castNumber > movie.getMovieCast().size() + 1) {
                                    errorMessage = "Error! Please enter a valid input!";
                                    continue;
                                }
                                if (castNumber == movie.getMovieCast().size() + 1) {
                                    return;
                                }
                                movie.getMovieCast().remove(castNumber - 1);
                                DatabaseManager.saveUpdateToDatabase(movieUUID, movie, Database.MOVIE);
                                return;
                            } while (true);

                        case 2:
                            UIHandler.clearScreen();
                            System.out.println(errorMessage);
                            MainView.printBoilerPlate("Add Movie Cast");
                            System.out.println("Enter the name of the cast to be added.");
                            String castName = InputHandler.stringHandler();
                            movie.getMovieCast().add(castName);
                            DatabaseManager.saveUpdateToDatabase(movieUUID, movie, Database.MOVIE);
                            return;
                    }
                } while (true);

            case 6:
                UIHandler.clearScreen();
                System.out.println(errorMessage);
                MainView.printBoilerPlate("Configure Movie Director");
                System.out.println("Enter the name of the new movie director");
                String newDirectorName = InputHandler.stringHandler();
                movie.setMovieDirector(newDirectorName);
                DatabaseManager.saveUpdateToDatabase(movieUUID, movie, Database.MOVIE);
                return;

            case 7:
                UIHandler.clearScreen();
                System.out.println(errorMessage);
                MainView.printBoilerPlate("Configure Movie Synopsis");
                System.out.println("Enter the new synopsis for the movie:");
                String newSynopsis = InputHandler.stringHandler();
                movie.setMovieSynopsis(newSynopsis);
                DatabaseManager.saveUpdateToDatabase(movieUUID, movie, Database.MOVIE);
                return;

            case 8:
                do {
                    UIHandler.clearScreen();
                    System.out.println(errorMessage);
                    MainView.printBoilerPlate("Configure Movie Duration");
                    System.out.println("Enter the new showing duration for the movie: ");
                    double newMovieDuration = InputHandler.doubleHandler();
                    if (newMovieDuration < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    movie.setMovieDuration(newMovieDuration);
                    DatabaseManager.saveUpdateToDatabase(movieUUID, movie, Database.MOVIE);
                    return;
                } while (true);

            case 9:
                do {
                    MovieSchedule movieSchedule = MovieScheduleManager.getMovieScheduleByMovie(movie);
                    UIHandler.clearScreen();
                    System.out.println(errorMessage);
                    MainView.printBoilerPlate("Configure Schedule");
                    MainView.printMenuContent("""

                            Select one of the following options to configure the movie schedule:

                            01. Remove Schedule
                            02. Add Schedule
                            03. Quit and return back
                            """);

                    choice = InputHandler.intHandler();
                    if (choice < 1 || choice > 3) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    errorMessage = "";
                    switch (choice) {
                        case 1:
                            UIHandler.clearScreen();
                            System.out.println(errorMessage);
                            MainView.printBoilerPlate("Remove Schedule");
                            String content = "\nSelect which schedule to be removed. (Enter ID number)\n\n";
                            for (int i = 0; i < movieSchedule.getShowingVenues().size(); i++) {
                                String cinemaUUID = movieSchedule.getShowingVenues().get(i);
                                Cinema cinema = CinemaManager.getCinemaByUUID(cinemaUUID);
                                DateTime showingTime = movieSchedule.getShowingTime().get(i);
                                String payload = String.format("ID: %02d.\n", (i + 1));
                                payload += String.format("Cinema ID: %s", cinemaUUID);
                                payload += String.format("\tClass: %s\n", cinema.getCinemaClass());
                                payload += (showingTime.getTimeNow() + "\n\n");

                                content = content + payload;
                            }
                            content += String.format("%02d. ", movieSchedule.getShowingVenues().size() + 1);
                            content += "Quit and return back";
                            MainView.printMenuContent(content);
                            int venueID = InputHandler.intHandler();
                            if (venueID < 1 || venueID > movieSchedule.getShowingVenues().size() + 1) {
                                errorMessage = "Error! Please enter a valid input!";
                                continue;
                            }
                            if (venueID == movieSchedule.getShowingVenues().size() + 1) {
                                return;
                            }
                            movieSchedule.removeShowingVenue(venueID - 1);
                            movieSchedule.removeShowingTime(venueID - 1);
                            movieSchedule.removeSeatingPlan(venueID - 1);
                            DatabaseManager.saveUpdateToDatabase(movieSchedule.getUUID(), movieSchedule,
                                    Database.MOVIE_SCHEDULE);
                            return;

                        case 2:
                            do {
                                UIHandler.clearScreen();
                                System.out.println(errorMessage);
                                MainView.printBoilerPlate("Add Schedule");
                                ArrayList<Cineplex> cineplexes = Database.getValueList(Database.CINEPLEX.values());
                                content = "\nSelect the cineplex showing this movie: \n\n";

                                for (int i = 0; i < cineplexes.size(); i++) {
                                    String index = String.format("%02d. ", i + 1);
                                    String payload = String.format(index + cineplexes.get(i).getCineplexName() + "\n");
                                    payload += (cineplexes.get(i).getCineplexLocation() + "\n");
                                    content += payload;
                                }
                                content += String.format("%02d. ", cineplexes.size() + 1);
                                content += "Quit and return back";

                                MainView.printMenuContent(content);
                                int cineplexChoice = InputHandler.intHandler();
                                if (cineplexChoice < 0 || cineplexChoice > cineplexes.size() + 1) {
                                    errorMessage = "Error! Please enter a valid input!";
                                    continue;
                                }

                                if (cineplexChoice == cineplexes.size() + 1) {
                                    return;
                                }
                                Cineplex cineplex = cineplexes.get(cineplexChoice - 1);
                                UIHandler.clearScreen();
                                System.out.println(errorMessage);
                                MainView.printBoilerPlate("Configure New Showing Venue");
                                MainView.printMenuContent("""

                                        Select which type of showing venue to be added.

                                        1. Standard Cinema
                                        2. Platinum Cinema
                                        3. IMAX Cinema
                                        4. Quit and return back
                                            """);
                                int newVenueType = InputHandler.intHandler();
                                if (newVenueType < 0 || newVenueType > CinemaClass.values().length) {
                                    errorMessage = "Error! Please enter a valid input!";
                                    continue;
                                }
                                if (newVenueType == CinemaClass.values().length + 1) {
                                    return;
                                }

                                CinemaClass cinemaClass;
                                if (newVenueType == 1) {
                                    cinemaClass = CinemaClass.STANDARD;
                                } else if (newVenueType == 2) {
                                    cinemaClass = CinemaClass.PLATINUM;
                                } else {
                                    cinemaClass = CinemaClass.IMAX;
                                }

                                ArrayList<DateTime> newShowingTimes = new ArrayList<DateTime>();
                                ArrayList<Cinema> cinemas = CinemaManager.filterCinemaByClass(cinemaClass, cineplex);
                                ArrayList<String> cinemasUUID = new ArrayList<>();

                                for (int i = 0; i < cinemas.size(); i++) {
                                    Cinema cinema = cinemas.get(i);
                                    cinemasUUID.add(cinema.getUUID());
                                    UIHandler.clearScreen();
                                    System.out.println(errorMessage);
                                    MainView.printBoilerPlate("Configure New Showing Time");
                                    System.out.println("Enter the showing time for " + cinema.getUUID());
                                    DateTime showingTime = queryDate();
                                    newShowingTimes.add(showingTime);
                                }
                                MovieScheduleManager.updateMovieSchedule(movie.getUUID(), cinemasUUID, newShowingTimes);
                                return;
                            } while (true);

                        default:
                            return;
                    }
                } while (true);
            default:
                return;
        }
    }

    /**
     * Method for cinema staffs to configure the {@link Prices} for various flat
     * prices and rates and updates the database
     * 
     * @param choice is the choice of which price to configure
     */

    public static void configurePrice(int choice) {
        double price;
        String errorMessage = "";

        do {
            UIHandler.clearScreen();
            System.out.println(errorMessage);
            switch (choice) {
                case 1:
                    MainView.printBoilerPlate("Configure Standard Cinema Price");
                    System.out.println("Enter the new price for all Standard Cinemas:");
                    price = InputHandler.doubleHandler();
                    if (price < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    Database.PRICES.setDefaultStandardCinemaPrice(price);
                    DatabaseManager.reloadDatabase();
                    return;
                case 2:
                    MainView.printBoilerPlate("Configure Platinum Cinema Price");
                    System.out.println("Enter the new price for all Platinum Cinemas:");
                    price = InputHandler.doubleHandler();
                    if (price < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    Database.PRICES.setDefaultPlatinumCinemaPrice(price);
                    DatabaseManager.reloadDatabase();
                    return;
                case 3:
                    MainView.printBoilerPlate("Configure IMAX Cinema Price");
                    System.out.println("Enter the new price for all IMAX Cinemas:");
                    price = InputHandler.doubleHandler();
                    if (price < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    Database.PRICES.setDefaultIMaxCinemaPrice(price);
                    DatabaseManager.reloadDatabase();
                    return;
                case 4:
                    MainView.printBoilerPlate("Configure Seat Price");
                    System.out.println("Enter the new price for all cinema seats:");
                    price = InputHandler.doubleHandler();
                    if (price < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    Database.PRICES.setDefaultSeatPrice(price);
                    DatabaseManager.reloadDatabase();
                    return;
                case 5:
                    MainView.printBoilerPlate("Configure Blockbuster Movie Price");
                    System.out.println("Enter the new price for all Blockbuster Movies:");
                    price = InputHandler.doubleHandler();
                    if (price < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    Database.PRICES.setDefaultBlockbusterMoviePrice(price);
                    DatabaseManager.reloadDatabase();
                    return;
                case 6:
                    MainView.printBoilerPlate("Configure 3D Movie Price");
                    System.out.println("Enter the new price for all 3D Movies:");
                    price = InputHandler.doubleHandler();
                    if (price < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    Database.PRICES.setDefault3DMoviePrice(price);
                    DatabaseManager.reloadDatabase();
                    return;
                case 7:
                    MainView.printBoilerPlate("Configure Standard Movie Price");
                    System.out.println("Enter the new price for all Standard Movies:");
                    price = InputHandler.doubleHandler();
                    if (price < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    Database.PRICES.setDefaultStandardMoviePrice(price);
                    DatabaseManager.reloadDatabase();
                    return;
                case 8:
                    MainView.printBoilerPlate("Configure Child Ticket Price");
                    System.out.println("Enter the new price for all Child Ticket:");
                    price = InputHandler.doubleHandler();
                    if (price < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    Database.PRICES.setDefaultChildPrice(price);
                    DatabaseManager.reloadDatabase();
                    return;
                case 9:
                    MainView.printBoilerPlate("Configure Student Ticket Price");
                    System.out.println("Enter the new price for all Student Ticket:");
                    price = InputHandler.doubleHandler();
                    if (price < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    Database.PRICES.setDefaultStudentPrice(price);
                    DatabaseManager.reloadDatabase();
                    return;
                case 10:
                    MainView.printBoilerPlate("Configure Adult Ticket Price");
                    System.out.println("Enter the new price for all Adult Ticket:");
                    price = InputHandler.doubleHandler();
                    if (price < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    Database.PRICES.setDefaultAdultPrice(price);
                    DatabaseManager.reloadDatabase();
                    return;
                case 11:
                    MainView.printBoilerPlate("Configure Senior Citizen Ticket Price");
                    System.out.println("Enter the new price for all Senior Citizen Ticket:");
                    price = InputHandler.doubleHandler();
                    if (price < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    Database.PRICES.setDefaultSeniorCitizenPrice(price);
                    DatabaseManager.reloadDatabase();
                    return;
                case 12:
                    MainView.printBoilerPlate("Configure Holiday Ticket Price");
                    System.out.println("Enter the new price for all Holiday Ticket:");
                    price = InputHandler.doubleHandler();
                    if (price < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    Database.PRICES.setHolidayPrice(price);
                    DatabaseManager.reloadDatabase();
                    return;
                case 13:
                    MainView.printBoilerPlate("Configure Weekend Ticket Price");
                    System.out.println("Enter the new price for all Weekend Ticket:");
                    price = InputHandler.doubleHandler();
                    if (price < 0) {
                        errorMessage = "Error! Please enter a valid input!";
                        continue;
                    }
                    Database.PRICES.setWeekendPrice(price);
                    DatabaseManager.reloadDatabase();
                    return;
                default:
                    return;
            }
        } while (true);
    }

    /**
     * Method for cinema staffs to configure the ArrayList of holidays in the
     * database
     * 
     * @param choice is the choice of whether to add a new holiday or to remove an
     *               existing holiday
     * @return Integer flag whether the configuration is successful or not
     */

    public static int configureHoliday(int choice) {
        DateTime holiday;
        String errorMessage = "";

        do {
            switch (choice) {
                case 1:
                    UIHandler.clearScreen();
                    System.out.println(errorMessage);
                    MainView.printBoilerPlate("Configure Holiday");
                    System.out.println("Enter a holiday date time to be added: ");
                    holiday = queryHoliday();

                    boolean flag = false;
                    for (int i = 0; i < Database.holidays.size(); i++) {
                        if (Database.holidays.get(i).getYear() == holiday.getYear()
                                && Database.holidays.get(i).getMonth() == holiday.getMonth()
                                && Database.holidays.get(i).getDate() == holiday.getDate()
                                && Database.holidays.get(i).getHour() == holiday.getHour()
                                && Database.holidays.get(i).getMinute() == holiday.getMinute()
                                && Database.holidays.get(i).getDay() == holiday.getDay()) {
                            errorMessage = "Holiday already exists!";
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        continue;
                    }
                    Database.holidays.add(holiday);
                    DatabaseManager.reloadDatabase();
                    System.out.println("Holiday added!");
                    System.out.println("Press any key to continue");
                    InputHandler.stringHandler();
                    return 0;

                case 2:
                    do {
                        if (Database.holidays.size() == 0) {
                            UIHandler.clearScreen();
                            MainView.printBoilerPlate("Configure Holiday");
                            MainView.printMenuContent("""
                                    Holiday list is empty!
                                    Press any key to continue
                                        """);

                            InputHandler.stringHandler();
                            return 1;
                        }

                        String content = "\nSelect the holiday to be removed: \n\n";
                        UIHandler.clearScreen();
                        System.out.println(errorMessage);
                        MainView.printBoilerPlate("Configure Holiday");
                        printHolidayList(content);
                        choice = InputHandler.intHandler();
                        if (choice < 1 || choice > Database.holidays.size() + 1) {
                            errorMessage = "Error! Please enter a valid input!";
                            continue;
                        }
                        if (choice == Database.holidays.size() + 1) {
                            return 1;
                        }
                        Database.holidays.remove(choice - 1);
                        DatabaseManager.reloadDatabase();
                        System.out.println("Holiday removed!");
                        System.out.println("Press any key to continue");
                        InputHandler.stringHandler();
                        return 1;
                    } while (true);
                default:
                    return 0;
            }
        } while (true);
    }

    /**
     * Method to print out the holidays on the holidays ArrayList
     * 
     * @param content is the contents to be printed out
     */
    public static void printHolidayList(String content) {
        for (int i = 0; i < Database.holidays.size(); i++) {
            DateTime holiday = Database.holidays.get(i);
            String index = String.format("%02d. ", i + 1);
            String payload = String.format(index + holiday.getHolidayTimeNow() + "\n");
            content += payload;
        }
        String index = String.format("%02d. ", Database.holidays.size() + 1);
        String payload = String.format(index + "Quit and return back\n");
        content += payload;
        MainView.printMenuContent(content);
    }

    /**
     * Method to query and create a new date
     * 
     * @return {@link DateTime} object of the queried date
     */
    public static DateTime queryDate() {
        System.out.print("Year: ");
        int year = InputHandler.intHandler();
        System.out.print("Month: ");
        int month = InputHandler.intHandler();
        System.out.print("Date: ");
        int date = InputHandler.intHandler();
        System.out.print("Day: ");
        int day = InputHandler.intHandler();
        System.out.print("Hour: ");
        int hour = InputHandler.intHandler();
        System.out.print("Minute: ");
        int minute = InputHandler.intHandler();

        DateTime dateTime = new DateTime(minute, hour, day, date, month, year);
        return dateTime;
    }

    /**
     * Method to query and create a new holiday
     * 
     * @return {@link DateTime} object of the queried date
     */
    public static DateTime queryHoliday() {
        System.out.print("Year: ");
        int year = InputHandler.intHandler();
        System.out.print("Month: ");
        int month = InputHandler.intHandler();
        System.out.print("Date: ");
        int date = InputHandler.intHandler();
        DateTime holiday = new DateTime(0, 0, 0, date, month, year);
        return holiday;
    }

    /**
     * Method to opt in/out the permissions of movie goers from viewing the list of
     * top five
     * 
     * @param choice   is the choice whether to opt in/out based on overall ratings
     *                 or based on movie sales
     * @param optInOut is the choice whether to opt in or opt out
     */
    public static void optInOut(int choice, boolean optInOut) {
        if (choice == 1) {
            Database.PERMISSION.setOverallRatingsPermission(optInOut);
        } else {
            Database.PERMISSION.setMovieSalesPermission(optInOut);
        }
        DatabaseManager.reloadDatabase();
    }
}