package raktsetu;

import java.io.*;
import java.util.ArrayList;

public class BloodStockFile {

    static void saveStock(ArrayList<BloodStock> stocks) {

        try (FileWriter writer = new FileWriter("blood_stock.txt")) {

            for (BloodStock stock : stocks) {
                writer.write(stock.bloodGroup + ","
                        + stock.units + "\n");
            }

        } catch (IOException e) {
            System.out.println("Error saving blood stock!");
        }
    }

    static void loadStock(ArrayList<BloodStock> stocks) {

        File file = new File("blood_stock.txt");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 2) {
                    String group = data[0];
                    int units = Integer.parseInt(data[1]);

                    if (raktsetu.isValidGroup(group) && units > 0) {
                        stocks.add(new BloodStock(group, units));
                    }
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading blood stock!");
        }
    }
}


