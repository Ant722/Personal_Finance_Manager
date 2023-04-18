package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;


public class Statistics {
    public static HashMap<String, String> allCategory = new HashMap<>();

    private HashMap<String, Integer> maxCategory = new HashMap<>();


    public static void loadTsvFile(String file) {
        try (BufferedReader TSVFile =
                     new BufferedReader(new FileReader(file))) {

            while (TSVFile.ready()) {
                String row = TSVFile.readLine();
                String[] str = row.split("\t");
                allCategory.put(str[0], str[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPurchase(Purchase p) throws FileNotFoundException {

        String name = p.getTitle();
        int sum = p.getSum();
        if (allCategory.containsKey(name)) {

            String category = allCategory.get(name);

            if (maxCategory.containsKey(category)) {

                sum += maxCategory.get(category);
                maxCategory.put(category, sum);

            } else {
                maxCategory.put(category, sum);
            }
        } else if (maxCategory.containsKey("другое")) {

            sum += maxCategory.get("другое");
            maxCategory.put("другое", sum);
        } else {
            maxCategory.put("другое", sum);

        }
    }

    public Object Statistics() {

        String category = maxCategory.keySet().stream()
                .max(Comparator.comparing(maxCategory::get))
                .orElse(null);
        MaxCategory maxCategor = new MaxCategory(category, maxCategory.get(category));
        return maxCategor;

    }

    @Override
    public String toString() {
        return "Statistics{" +
                "maxCategory=" + maxCategory +
                '}';
    }

}