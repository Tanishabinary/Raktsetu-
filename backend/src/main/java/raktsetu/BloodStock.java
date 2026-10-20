package backend.src.main.java.raktsetu;


public class BloodStock 
{

    String bloodGroup;
    int units;

    BloodStock(String bloodGroup, int units) 
    {
        this.bloodGroup = bloodGroup;
        this.units = units;
    }

    void displayStock() 
    {
        System.out.println("Blood Group: " + bloodGroup);
        System.out.println("Available Units: " + units);
        System.out.println("----------------------");
    }
}



