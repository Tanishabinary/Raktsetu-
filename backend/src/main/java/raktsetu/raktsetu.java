 package raktsetu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
@SpringBootApplication
public class raktsetu {

    static ArrayList<Donor> donors = new ArrayList<Donor>();
    static ArrayList<BloodStock> stocks = new ArrayList<BloodStock>();
    static ArrayList<BloodRequest> requests =
            new ArrayList<BloodRequest>();

    static Scanner sc = new Scanner(System.in);
    static String currentRole = "USER";

    static int nextId = 1;
    static int nextRequestId = 1;

    static final String REQUEST_FILE = "blood_requests.txt";
    public static void main(String[] args) {

    DonorFile.loadDonors(donors);
    BloodStockFile.loadStock(stocks);
    loadRequests();
    SpringApplication.run(raktsetu.class, args);

   for (Donor donor : donors) {
    if (donor.getDonorId() >= nextId) {
        nextId = donor.getDonorId() + 1;
    }
}
    // ONLY run the interactive terminal menu if a real console is available (Local PC)
    if (System.console() != null) {
        int choice = 0;

        while (choice != 6) {

            System.out.println("\n===== RAKTSETU =====");
            System.out.println("1. Register Donor");
            System.out.println("2. View Donors");
            System.out.println("3. Search Blood Group");
            System.out.println("4. Blood Stock Management");
            System.out.println("5. Blood Request Management");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                if (choice == 1) {
                    registerDonor();
                } else if (choice == 2) {
                    viewDonors();
                } else if (choice == 3) {
                    searchDonor();
                } else if (choice == 4) {
                    manageStock();
                } else if (choice == 5) {
                    manageRequests();
                } else if (choice == 6) {
                    System.out.println("Thank you for using RaktSetu!");
                } else {
                    System.out.println("Invalid choice!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
            }
        }

        sc.close();
    } else {
        // On Render (Cloud): Keeps the server alive without crashing on missing input
        System.out.println("Running in cloud environment. Web server active.");
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

    



    // REGISTER DONOR
    static void registerDonor() {

        System.out.println("\n--- Donor Registration ---");

        System.out.print("Enter name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter age: ");
        int age;

        try {
            age = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age!");
            return;
        }

        if (name.isEmpty() || age < 18 || age > 65) {
            System.out.println("Invalid details!");
            return;
        }

        System.out.print("Enter blood group: ");
        String bloodGroup = sc.nextLine().trim().toUpperCase();

        if (isValidGroup(bloodGroup) == false) {
            System.out.println("Invalid blood group!");
            return;
        }

        System.out.print("Enter phone: ");
        String phone = sc.nextLine().trim();

        if (phone.matches("[0-9]{10}") == false) {
            System.out.println("Enter a 10-digit phone number!");
            return;
        }

        System.out.print("Enter city: ");
        String city = sc.nextLine().trim();

        if (city.isEmpty()) {
            System.out.println("City cannot be empty!");
            return;
        }

        Donor d = new Donor(
            nextId, name, age, bloodGroup, phone, city
        );

        donors.add(d);

DonorFile.saveDonors(donors);

System.out.println("Donor registered successfully!");
System.out.println("Donor ID: " + nextId);

nextId++;
    }

    // CHECK VALID BLOOD GROUP
    static boolean isValidGroup(String group) {

        String[] groups = {
            "A+", "A-", "B+", "B-",
            "AB+", "AB-", "O+", "O-"
        };

        for (String g : groups) {
            if (g.equals(group)) {
                return true;
            }
        }

        return false;
    }

    // VIEW DONORS
    static void viewDonors() {

        System.out.println("\n--- All Donors ---");

        if (donors.isEmpty()) {
            System.out.println("No donors registered.");
        } else {
            for (Donor d : donors) {
                d.displayDonor();
            }
        }
    }

    // SEARCH DONOR
    static void searchDonor() {

        System.out.print("Enter blood group: ");
        String group = sc.nextLine().trim().toUpperCase();

        if (isValidGroup(group) == false) {
            System.out.println("Invalid blood group!");
            return;
        }

        boolean found = false;

        for (Donor d : donors) {
            if (d.getBloodGroup().equals(group)) {
                d.displayDonor();
                found = true;
            }
        }

        if (found == false) {
            System.out.println("No donors found!");
        }
    }

    // BLOOD STOCK MENU
    static void manageStock() {

        int choice = 0;

        while (choice != 3) {

            System.out.println("\n--- Blood Stock ---");
            System.out.println("1. Add Blood Stock");
            System.out.println("2. View Blood Stock");
            System.out.println("3. Back to Main Menu");

            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
                choice = 0;
                continue;
            }

            if (choice == 1) {
                addStock();
            } else if (choice == 2) {
                viewStock();
            } else if (choice == 3) {
                System.out.println("Returning to main menu...");
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    // ADD STOCK
    static void addStock() {

        System.out.print("Enter blood group: ");
        String group = sc.nextLine().trim().toUpperCase();

        if (isValidGroup(group) == false) {
            System.out.println("Invalid blood group!");
            return;
        }

        System.out.print("Enter units to add: ");
        int units;

        try {
            units = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid number!");
            return;
        }

        if (units <= 0) {
            System.out.println("Units must be greater than zero!");
            return;
        }

        boolean found = false;

        for (BloodStock s : stocks) {
            if (s.bloodGroup.equals(group)) {
                s.units = s.units + units;
                found = true;
            }
        }

        if (found == false) {
            BloodStock s = new BloodStock(group, units);
            stocks.add(s);
        }

        BloodStockFile.saveStock(stocks);
System.out.println("Blood stock updated successfully!");
    }

    // VIEW STOCK
    static void viewStock() {

        System.out.println("\n--- Blood Stock ---");

        if (stocks.isEmpty()) {
            System.out.println("No blood stock recorded.");
        } else {
            for (BloodStock s : stocks) {
                s.displayStock();
            }
        }
    }

    // BLOOD REQUEST MENU
    static void manageRequests() {

        int choice = 0;

        while (choice != 4) {

            System.out.println("\n--- Blood Requests ---");
            System.out.println("1. Submit Request");
            System.out.println("2. View Requests");
            System.out.println("3. Update Request Status");
            System.out.println("4. Back to Main Menu");

            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
                choice = 0;
                continue;
            }

            if (choice == 1) {
                submitRequest();
            } else if (choice == 2) {
                viewRequests();
            } else if (choice == 3) {
                updateRequestStatus();
            } else if (choice == 4) {
                System.out.println("Returning to main menu...");
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    // SUBMIT REQUEST
    static void submitRequest() {

        System.out.println("\n--- Submit Blood Request ---");

        System.out.print("Enter patient name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty() || name.contains("|")) {
            System.out.println("Enter a valid patient name!");
            return;
        }

        System.out.print("Enter blood group: ");
        String group = sc.nextLine().trim().toUpperCase();

        if (isValidGroup(group) == false) {
            System.out.println("Invalid blood group!");
            return;
        }

        System.out.print("Enter units required: ");
        int units;

        try {
            units = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid number!");
            return;
        }

        if (units <= 0) {
            System.out.println("Units must be greater than zero!");
            return;
        }

        System.out.println("Select urgency:");
        System.out.println("1. Normal");
        System.out.println("2. Urgent");
        System.out.println("3. Emergency");

        System.out.print("Enter choice: ");
        int urgencyChoice;

        try {
            urgencyChoice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
            return;
        }

        String urgency;

        if (urgencyChoice == 1) {
            urgency = "Normal";
        } else if (urgencyChoice == 2) {
            urgency = "Urgent";
        } else if (urgencyChoice == 3) {
            urgency = "Emergency";
        } else {
            System.out.println("Invalid urgency!");
            return;
        }

        BloodRequest r = new BloodRequest(
            nextRequestId, name, group, units, urgency
        );

        requests.add(r);

        // Save the request to a file
        if (saveRequests()) {
            System.out.println("Blood request submitted!");
            System.out.println("Request ID: " + nextRequestId);
            System.out.println("Status: Pending");
            nextRequestId++;
        } else {
            requests.remove(r);
            System.out.println("Request could not be saved.");
            System.out.println("Please try again.");
        }
    }

    // VIEW REQUESTS
    static void viewRequests() {

        System.out.println("\n--- All Blood Requests ---");

        if (requests.isEmpty()) {
            System.out.println("No requests available.");
        } else {
            for (BloodRequest r : requests) {
                r.displayRequest();
            }
        }
    }

    // UPDATE REQUEST STATUS
    static void updateRequestStatus() {
        
        if (!currentRole.equals("ADMIN")) {
    System.out.println("Access denied! Admin only.");
    return;
}

        System.out.print("Enter request ID: ");
        int id;

        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid request ID!");
            return;
        }

        BloodRequest selected = null;

        for (BloodRequest r : requests) {
            if (r.requestId == id) {
                selected = r;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Request not found!");
            return;
        }

        System.out.println("1. Pending");
        System.out.println("2. Approved");
        System.out.println("3. Rejected");
        System.out.println("4. Fulfilled");

        System.out.print("Select new status: ");
        int statusChoice;

        try {
            statusChoice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
            return;
        }

        String oldStatus = selected.status;

        if (statusChoice == 1) {
            selected.status = "Pending";
        } else if (statusChoice == 2) {
            selected.status = "Approved";
        } else if (statusChoice == 3) {
            selected.status = "Rejected";
        } else if (statusChoice == 4) {
            selected.status = "Fulfilled";
        } else {
            System.out.println("Invalid choice!");
            return;
        }

        // Save the updated status
        if (saveRequests()) {
            System.out.println("Request status updated!");
        } else {
            selected.status = oldStatus;
            System.out.println("Could not save the updated status.");
            System.out.println("The previous status has been restored.");
        }
    }

    // SAVE ALL REQUESTS TO FILE
    static boolean saveRequests() {

        try (FileWriter writer = new FileWriter(REQUEST_FILE)) {

            for (BloodRequest r : requests) {

                writer.write(
                    r.requestId + "|" +
                    r.patientName + "|" +
                    r.bloodGroup + "|" +
                    r.units + "|" +
                    r.urgency + "|" +
                    r.status + "\n"
                );
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error saving requests: "
                    + e.getMessage());
            return false;
        }
    }

    // LOAD REQUESTS FROM FILE
    static void loadRequests() {

        File file = new File(REQUEST_FILE);

        if (file.exists() == false) {
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;
            int highestId = 0;

            while ((line = reader.readLine()) != null) {

                String[] details = line.split("\\|", -1);

                if (details.length == 6) {

                    try {
                        int id = Integer.parseInt(details[0]);
                        String name = details[1];
                        String group = details[2];
                        int units = Integer.parseInt(details[3]);
                        String urgency = details[4];
                        String status = details[5];

                        if (id <= 0 || name.isEmpty()
                                || isValidGroup(group) == false
                                || units <= 0
                                || !(urgency.equals("Normal")
                                || urgency.equals("Urgent")
                                || urgency.equals("Emergency"))
                                || !(status.equals("Pending")
                                || status.equals("Approved")
                                || status.equals("Rejected")
                                || status.equals("Fulfilled"))) {

                            System.out.println(
                                "Skipping invalid saved request."
                            );
                            continue;
                        }

                        BloodRequest r = new BloodRequest(
                            id, name, group, units, urgency
                        );

                        r.status = status;
                        requests.add(r);

                        if (id > highestId) {
                            highestId = id;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println(
                            "Skipping an invalid request record."
                        );
                    }
                }
            }

            nextRequestId = highestId + 1;

            if (requests.isEmpty() == false) {
                System.out.println("Saved blood requests loaded.");
            }

        } catch (IOException e) {
            System.out.println("Error loading requests: "
                    + e.getMessage());
        }
    }
}



