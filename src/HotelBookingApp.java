// Version 6.1
// Use Case 6: Reservation Confirmation & Room Allocation

import java.util.*;

// Version 6.0
// Reservation class
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


// Inventory Service
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        int count = inventory.get(roomType);
        inventory.put(roomType, count - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Available: " + entry.getValue());
        }
    }
}


// Booking Service (Allocation Logic)
class BookingService {

    private Queue<Reservation> requestQueue;
    private HashMap<String, Set<String>> allocatedRooms;

    public BookingService() {
        requestQueue = new LinkedList<>();
        allocatedRooms = new HashMap<>();
    }

    // Add booking request
    public void addRequest(Reservation r) {
        requestQueue.add(r);
    }

    // Process requests
    public void processRequests(RoomInventory inventory) {

        System.out.println("Processing Booking Requests...\n");

        while (!requestQueue.isEmpty()) {

            Reservation reservation = requestQueue.poll();
            String roomType = reservation.getRoomType();

            // Check availability
            if (inventory.getAvailability(roomType) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure map entry exists
                allocatedRooms.putIfAbsent(roomType, new HashSet<>());

                // Prevent duplicate room assignment
                if (!allocatedRooms.get(roomType).contains(roomId)) {

                    allocatedRooms.get(roomType).add(roomId);

                    // Update inventory immediately
                    inventory.decrementRoom(roomType);

                    System.out.println("Reservation Confirmed!");
                    System.out.println("Guest: " + reservation.getGuestName());
                    System.out.println("Room Type: " + roomType);
                    System.out.println("Assigned Room ID: " + roomId);
                    System.out.println("-----------------------------");
                }

            } else {
                System.out.println("Reservation Failed for " + reservation.getGuestName()
                        + " - No " + roomType + " available.");
                System.out.println("-----------------------------");
            }
        }
    }

    // Generate simple unique room ID
    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 4);
    }
}


// Main Application
public class HotelBookingApp {

    public static void main(String[] args) {

        // Initialize services
        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService();

        // Booking requests (FIFO)
        bookingService.addRequest(new Reservation("Alice", "Single Room"));
        bookingService.addRequest(new Reservation("Bob", "Double Room"));
        bookingService.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingService.addRequest(new Reservation("David", "Suite Room")); // May fail if full

        // Process bookings
        bookingService.processRequests(inventory);

        // Display remaining inventory
        inventory.displayInventory();

        System.out.println("\nBooking processing completed.");
    }
}