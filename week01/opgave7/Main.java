package week01.opgave7;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        //// opgave 7.1
        Collection<Transaction> transactions = new ArrayDeque<>();


        //// opgave 7.2 - Setup
        try {
            Scanner sc = new Scanner(new File("week01/opgave7/transactions.txt"));
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split(",");
                if (line.length == 3)
                    transactions.add(new Transaction(Integer.parseInt(line[0].trim()), Integer.parseInt(line[1].trim()), line[2].trim()));
                else
                    System.err.println(String.join("", line) + " - does not have only 2 commas.");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        //// opgave 7.3 - Calculate the total sum of all the transaction amounts.
        int sumOfAmounts = transactions.stream().mapToInt(Transaction::getAmount).sum();
        System.out.println("Sum of all amounts: " + sumOfAmounts);

        //// opgave 7.3 - Group transactions by currency and calculate the sum of amounts for each currency.
        Map<String, List<Transaction>> mapped = transactions.stream().collect(Collectors.groupingBy(Transaction::getCurrency));
        //// the initiator of the Collection.
        Map<String, Integer> currencyTransactions = mapped.keySet().stream()
                .map(currency -> new Object[]{currency,
                                mapped.get(currency).stream()
                                        .mapToInt(Transaction::getAmount)
                                        .reduce(Integer::sum)
                                        .orElse(0)
                        }
                ).collect(Collector.of(HashMap::new,
                        (a, b) -> {
                            //// a is the Collection, b is each item.
                            a.put(b[0].toString(), (Integer) b[1]);
                        },
                        //// this is not run for collect, it must have something to do with something else.
                        (c, d) -> (c != null) ? c : d
                ));

        //// with the knowledge I unlocked from that experiment I here try to make it shorter:
        Map<String, Integer> currencyTransactionsShorter = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCurrency))
                .entrySet().stream()
                .collect(Collector.of(
                        HashMap::new,
                        (collection, item) -> collection.put(
                                item.getKey(),
                                item.getValue().stream()
                                        .map(Transaction::getAmount)
                                        .reduce(Integer::sum).orElse(0)),
                        (itemA, itemB) -> (itemA != null) ? itemA : itemB)
                );
        System.out.println("\nsum of groups of currency: (I made 2 cause I felt like practicing)");
        System.out.println(currencyTransactions);
        System.out.println(currencyTransactionsShorter);


        //// opgave 7.3 - Find the highest transaction amount.
        System.out.println("\nFind the highest transaction amount. (first as the whole transaction, second as only the amount)");
        System.out.println(transactions.stream().max(Comparator.comparingInt(Transaction::getAmount)).orElse(null));
        System.out.println(transactions.stream().mapToInt(Transaction::getAmount).max().orElse(0));


        //// opgave 7.3 - Find the average transaction amount.
        //// this is done in 2 steps mostly cause otherwise the compiler don't know what the map is, and thinks everything is a object...
        //// Which means I have to cast everything, which I am not gonna.
        Map<Integer, Float> map = transactions.stream().collect(
                Collector.of(
                        HashMap::new,
                        (a, b) -> a.put(a.size() + 1, (float) b.getAmount()),
                        (c, d) -> c
                )
        );
        Float averageAmount = map.entrySet().stream().reduce((a, b) -> {
            a.setValue(a.getValue() - ((a.getValue() - b.getValue()) / b.getKey()));
            return a;
        }).map(Map.Entry::getValue).orElse(null);
        System.out.println("\nFind the average transaction amount.");
        System.out.println(averageAmount);


        //// opgave 7.4 - Allerede Gjort
    }
}
