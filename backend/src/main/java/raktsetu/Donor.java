package raktsetu;

public class Donor {

    private int donorId;
    private String name;
    private int age;
    private String bloodGroup;
    private String phone;
    private String city;
    private String lastDonationDate; // Added to match your form

    // 1. Default constructor (Required for Spring / JSON deserialization)
    public Donor() {
    }

    // 2. Parameterized constructor
    public Donor(int donorId, String name, int age, String bloodGroup, String phone, String city, String lastDonationDate) {
        this.donorId = donorId;
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.city = city;
        this.lastDonationDate = lastDonationDate;
    }
    public Donor(int donorId, String name, int age, String bloodGroup, String phone, String city) {
        this.donorId = donorId;
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.city = city;
        this.lastDonationDate = ""; // Default empty
    }

    // 3. Getters and Setters (Required for Spring to map JSON fields)
    public int getDonorId() { return donorId; }
    public void setDonorId(int donorId) { this.donorId = donorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getLastDonationDate() { return lastDonationDate; }
    public void setLastDonationDate(String lastDonationDate) { this.lastDonationDate = lastDonationDate; }

    public void displayDonor() {
        System.out.println("Donor ID: " + donorId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Blood Group: " + bloodGroup);
        System.out.println("Phone: " + phone);
        System.out.println("City: " + city);
        System.out.println("Last Donation Date: " + lastDonationDate);
        System.out.println("----------------------");
    }
}
