package file_utilities;

import java.io.*;
import java.util.ArrayList;

public class CSVParser {
    public static ArrayList<String[]> loadCSV(String filename) {
        try {
            ArrayList<String[]> allEntries = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            String line = "";
            while((line = br.readLine()) != null) {
                allEntries.add(line.split(","));
            }
            br.close();
            return allEntries;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void writeCSV(String filename, ArrayList<Object[]> allEntries) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)));
            for(Object[] line : allEntries) {
                for(Object o : line) bw.write(o.toString()+",");
                bw.write('\n');
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
