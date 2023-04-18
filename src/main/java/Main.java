import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.Purchase;
import org.example.Statistics;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


import static org.example.ServerConfigurations.PORT;


public class Main {
    public static String TsvFile = "categories.tsv";

    public static void main(String[] args) {
        Statistics statistics = new Statistics();
        Statistics.loadTsvFile(TsvFile);
        GsonBuilder builder = new GsonBuilder();


        try (ServerSocket serverSocket = new ServerSocket(PORT);) { // стартуем сервер один(!) раз
            System.out.println("Сервер запущен");
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    System.out.println("Подключен клиент" + socket.getPort());
                    String str = in.readLine();
                    Gson gson = new Gson();


                   Purchase purchase = gson.fromJson(str, Purchase.class);
                    statistics.addPurchase(purchase);

                    Gson gson1 = builder.setPrettyPrinting().create();
                    out.write(gson1.toJson(statistics.Statistics()));
                    System.out.println(gson1.toJson(statistics.Statistics()));


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (
                IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }

    }
}

