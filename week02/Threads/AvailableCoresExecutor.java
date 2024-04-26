package week02.Threads;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

public class AvailableCoresExecutor extends ThreadPoolExecutor {
    private static int cores = Runtime.getRuntime().availableProcessors();

    public AvailableCoresExecutor() {
        super(cores, cores, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }
    public static int getCores(){
        return cores;
    }
}
