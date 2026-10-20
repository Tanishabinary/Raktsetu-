package backend.src.main.java.raktsetu;


public class Donor {

    int donorId;
    String name;
    int age;
    String bloodGroup;
    String phone;
    String city;

    Donor(int donorId, String name, int age,
          String bloodGroup, String phone, String city) {

        this.donorId = donorId;
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.city = city;
    }

    void displayDonor() {

        System.out.println("Donor ID: " + donorId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Blood Group: " + bloodGroup);
        System.out.println("Phone: " + phone);
        System.out.println("City: " + city);
        System.out.println("----------------------");
    }
}

