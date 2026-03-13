// Version 4.1
// Use Case 4: Room Search & Availability Check

import java.util.*;

// Version 4.0
// Abstract Room Domain Model
abstract class Room {

    private String roomType;
    private int beds;
    private double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per Night: ₹" + price);
    }
}

// Concrete Room Classes
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 4000);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 8000);
    }
}


// Centralized Inventory (Read-only access for search)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // example unavailable room
    }

    // Read-only availability check
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}


// Search Service (Read-only logic)
class RoomSearchService {

    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("===== Available Rooms =====");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check (filter unavailable rooms)
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println("----------------------------");
            }
        }
    }
}


// Main Application
public class HotelBookingApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Room domain objects
        List<Room> rooms = new ArrayList<>();

        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Search service
        RoomSearchService searchService = new RoomSearchService();

        // Guest performs search
        searchService.searchAvailableRooms(rooms, inventory);

        System.out.println("Search completed. System state unchanged.");
    }
}