package week01.opgave9;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Task {

    public void run() throws InterruptedException {
        System.out.println("task begun");
        Thread.sleep(10000);
        System.out.println("task ended");
    }

    public static void main(String[] args) {
        //// opgave 9.2
        List<CompletableFuture<Void>> completables = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            completables.add(CompletableFuture.runAsync(() -> {
                try {
                    new Task().run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e); //// it wouldn't run without a try catch here...
                }
            }).exceptionally((e) -> {
                //noinspection ThrowablePrintedToSystemOut
                System.out.println(e);
                return null;
            }));
        }
        CompletableFuture<Void> allFutures = null;
        for (CompletableFuture<Void> com : completables) {
            if (allFutures == null)
                allFutures = com;
            else
                allFutures = CompletableFuture.allOf(allFutures, com);
        }
        if(allFutures != null)
            allFutures.thenRun(() -> System.out.println("All Tasks Complete"));


        //// opgave 9.3
        ExecutorService exes = Executors.newFixedThreadPool(4);
        List<Task> tasksToExe = new ArrayList<>();
        IntStream.range(0, 12).forEach((i) -> tasksToExe.add(new Task()));
        tasksToExe.forEach(x -> exes.submit(() -> {
            try {
                x.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
                //// We kinda need to be thought how to avoid NEEDING a try catch like this.
                //// Btw normally we would use an interface like Runnable to run tasks like this, but that doesn't include throwable on the run function.
                //// so that's not possible.
            }
        }));
        exes.shutdown();
        System.out.println("Executer shutdown, it wouldn't do it's tasks.");
    }
}
