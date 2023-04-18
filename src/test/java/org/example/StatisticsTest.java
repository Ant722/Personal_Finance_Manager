package org.example;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class StatisticsTest extends TestCase {
    Statistics statistics = new Statistics();


    public void tearDown() throws Exception {
    }
    @Test
    public void testLoadTsvFile() {
        Statistics.loadTsvFile("categories.tsv");
        int actual = Statistics.allCategory.size();
        int expected = 8;

        String actualKey = "тапки";
        String actualValue = "быт";

        Assertions.assertEquals(expected,actual);
        Assertions.assertTrue(Statistics.allCategory.containsKey(actualKey));
        Assertions.assertTrue(Statistics.allCategory.containsValue(actualValue));


    }
    @Test
    public void testAddPurchase() throws FileNotFoundException {
        Purchase purchaseMock = Mockito.mock(Purchase.class);
        Mockito.when(purchaseMock.getTitle()).thenReturn("будка");
        Mockito.when(purchaseMock.getSum()).thenReturn(2000);
        statistics.addPurchase(purchaseMock);
        String actual = "другое";
        int actualValue = 2000;
        Assertions.assertFalse(statistics.getMaxCategory().isEmpty());
        Assertions.assertTrue(statistics.getMaxCategory().containsValue(actualValue));
        Assertions.assertTrue(statistics.getMaxCategory().containsKey(actual));

    }
    @Test
    public void testStatistics() {
        statistics.getMaxCategory().put("финансы", 2400);
        String expected = statistics.Statistics().toString();
        String actual = (new MaxCategory("финансы", 2400)).toString();
        Assertions.assertEquals(expected, actual);
    }
}