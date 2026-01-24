package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private static final Random random = new Random();

    private static final String[] GENRES = {
            "Fiction", "Mystery", "Science Fiction", "Fantasy", "Romance",
            "Thriller", "Horror", "Biography", "History", "Self-Help"
    };

    private static final String[] AUTHORS = {
            "James Patterson", "Stephen King", "J.K. Rowling", "Dan Brown", "Nora Roberts",
            "John Grisham", "Michael Connelly", "Lee Child", "David Baldacci", "Harlan Coben",
            "Agatha Christie", "Isaac Asimov", "Arthur C. Clarke", "George R.R. Martin", "Brandon Sanderson",
            "Neil Gaiman", "Margaret Atwood", "Toni Morrison", "Ernest Hemingway", "F. Scott Fitzgerald",
            "Jane Austen", "Charles Dickens", "Mark Twain", "Virginia Woolf", "Gabriel García Márquez",
            "Paulo Coelho", "Khaled Hosseini", "Gillian Flynn", "Paula Hawkins", "Suzanne Collins"
    };

    private static final String[] BOOK_TITLES_PREFIX = {
            "The Secret", "Mystery of", "Chronicles of", "Tales from", "Journey to",
            "Legend of", "Return to", "Shadow of", "Heart of", "Edge of",
            "Beyond the", "Last", "First", "Hidden", "Lost",
            "Forgotten", "Dark", "Silent", "Whispers of", "Echoes of"
    };

    private static final String[] BOOK_TITLES_SUFFIX = {
            "Darkness", "Dawn", "Ocean", "Mountain", "City",
            "Night", "Dreams", "Memories", "Hope", "Destiny",
            "Kingdom", "Empire", "Stars", "Moon", "Sun",
            "Fire", "Ice", "Storm", "Wind", "Time"
    };

    private static final String[] CUSTOMER_NAMES = {
            "John Smith", "Emily Johnson", "Michael Williams", "Sarah Brown", "David Jones",
            "Emma Davis", "James Miller", "Olivia Wilson", "Robert Moore", "Sophia Taylor",
            "William Anderson", "Isabella Thomas", "Richard Jackson", "Mia White", "Joseph Harris",
            "Charlotte Martin", "Charles Thompson", "Amelia Garcia", "Christopher Martinez", "Harper Robinson"
    };

    private static final String[] ORDER_STATUS = {
            "PENDING", "PROCESSING", "SHIPPED", "COMPLETED", "CANCELLED"
    };

    public static void main(String[] args) throws Exception {
        generateBooksJson(1000);
        generateOrdersJson(1000);
        System.out.println("Generated books.json and orders.json successfully!");
    }

    private static void generateBooksJson(int count) throws Exception {
        List<Book> books = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            String id = String.format("BK%04d", i);
            String title = generateBookTitle();
            String author = AUTHORS[random.nextInt(AUTHORS.length)];
            String genre = GENRES[random.nextInt(GENRES.length)];
            double price = 9.99 + (random.nextDouble() * 40); // $9.99 to $49.99
            int stock = random.nextInt(100) + 1; // 1 to 100
            double rating = 3.0 + (random.nextDouble() * 2); // 3.0 to 5.0

            books.add(new Book(id, title, author, genre,
                    Math.round(price * 100.0) / 100.0,
                    stock,
                    Math.round(rating * 10.0) / 10.0));
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File("books.json"), books);

        System.out.println("Generated " + count + " books");
    }

    private static void generateOrdersJson(int count) throws Exception {
        List<Order> orders = new ArrayList<>();
        long currentTime = System.currentTimeMillis();
        long oneDay = 24 * 60 * 60 * 1000;

        for (int i = 1; i <= count; i++) {
            String orderId = String.format("ORD%05d", i);
            String bookId = String.format("BK%04d", random.nextInt(1000) + 1);
            String customerName = CUSTOMER_NAMES[random.nextInt(CUSTOMER_NAMES.length)];
            int quantity = random.nextInt(5) + 1; // 1 to 5
            String status = ORDER_STATUS[random.nextInt(ORDER_STATUS.length)];
            long timestamp = currentTime - (random.nextInt(90) * oneDay); // Last 90 days

            orders.add(new Order(orderId, bookId, customerName, quantity, status, timestamp));
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File("orders.json"), orders);

        System.out.println("Generated " + count + " orders");
    }

    private static String generateBookTitle() {
        String prefix = BOOK_TITLES_PREFIX[random.nextInt(BOOK_TITLES_PREFIX.length)];
        String suffix = BOOK_TITLES_SUFFIX[random.nextInt(BOOK_TITLES_SUFFIX.length)];

        if (random.nextBoolean()) {
            return prefix + " " + suffix;
        } else {
            return prefix + " the " + suffix;
        }
    }
}
