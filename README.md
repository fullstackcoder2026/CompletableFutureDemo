# CompletableFutureDemo

CompletableFuture Methods Explained
1. supplyAsync() - Start Async Task with Result
   Purpose: Execute a task asynchronously and return a result.
   Syntax: CompletableFuture.supplyAsync(Supplier<T> supplier, Executor executor)
   Example:
   javaCompletableFuture<List<Book>> future = CompletableFuture.supplyAsync(() -> {
   return books.stream()
   .filter(b -> b.getGenre().equals("Fiction"))
   .collect(Collectors.toList());
   }, executor);
   When to use: When you need to perform a time-consuming operation asynchronously and get a result back.

2. runAsync() - Start Async Task without Result
   Purpose: Execute a task asynchronously without returning a result.
   Syntax: CompletableFuture.runAsync(Runnable runnable, Executor executor)
   Example:
   javaCompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
   System.out.println("Sending notification...");
   }, executor);
   When to use: For fire-and-forget operations like logging, notifications, cleanup tasks.

3. thenApply() - Transform Result
   Purpose: Transform the result of a CompletableFuture.
   Syntax: future.thenApply(Function<T, R> fn)
   Example:
   javaCompletableFuture<Double> discountedPrice = bookFuture
   .thenApply(book -> book.getPrice() * 0.8);
   When to use: When you want to transform/map the result synchronously.

4. thenApplyAsync() - Transform Result Asynchronously
   Purpose: Same as thenApply but runs transformation in a separate thread.
   Example:
   javaCompletableFuture<String> result = future
   .thenApplyAsync(book -> processBook(book), executor);
   When to use: When the transformation itself is time-consuming.

5. thenAccept() - Consume Result
   Purpose: Consume the result without returning anything.
   Syntax: future.thenAccept(Consumer<T> consumer)
   Example:
   javabookFuture.thenAccept(book -> {
   System.out.println("Book: " + book.getTitle());
   });
   When to use: When you want to perform an action with the result but don't need to return anything.

6. thenRun() - Run Action After Completion
   Purpose: Run an action after completion without access to the result.
   Syntax: future.thenRun(Runnable action)
   Example:
   javafuture.thenRun(() -> {
   System.out.println("Task completed!");
   });
   When to use: For cleanup or notification after a task completes.

7. thenCompose() - Chain Dependent Futures (FlatMap)
   Purpose: Chain futures where the next future depends on the previous result.
   Syntax: future.thenCompose(Function<T, CompletableFuture<R>> fn)
   Example:
   javaCompletableFuture<Book> result = getOrder()
   .thenCompose(order -> getBookById(order.getBookId()));
   When to use: When you have dependent async operations (like getting an order, then fetching the associated book).

8. thenCombine() - Combine Two Independent Futures
   Purpose: Combine results of two independent futures.
   Syntax: future1.thenCombine(future2, BiFunction<T, U, R> fn)
   Example:
   javaCompletableFuture<String> combined = totalBooksFuture
   .thenCombine(avgPriceFuture, (total, avg) -> {
   return "Total: " + total + ", Avg: " + avg;
   });
   When to use: When you have two independent operations that can run in parallel and you want to combine their results.

9. allOf() - Wait for All Futures
   Purpose: Wait for all futures to complete.
   Syntax: CompletableFuture.allOf(CompletableFuture<?>... futures)
   Example:
   javaCompletableFuture<Void> all = CompletableFuture.allOf(
   future1, future2, future3
   );
   all.join(); // Wait for all to complete
   When to use: When you need all operations to complete before proceeding (e.g., fetching data from multiple sources).

10. anyOf() - Wait for Any Future
    Purpose: Return the result of the first completed future.
    Syntax: CompletableFuture.anyOf(CompletableFuture<?>... futures)
    Example:
    javaCompletableFuture<Object> fastest = CompletableFuture.anyOf(
    db1Query, db2Query, db3Query
    );
    When to use: When you have multiple sources and want the fastest response (e.g., multiple database replicas).

11. exceptionally() - Handle Exceptions
    Purpose: Provide a fallback value in case of exception.
    Syntax: future.exceptionally(Function<Throwable, T> fn)
    Example:
    javaCompletableFuture<Book> result = getBook()
    .exceptionally(ex -> {
    return DEFAULT_BOOK;
    });
    When to use: When you want to provide a default value if an operation fails.

12. handle() - Handle Both Result and Exception
    Purpose: Handle both successful result and exception in one place.
    Syntax: future.handle(BiFunction<T, Throwable, R> fn)
    Example:
    javaCompletableFuture<String> result = future.handle((book, ex) -> {
    if (ex != null) {
    return "Error: " + ex.getMessage();
    }
    return "Success: " + book.getTitle();
    });
    When to use: When you need to handle both success and failure cases and transform them into a consistent result type.

13. whenComplete() - Perform Action on Completion
    Purpose: Perform an action when the future completes (success or failure) without transforming the result.
    Syntax: future.whenComplete(BiConsumer<T, Throwable> action)
    Example:
    javafuture.whenComplete((result, ex) -> {
    if (ex != null) {
    logger.error("Failed", ex);
    } else {
    logger.info("Success: " + result);
    }
    });
    When to use: For logging or monitoring without changing the result.

14. completeExceptionally() - Complete with Exception
    Purpose: Manually complete a future with an exception.
    Example:
    javaCompletableFuture<Book> future = new CompletableFuture<>();
    future.completeExceptionally(new BookNotFoundException());

15. complete() - Manually Complete Future
    Purpose: Manually complete a future with a result.
    Example:
    javaCompletableFuture<Book> future = new CompletableFuture<>();
    future.complete(book);

Key Differences
thenApply vs thenCompose

thenApply: Returns T → Wraps it in CompletableFuture<T>
thenCompose: Returns CompletableFuture<T> → Flattens it to CompletableFuture<T>

thenApply vs thenApplyAsync

thenApply: Runs in the same thread as the previous stage
thenApplyAsync: Runs in a separate thread from the executor

exceptionally vs handle

exceptionally: Only called on exception
handle: Called on both success and exception


Best Practices

Always use ExecutorService: Don't rely on ForkJoinPool.commonPool()
Handle exceptions: Always add error handling
Avoid blocking: Don't call .get() unnecessarily, use callbacks
Chain wisely: Use thenCompose for dependent operations, thenCombine for independent ones
Shut down executor: Always shutdown the executor when done


Common Patterns
Pattern 1: Sequential Dependent Operations
javagetUserOrder()
.thenCompose(order -> getBookDetails(order.getBookId()))
.thenCompose(book -> validateInventory(book))
.thenApply(book -> calculatePrice(book))
.thenAccept(price -> processPayment(price));
Pattern 2: Parallel Independent Operations
javaCompletableFuture<Integer> stock = getStockCount();
CompletableFuture<Double> price = getAveragePrice();
CompletableFuture<Long> orders = getOrderCount();

CompletableFuture.allOf(stock, price, orders).join();
// Now all three are complete
Pattern 3: Timeout Handling
javaCompletableFuture<Book> future = getBook()
.orTimeout(5, TimeUnit.SECONDS)
.exceptionally(ex -> DEFAULT_BOOK);

Running the Project

Generate data:

bash   javac DataGenerator.java Book.java Order.java
java DataGenerator

Run main application:

bash   javac CompletableFutureBookStore.java Book.java Order.java
java CompletableFutureBookStore
This will demonstrate all 12 CompletableFuture patterns with real book and order data!