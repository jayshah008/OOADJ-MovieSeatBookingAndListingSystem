package src.database;

import java.io.*;
import java.util.*;
import src.controller.*;
import src.model.*;

public class Database {
    
    public static HashMap<String, Cineplex> CINEPLEX = new HashMap<String, Cineplex>();

    
    public static HashMap<String, Cinema> CINEMA = new HashMap<String, Cinema>();

   
    public static HashMap<String, Seat> SEAT = new HashMap<String, Seat>();

    
    public static HashMap<String, MovieSchedule> MOVIE_SCHEDULE = new HashMap<String, MovieSchedule>();

    
    public static HashMap<String, BookingHistory> BOOKING_HISTORY = new HashMap<String, BookingHistory>();

   
    public static HashMap<String, Payment> PAYMENT = new HashMap<String, Payment>();

   
    public static HashMap<String, Movie> MOVIE = new HashMap<String, Movie>();

    
    public static HashMap<String, MovieGoer> MOVIE_GOER = new HashMap<String, MovieGoer>();

    
    public static HashMap<String, MovieReview> MOVIE_REVIEW = new HashMap<String, MovieReview>();

    public static HashMap<String, CinemaStaff> CINEMA_STAFF = new HashMap<String, CinemaStaff>();

    
    public static HashMap<String, MovieTicket> MOVIE_TICKET = new HashMap<String, MovieTicket>();

    
    public static int totalNumOfSeats = 144;

    
    public static int numOfRows = 12;

    
    public static int numOfCoupleRows = 4;

    public static ArrayList<DateTime> holidays = new ArrayList<>();

    public static Prices PRICES = new Prices();

   
    public static Permission PERMISSION = new Permission();

    public static int platinumNumOfRow = 4;

    
    public static int platinumNumOfSeatsPerRow = 6;

   
    private static String path = "../src/database/data";

    
    private static String extension = ".dat";

   
    public Database() {
        if (!readData(ModelType.CINEPLEX)) {
            System.out.println("Error! Reading of data " + ModelType.CINEPLEX + " failed!");
        }
        if (!readData(ModelType.CINEMA)) {
            System.out.println("Error! Reading of data " + ModelType.CINEMA + " failed!");
        }
        if (!readData(ModelType.SEAT)) {
            System.out.println("Error! Reading of data " + ModelType.SEAT + " failed!");
        }
        if (!readData(ModelType.MOVIE_SCHEDULE)) {
            System.out.println("Error! Reading of data " + ModelType.MOVIE_SCHEDULE + " failed!");
        }
        if (!readData(ModelType.MOVIE)) {
            System.out.println("Error! Reading of data " + ModelType.MOVIE + " failed!");
        }
        if (!readData(ModelType.MOVIE_GOER)) {
            System.out.println("Error! Reading of data " + ModelType.MOVIE_GOER + " failed!");
        }
        if (!readData(ModelType.MOVIE_REVIEW)) {
            System.out.println("Error! Reading of data " + ModelType.MOVIE_REVIEW + " failed!");
        }
        if (!readData(ModelType.BOOKING_HISTORY)) {
            System.out.println("Error! Reading of data " + ModelType.MOVIE_SCHEDULE + " failed!");
        }
        if (!readData(ModelType.PAYMENT)) {
            System.out.println("Error! Reading of data " + ModelType.PAYMENT + " failed!");
        }
        if (!readData(ModelType.CINEMA_STAFF)) {
            System.out.println("Error! Reading of data " + ModelType.CINEMA_STAFF + " failed!");
        }
        if (!readData(ModelType.MOVIE_TICKET)) {
            System.out.println("Error! Reading of data " + ModelType.MOVIE_TICKET + " failed!");
        }
        if (!readData(ModelType.PRICES)) {
            System.out.println("Error! Reading of data " + ModelType.PRICES + " failed");
        }
        if (!readData(ModelType.PERMISSION)) {
            System.out.println("Error! Reading of data " + ModelType.PERMISSION + " failed");
        }
        if (!readData(ModelType.HOLIDAY)) {
            System.out.println("Error! Reading of data " + ModelType.HOLIDAY + " failed");
        }

        ArrayList<CinemaStaff> currentStaff = Database.getValueList(Database.CINEMA_STAFF.values());
        if (currentStaff.size() == 0) {
            DatabaseManager.initalizeCinemaStaff();
        }
    }

    
    public static boolean readData(ModelType modelType) {
        String filePath = Database.path + "/" + modelType.getFileName() + extension;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object object = objectInputStream.readObject();

            if (modelType == ModelType.CINEPLEX) {
                Database.CINEPLEX = (HashMap<String, Cineplex>) object;
            } else if (modelType == ModelType.CINEMA) {
                Database.CINEMA = (HashMap<String, Cinema>) object;
            } else if (modelType == ModelType.SEAT) {
                Database.SEAT = (HashMap<String, Seat>) object;
            } else if (modelType == ModelType.MOVIE_SCHEDULE) {
                Database.MOVIE_SCHEDULE = (HashMap<String, MovieSchedule>) object;
            } else if (modelType == ModelType.BOOKING_HISTORY) {
                Database.BOOKING_HISTORY = (HashMap<String, BookingHistory>) object;
            } else if (modelType == ModelType.PAYMENT) {
                Database.PAYMENT = (HashMap<String, Payment>) object;
            } else if (modelType == ModelType.MOVIE) {
                Database.MOVIE = (HashMap<String, Movie>) object;
            } else if (modelType == ModelType.MOVIE_GOER) {
                Database.MOVIE_GOER = (HashMap<String, MovieGoer>) object;
            } else if (modelType == ModelType.MOVIE_REVIEW) {
                Database.MOVIE_REVIEW = (HashMap<String, MovieReview>) object;
            } else if (modelType == ModelType.CINEMA_STAFF) {
                Database.CINEMA_STAFF = (HashMap<String, CinemaStaff>) object;
            } else if (modelType == ModelType.PRICES) {
                Database.PRICES = (Prices) object;
            } else if (modelType == ModelType.MOVIE_TICKET) {
                Database.MOVIE_TICKET = (HashMap<String, MovieTicket>) object;
            } else if (modelType == ModelType.HOLIDAY) {
                Database.holidays = (ArrayList<DateTime>) object;
            } else if (modelType == ModelType.PERMISSION) {
                Database.PERMISSION = (Permission) object;
            }

            objectInputStream.close();
            fileInputStream.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error! Reading of data " + modelType.getFileName() + " failed!");
            return false;
        }
    }

   
    public static boolean writeData(ModelType modelType) {
        String filePath = Database.path + "/" + modelType.getFileName() + extension;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            if (modelType == ModelType.CINEPLEX) {
                objectOutputStream.writeObject(Database.CINEPLEX);
            } else if (modelType == ModelType.CINEMA) {
                objectOutputStream.writeObject(Database.CINEMA);
            } else if (modelType == ModelType.SEAT) {
                objectOutputStream.writeObject(Database.SEAT);
            } else if (modelType == ModelType.MOVIE_SCHEDULE) {
                objectOutputStream.writeObject(Database.MOVIE_SCHEDULE);
            } else if (modelType == ModelType.BOOKING_HISTORY) {
                objectOutputStream.writeObject(Database.BOOKING_HISTORY);
            } else if (modelType == ModelType.PAYMENT) {
                objectOutputStream.writeObject(Database.PAYMENT);
            } else if (modelType == ModelType.MOVIE) {
                objectOutputStream.writeObject(Database.MOVIE);
            } else if (modelType == ModelType.MOVIE_GOER) {
                objectOutputStream.writeObject(Database.MOVIE_GOER);
            } else if (modelType == ModelType.MOVIE_REVIEW) {
                objectOutputStream.writeObject(Database.MOVIE_REVIEW);
            } else if (modelType == ModelType.CINEMA_STAFF) {
                objectOutputStream.writeObject(Database.CINEMA_STAFF);
            } else if (modelType == ModelType.PRICES) {
                objectOutputStream.writeObject(Database.PRICES);
            } else if (modelType == ModelType.MOVIE_TICKET) {
                objectOutputStream.writeObject(Database.MOVIE_TICKET);
            } else if (modelType == ModelType.HOLIDAY) {
                objectOutputStream.writeObject(Database.holidays);
            } else if (modelType == ModelType.PERMISSION) {
                objectOutputStream.writeObject(Database.PERMISSION);
            }

            fileOutputStream.close();
            objectOutputStream.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error! Failed to write to file " + modelType + "!");
            return false;
        }
    }

    
    public static boolean remountDatabase() {
        try {
            Database.readData(ModelType.CINEPLEX);
            Database.readData(ModelType.CINEMA);
            Database.readData(ModelType.SEAT);
            Database.readData(ModelType.MOVIE_SCHEDULE);
            Database.readData(ModelType.BOOKING_HISTORY);
            Database.readData(ModelType.PAYMENT);
            Database.readData(ModelType.MOVIE);
            Database.readData(ModelType.MOVIE_GOER);
            Database.readData(ModelType.MOVIE_REVIEW);
            Database.readData(ModelType.CINEMA_STAFF);
            Database.readData(ModelType.PRICES);
            Database.readData(ModelType.MOVIE_TICKET);
            Database.readData(ModelType.HOLIDAY);
            Database.readData(ModelType.PERMISSION);

            return true;
        } catch (Exception e) {
            System.out.println("Error! " + e.getMessage());
            return false;
        }
    }

    
    public static boolean writeToDatabase() {
        try {
            Database.writeData(ModelType.CINEPLEX);
            Database.writeData(ModelType.CINEMA);
            Database.writeData(ModelType.SEAT);
            Database.writeData(ModelType.MOVIE_SCHEDULE);
            Database.writeData(ModelType.BOOKING_HISTORY);
            Database.writeData(ModelType.PAYMENT);
            Database.writeData(ModelType.MOVIE);
            Database.writeData(ModelType.MOVIE_GOER);
            Database.writeData(ModelType.MOVIE_REVIEW);
            Database.writeData(ModelType.CINEMA_STAFF);
            Database.writeData(ModelType.PRICES);
            Database.writeData(ModelType.MOVIE_TICKET);
            Database.writeData(ModelType.HOLIDAY);
            Database.writeData(ModelType.PERMISSION);
            return true;
        } catch (Exception e) {
            System.out.println("Error! " + e.getMessage());
            return false;
        }
    }

    
    public static void resetDatabase() {
        Database.CINEPLEX = new HashMap<String, Cineplex>();
        Database.CINEMA = new HashMap<String, Cinema>();
        Database.SEAT = new HashMap<String, Seat>();
        Database.MOVIE_SCHEDULE = new HashMap<String, MovieSchedule>();
        Database.BOOKING_HISTORY = new HashMap<String, BookingHistory>();
        Database.PAYMENT = new HashMap<String, Payment>();
        Database.MOVIE = new HashMap<String, Movie>();
        Database.MOVIE_GOER = new HashMap<String, MovieGoer>();
        Database.MOVIE_REVIEW = new HashMap<String, MovieReview>();
        Database.CINEMA_STAFF = new HashMap<String, CinemaStaff>();
        Database.PRICES = new Prices();
        Database.MOVIE_TICKET = new HashMap<String, MovieTicket>();
        Database.holidays = new ArrayList<DateTime>();
        Database.PERMISSION = new Permission();

        Database.writeData(ModelType.CINEPLEX);
        Database.writeData(ModelType.CINEMA);
        Database.writeData(ModelType.SEAT);
        Database.writeData(ModelType.MOVIE_SCHEDULE);
        Database.writeData(ModelType.BOOKING_HISTORY);
        Database.writeData(ModelType.PAYMENT);
        Database.writeData(ModelType.MOVIE);
        Database.writeData(ModelType.MOVIE_GOER);
        Database.writeData(ModelType.MOVIE_REVIEW);
        Database.writeData(ModelType.CINEMA_STAFF);
        Database.writeData(ModelType.PRICES);
        Database.writeData(ModelType.MOVIE_TICKET);
        Database.writeData(ModelType.HOLIDAY);
        Database.writeData(ModelType.PERMISSION);
    }

    
    public static ArrayList<String> getKeyList(Collection keySet) {
        ArrayList<String> keyList = new ArrayList<>(keySet);
        return keyList;
    }

    
    public static <V> ArrayList<V> getValueList(Collection valueSet) {
        ArrayList<V> valueList = new ArrayList<>(valueSet);
        return valueList;
    }

   
    public static <K, V> V getValueByKey(K key, HashMap<K, V> data) {
        ArrayList<String> dataList = Database.getKeyList(data.keySet());
        ArrayList<V> valueList = Database.getValueList(data.values());
        int index = dataList.indexOf(key);
        return valueList.get(index);
    }

   
    public static <K, V> String getKeyByValue(V value, HashMap<K, V> data) {
        ArrayList<String> dataList = Database.getKeyList(data.keySet());
        ArrayList<V> valueList = Database.getValueList(data.values());
        int index = valueList.indexOf(value);
        return dataList.get(index);
    }
}
