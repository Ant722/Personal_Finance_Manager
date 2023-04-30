package org.example;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

public class Purchase implements Serializable {
    private String title;
    private String data;
    private int sum;

    public Purchase(String title, String data, int sum) {
        this.title = title;
        this.data = data;
        this.sum = sum;
        System.out.println(this);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
                "\n\tdata" +  ':' + data +
                "\n\tsum" +  ':' + sum +
                "\n" + '}';
    }



}
