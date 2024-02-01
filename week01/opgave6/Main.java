package week01.opgave6;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        //// Setup aka. opgave 6.2
        List<Book> books = new ArrayList<>();
        BiConsumer<String, String> createBook = (title, author) -> {
            Random rng = new Random();
            books.add(
                    new Book(title,
                            author,
                            rng.nextInt(2000, 2024),
                            rng.nextInt(600),
                            rng.nextFloat(5)));
        };
        createBook.accept("Hairy Potter", "J. K. Rolling");
        createBook.accept("Lord Of Rings", "Jeremy Mayer");
        createBook.accept("The Goblet","J. K. Rolling");
        createBook.accept("Mage Apprentice", "Firestones");
        createBook.accept("The Jaded Compass","Pullman, P");
        createBook.accept("Christian's Downfall","Christian Høj");
        createBook.accept("Nicklas's Confusion", "Nicklas");
        createBook.accept("Patrick's Triumph","Patrick");


        //// opgave 6.3 - Find the average rating of all books.
        System.out.println("\nFind the average rating of all books.");

        //// Denne lavede jeg 2 af, fandt ud af generic lambda function ikke eksistere.
        Function<List<Float>, Float> myAvgFunction = list -> {
            return IntStream.range(0, list.size()).boxed()
                    .map(Integer::floatValue)
                    .reduce(0f, (y1, y2) -> y1 - (y1 - list.get(y2.intValue())) / (y2 + 1));
        };

        BiFunction<List<Book>, Function<Book, Float>, Number> mySecondAvgFunction = (list, function) ->
                IntStream
                        .range(0, list.size()).boxed()
                        .map(Integer::floatValue)
                        .reduce(0f, (y1, y2) -> y1 - (y1 - function.apply(list.get(y2.intValue()))) / (y2 + 1));


        float myAvg = myAvgFunction.apply(books.stream().map(Book::getRating).toList());
        System.out.println("Generic, but requires it to be a list of floats" + myAvg);

        float mySecAvg = mySecondAvgFunction.apply(books, Book::getRating).floatValue();
        System.out.println("Specifically needs books, but can be used for any float value of the book" + mySecAvg);


        //// opgave 6.3 - Filter and display books published after a specific year.
        int filteredYear = 2014;
        System.out.println("\nFilter and display books published after a specific year. Year: " + filteredYear);
        BiFunction<List<Book>, Integer, List<Book>> MyFilterBooksByYear = (list, year) -> list.stream().filter(book -> book.getYear() == year).toList();
        System.out.println(MyFilterBooksByYear.apply(books, filteredYear));


        //// opgave 6.3 - Sort books by rating in descending order.
        System.out.println("\nSort books by rating in descending order.");
        BiFunction<List<Book>, Comparator<? super Book>, List<Book>> mySortByDesc = (list, getNum) -> list.stream().sorted(getNum.reversed()).toList();
        List<Book> filteredRevSortedList = mySortByDesc.apply(books, (o1, o2) -> Float.compare(o1.getRating(), o2.getRating()));
        System.out.println(filteredRevSortedList);


        //// opgave 6.3 - Find and display the book with the highest rating.
        System.out.println("\nFind and display the book with the highest rating.");

        //// orElse er for ikke, at give en fejl i tilfælde der ikke er nogen bøger.
        Book mostPopularBook = mySortByDesc.apply(books, (o1, o2) -> Float.compare(o1.getRating(), o2.getRating())).stream().findFirst().orElse(new Book());
        System.out.println(mostPopularBook);


        //// opgave 6.3 - Group books by author and calculate the average rating for each author.
        System.out.println("\nGroup books by author and calculate the average rating for each author.");

        BiFunction<List<Book>, Function<Book, String>, Map<String, List<Book>>> myGroupFunction = (list, function) -> list.stream().collect(Collectors.groupingBy(function));
        Map<String, List<Book>> groupedBooksByAuthor = myGroupFunction.apply(books, (Book::getAuthor));
        System.out.println(groupedBooksByAuthor);


        //// opgave 6.3 - Calculate the total number of pages for all books (assuming each book has a fixed number of pages).
        System.out.println("\nCalculate the total number of pages for all books (assuming each book has a fixed number of pages).");

        //// dunno why we should assume, but I'll make one that assumes and one that gets the actual amount (that has been randomly set at setup)
        BiFunction<List<Book>, Integer, Integer> approxPagesFromBooks = (list, pagesPerBook) -> list.stream()
                .map(x -> pagesPerBook)
                .reduce(Integer::sum)
                .orElse(0);
        System.out.println("Assuming 500 pages per book: " + approxPagesFromBooks.apply(books, 500));

        Function<List<Book>, Integer> sumOfPages = list -> list.stream()
                .map(Book::getPages)
                .reduce(Integer::sum)
                .orElse(0);
        System.out.println("Actual sum of pages: " + sumOfPages.apply(books));


        //// opgave 6.4 - Chain multiple Stream operations together to perform complex tasks, such as filtering and sorting.
        System.out.println("\nChain multiple Stream operations together to perform complex tasks, such as filtering and sorting.");
        Map<String, List<Book>> myComplextasks = books.stream().filter(book -> book.getPages() > 200).collect(Collectors.groupingBy(Book::getAuthor));
        myComplextasks.keySet().forEach(author -> {
            List<Book> booksByAuthor = myComplextasks.get(author);
            System.out.println(author + booksByAuthor.size() + ": ");
            booksByAuthor.forEach(System.out::println);
        });

        //// opgave 6.5 - I kinda did that as I assumed that was the point of doing every other task.
    }
}
