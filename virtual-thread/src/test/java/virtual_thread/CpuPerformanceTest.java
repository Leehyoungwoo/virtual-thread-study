package virtual_thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CpuPerformanceTest {

    @Test
    public void testCpuIntensiveTaskWithVirtualThreads() throws InterruptedException {
        int numThreads = 500;
        long number = 123456789012345L;

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                CpuIntensiveTask.performCpuIntensiveTask(number);
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();
        System.out.println("Total Time with Virtual Threads: " + (endTime - startTime) + " ms");
    }
}
