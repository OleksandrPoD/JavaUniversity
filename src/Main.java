/*
Створити файл записів з відомостями про експортовані товари: найменування товару,
країна - експортер, обсяг у штуках. Визначити країни (в алфавітному порядку), в які
експортується даний товар і загальний обсяг його експорту.
*/

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<ExportProduct> products = new ArrayList<>();

        try {

            List<String> lines = Files.readAllLines(Path.of("products.txt"));

            for (String line : lines) {

                String[] parts = line.split(";");

                String productName = parts[0];
                String country = parts[1];
                int quantity = Integer.parseInt(parts[2]);

                products.add(new ExportProduct(productName, country, quantity));
            }

            Scanner scanner = new Scanner(System.in);

            System.out.print("Введіть назву товару: ");
            String targetProduct = scanner.nextLine();

            List<String> countries = new ArrayList<>();
            int totalQuantity = 0;

            for (ExportProduct p : products) {

                if (p.productName.equalsIgnoreCase(targetProduct)) {

                    countries.add(p.country);
                    totalQuantity += p.quantity;
                }
            }

            if (countries.isEmpty()) {

                System.out.println("Товар не знайдено");

            } else {

                Collections.sort(countries);

                System.out.println("\nКраїни експорту:");

                for (String country : countries) {
                    System.out.println(country);
                }

                System.out.println("\nЗагальний обсяг експорту: " + totalQuantity);
            }

        } catch (IOException e) {

            System.out.println("Помилка читання файлу");
        }
    }
}