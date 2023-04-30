package org.example;


import java.io.Serializable;

public class Purchase implements Serializable {
    private String title;
    private String date;
    private int sum;

    public Purchase(String title, String date, int sum) {
        this.title = title;
        this.sum = sum;
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDay() {
        int day = Integer.parseInt(date.substring(8,10));
        return day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMonth() {
        int month = Integer.parseInt(date.substring(5,7));
        return month;
    }

    public int getYear() {
        int year = Integer.parseInt(date.substring(0,4));
        return year;
    }


    public int getSum() {
        return sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Purchase{\n" +
                "\n\ttitle" + ':' + title +
                "\n\tdate" +  ':' + date +
                "\n\tsum" +  ':' + sum +
                "\n" + '}';
    }



}