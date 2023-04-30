package org.example;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.ServerConfigurations.TsvFile;

public class StatisticsTest extends TestCase implements Serializable {
    Statistics statistics = new Statistics();

    @Test
    public void testLoadTsvFile() {
        Statistics.loadTsvFile("categories.tsv");
        int actual = Statistics.allCategory.size();
        int expected = 8;

        String actualKey = "булка";
        String actualValue = "быт";

        Assertions.assertEquals(expected,actual);
        Assertions.assertTrue(Statistics.allCategory.containsKey(actualKey));
        Assertions.assertTrue(Statistics.allCategory.containsValue(actualValue));


    }
    @Test
    public void testAddPurchase() throws IOException {

        statistics.addPurchase(new Purchase("белка", "2021.03.07",340));
        int actual = statistics.getPurchases().size();
        int expected = 1;

        Assertions.assertEquals(actual, expected);
        Assertions.assertTrue(new File("data.bin").exists());
    }

    @Test
    public void testStatistics() {
        Statistics.loadTsvFile(TsvFile);

        List<Purchase> testList = new ArrayList<>();
        testList.add(new Purchase("будка","2014.12.22",12000));
        testList.add(new Purchase("булка","2023.05.13",200));
        testList.add(new Purchase("мыло","2023.05.04",1600));
        testList.add(new Purchase("тапки","2023.01.28",2340));


        statistics.setPurchases(testList);
        Map<String,MaxCategory> actualMap = (Map<String, MaxCategory>) statistics.statistic();
        int actualSize = actualMap.size();
        int expectedSize = 4;
        String actual = actualMap.get("maxMonthCategory").getCategory();
        String expected = "быт";
        int actualSum = actualMap.get("maxCategory").getSum();
        int expectedSum = 12000;

        Assertions.assertEquals(actualSize, expectedSize);
        Assertions.assertEquals(expected,actual);
        Assertions.assertEquals(actualSum,expectedSum);

    }
    @Test
    public void testLoadDataFile() throws IOException, ClassNotFoundException {

        List<Purchase> actual = Statistics.loadDataFile(new File("src/test//testData.bin"));
        List<Purchase> expected = new ArrayList<>();
        expected.add(new Purchase("белка", "2021.03.07",340));

        Assertions.assertEquals(actual.size(), expected.size());
    }

}