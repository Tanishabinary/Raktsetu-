package raktsetu;


import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class DonorFile {

    static void saveDonors(ArrayList<Donor> donors) {

        try {
            FileWriter writer = new FileWriter("donors.txt");

            for (Donor donor : donors) {

                writer.write(
                    donor.getDonorId() + "," +
                    donor.getName() + "," +
                    donor.getAge() + "," +
                    donor.getBloodGroup() + "," +
                    donor.getPhone() + "," +
                    donor.getCity() + "\n"
                );
            }

            writer.close();

            System.out.println("Donor data saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving donor data!");
        }
    }

    static void loadDonors(ArrayList<Donor> donors) {

        try {
            BufferedReader reader =
                new BufferedReader(new FileReader("donors.txt"));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 6) {

                    Donor donor = new Donor(
                        Integer.parseInt(data[0]),
                        data[1],
                        Integer.parseInt(data[2]),
                        data[3],
                        data[4],
                        data[5]
                    );

                    donors.add(donor);
                }
            }

            reader.close();

            System.out.println("Donor data loaded successfully!");

        } catch (IOException e) {
            System.out.println("No previous donor data found.");
        }
    }
}



