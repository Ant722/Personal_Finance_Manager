import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.ServerConfigurations.HOST;
import static org.example.ServerConfigurations.PORT;


public class Client {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<File> persons = new ArrayList<>();
        persons.add(new File("e.json"));
        persons.add(new File("t.json"));
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            while (true) {
                for (File person : persons) {

                    writer.println(person);

                    System.out.println(reader.readLine());

                    writer.println(scanner.nextLine());

                    System.out.println(reader.readLine());
                }
            }

        }

    }
}