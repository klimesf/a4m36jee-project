package cz.cvut.fel.a4m36jee.airlines.frontend.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CSVfileReader {



    // for TestNG data provider
    public static Iterator<String[]> readCSVfileToIterator(String fileName) {
        List<String[]> records = new ArrayList<String[]>();
        String record;

        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName));
            while ((record = file.readLine()) != null) {
                String fields[] = record.split(";");
                records.add(fields);
            }
            file.close();
            return records.iterator();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // for JUnit data provider
    public static Collection<String[]> readCSVfileToCollection(String fileName) {
        List<String[]> records = new ArrayList<String[]>();
        String record;

        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName));
            while ((record = file.readLine()) != null) {
                String fields[] = record.split(";");
                records.add(fields);
            }
            file.close();
            return records;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}