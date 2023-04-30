import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.Purchase;
import org.example.Statistics;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.example.ServerConfigurations.*;


public class Main implements Serializable{

    public static void main(String[] args)  {
        Statistics.loadTsvFile(TsvFile);
        Statistics statistics = new Statistics();


        try (ServerSocket serverSocket = new ServerSocket(PORT)) { // стартуем сервер один(!) раз

            System.out.println("Сервер запущен");

            if(dataFile.exists() && dataFile.canRead()){
                statistics.setPurchases(Statistics.loadDataFile(dataFile));
            }

            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    System.out.println("Новая покупка");
                    String str = in.readLine();
                    Gson gson = new Gson();


                    Purchase purchase = gson.fromJson(str, Purchase.class);
                    System.out.println(purchase.toString());
                    statistics.addPurchase(purchase);

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson1 = builder.setPrettyPrinting().create();
                    out.write(gson1.toJson(statistics.statistic()));
                    System.out.println(gson1.toJson(statistics.statistic()));


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (
                IOException | ClassNotFoundException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }

    }
}

