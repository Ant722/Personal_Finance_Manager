package org.example;

import java.io.*;
import java.util.*;

import static org.example.ServerConfigurations.dataFile;


public class Statistics implements Serializable {

    public static Map<String, String> allCategory = new HashMap<>();

    private List<Purchase> purchases = new ArrayList<>();



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
    public void addPurchase(Purchase purchase) throws IOException {
        purchases.add(purchase);
        saveData();
    }
    public void saveData() throws IOException {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            out.writeObject(this.purchases);
        }
    }

    public static List<Purchase> loadDataFile(File file) throws IOException, ClassNotFoundException {

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {

            return (List<Purchase>) inputStream.readObject();
        }
    }
    public List<Purchase> getPurchases() {
        return purchases;
    }
    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public Object statistic() {
        Map<String, Integer> historeCategory = new HashMap<>();
        for (Purchase p : purchases) {

            String name = p.getTitle();
            int sum = p.getSum();
            if (allCategory.containsKey(name)) {

                String category = allCategory.get(name);

                if (historeCategory.containsKey(category)) {

                    sum += historeCategory.get(category);
                    historeCategory.put(category, sum);

                } else {
                    historeCategory.put(category, sum);
                }
            } else if (historeCategory.containsKey("другое")) {

                sum += historeCategory.get("другое");
                historeCategory.put("другое", sum);
            } else {
                historeCategory.put("другое", sum);
            }

        }
        String category = historeCategory.keySet().stream()
                .max(Comparator.comparing(historeCategory::get))
                .orElse(null);
        return new MaxCategory(category,historeCategory.get(category));

    }
}