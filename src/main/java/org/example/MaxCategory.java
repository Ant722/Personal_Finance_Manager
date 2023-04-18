package org.example;

public class MaxCategory {
     private String category;
     private int sum;

    public MaxCategory(String category, int sum) {
        this.category = category;
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "maxCategory{" + '\n' +
                "category :'" + category + '\n' +
                ", sum :" + sum + '\n' +
                '}';
    }
}
