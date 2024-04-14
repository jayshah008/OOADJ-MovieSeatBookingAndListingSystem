package src.handler;

import java.util.HashMap;

import src.database.Database;


public class DatabaseHandler {

    
    public DatabaseHandler() {
    };

    public static <K, V> int generateUUID(HashMap<K, V> data) {

        if (data.size() == 0) {
            return 1;
        }

        String currentMaxKey = "";

        for (K key : data.keySet()) {
            if (key instanceof String) {
                String currentKey = (String) key;
                currentKey = currentKey.substring(2);
                if (currentKey.compareTo(currentMaxKey) > 0) {
                    currentMaxKey = currentKey;
                }
            }
        }

        String UUID = currentMaxKey;
        return Integer.parseInt(UUID) + 1;
    }
}