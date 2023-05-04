package org.example;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
        Map<String, MaxCategory> request = new HashMap<>();

        int lastYear = purchases.stream().mapToInt(Purchase::getYear)
                .filter(p -> p >= 0).max().orElse(0);

        Collection<Purchase> yearListPurchase = purchases.stream()
                .filter(p -> p.getYear() == lastYear)
                .collect(Collectors.toList());

        int lastMonth = yearListPurchase.stream().mapToInt(Purchase::getMonth)
                .filter(p -> p >= 0).max().orElse(0);

        Collection<Purchase> monthListPurchase = yearListPurchase.stream()
                .filter(p -> p.getMonth() == lastMonth)
                .collect(Collectors.toList());

        int lastDay = monthListPurchase.stream().mapToInt(Purchase::getDay)
                .filter(p -> p >= 0).max().orElse(0);

        Collection<Purchase> dayListPurchase = monthListPurchase.stream()
                .filter(p -> p.getDay() == lastDay)
                .collect(Collectors.toList());


        MaxCategory maxCategory = (MaxCategory) definingCategories(purchases);
        MaxCategory maxYearCategory = (MaxCategory) definingCategories(yearListPurchase);
        MaxCategory maxMonthCategory = (MaxCategory) definingCategories(monthListPurchase);
        MaxCategory maxDayCategory = (MaxCategory) definingCategories(dayListPurchase);
        request.put("maxCategory", maxCategory);
        request.put("maxYearCategory", maxYearCategory);
        request.put("maxMonthCategory", maxMonthCategory);
        request.put("maxDayCategory", maxDayCategory);
        return request;
    }

    private Object definingCategories(Collection<Purchase> purchases1) {
        Map<String, Integer> histore = new HashMap<>();
        for (Purchase p : purchases1) {

            String name = p.getTitle();
            int sum = p.getSum();
            if (allCategory.containsKey(name)) {

                String category = allCategory.get(name);

                if (histore.containsKey(category)) {

                    sum += histore.get(category);
                    histore.put(category, sum);

                } else {
                    histore.put(category, sum);
                }
            } else if (histore.containsKey("другое")) {

                sum += histore.get("другое");
                histore.put("другое", sum);
            } else {
                histore.put("другое", sum);
            }

        }
        String category = histore.keySet().stream()
                .max(Comparator.comparing(histore::get))
                .orElse(null);
        return new MaxCategory(category, histore.get(category));
    }
}