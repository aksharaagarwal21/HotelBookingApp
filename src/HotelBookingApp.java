// Version 3.1
// Use Case 3: Centralized Room Inventory Management

import java.util.HashMap;
import java.util.Map;

// Version 3.0
// Class responsible for managing room inventory
class RoomInventory {

    // HashMap to store room type and availability
    private HashMap<String, Integer> inventory;

    // Constructor initializes room availability
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Method to get availability of a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update availability
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found.");
        }
    }

    // Method to display full inventory
    public void displayInventory() {
        System.out.println("===== Current Room Inventory =====");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Available: " + entry.getValue());
        }
    }
}

// Main application class
public class HotelBookingApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Retrieve availability
        System.out.println("\nChecking availability for Double Room:");
        System.out.println("Available: " + inventory.getAvailability("Double Room"));

        // Update availability
        System.out.println("\nUpdating Double Room availability...");
        inventory.updateAvailability("Double Room", 4);

        // Display updated inventory
        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();

        System.out.println("\nApplication Terminated.");
    }
}