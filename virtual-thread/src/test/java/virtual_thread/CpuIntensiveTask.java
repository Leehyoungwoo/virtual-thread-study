package virtual_thread;

public class CpuIntensiveTask {
    public static void performCpuIntensiveTask(long number) {
        for (long i = 2; i <= number; i++) {
            while (number % i == 0) {
                number /= i;
            }
        }
    }
}
