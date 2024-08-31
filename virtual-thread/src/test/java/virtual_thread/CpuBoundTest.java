package virtual_thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CpuBoundTest {

    public Integer cpuBound() {
        IntStream.range(0, 300_000_000).reduce(0, Integer::sum);
        IntStream.range(0, 300_000_000).reduce(0, Integer::sum);
        return IntStream.range(0, 300_000_000).reduce(0, Integer::sum);
    }

    @Test
    public void testCpuBoundWithVirtualThreads() throws InterruptedException {
        int numThreads = 20;
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            executor.submit(this::cpuBound);
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();
        System.out.println("Total Time with Virtual Threads: " + (endTime - startTime) + " ms");
    }
}
