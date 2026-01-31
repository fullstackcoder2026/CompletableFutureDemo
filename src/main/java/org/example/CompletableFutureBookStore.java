package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureBookStore {

    private static List<Book> books;
    private static List<Order> orders;
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {

        loadData();

        System.out.println("=== CompletableFuture Examples with Book Store Project===\n");

        // 1. supplyAsync - Asynchronous computation
        example1_SupplyAsync();

        // 2. thenApply - Transform result
        example2_ThenApply();

        // 3. thenAccept - Consume result
        example3_ThenAccept();

        // 4. thenRun - Run action after completion
        example4_ThenRun();

        // 5. thenCompose - Chain dependent futures
        example5_ThenCompose();

        // 6. thenCombine - Combine two independent futures
        example6_ThenCombine();

        // 7. allOf - Wait for all futures
        example7_AllOf();

        // 8. anyOf - Wait for any future
        example8_AnyOf();

        // 9. exceptionally - Handle exceptions
        example9_Exceptionally();

        // 10. handle - Handle both result and exception
        example10_Handle();

        // 11. whenComplete - Perform action on completion
        example11_WhenComplete();

        // 12. Complex real-world example
        example12_ComplexOrder();

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);

    }





    // 1. supplyAsync - Run task asynchronously and return result
    private static void example1_SupplyAsync() throws Exception {

    }
    private static void example12_ComplexOrder() {
    }

    private static void example11_WhenComplete() {
    }

    private static void example10_Handle() {
    }

    private static void example9_Exceptionally() {
    }

    private static void example8_AnyOf() {
    }

    private static void example7_AllOf() {
    }

    private static void example6_ThenCombine() {
    }

    private static void example5_ThenCompose() {
    }

    private static void example4_ThenRun() {
    }

    private static void example3_ThenAccept() {
    }

    private static void example2_ThenApply() {
    }

    private static void loadData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        books = mapper.readValue(new File("books.json"), new TypeReference<List<Book>>(){});

        orders = mapper.readValue(new File("orders.json"), new TypeReference<List<Order>>(){});
        System.out.println("Loaded: " + books.size() + " books and " + orders.size() + " orders\n");
    }
}
