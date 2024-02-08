package week02.Threads;

import java.util.Deque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        AvailableCoresExecutor exe = new AvailableCoresExecutor();
        System.out.println(AvailableCoresExecutor.getCores());
        for (int i = 0; i < 160; i++) {
            final int j = i;
            exe.execute(() -> {
                System.out.println("task " + j + " started.");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored){}
                System.out.println("task " + j + " ended.");
            });
        }
        exe.shutdown();
    }
    private static class counter {
        private int count = 0;
        public synchronized void increment() {
            count++;
        }

        // Method to retrieve the current count value
        public synchronized int getCount() {
            return count;
        }
    }
}
