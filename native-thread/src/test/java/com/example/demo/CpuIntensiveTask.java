package com.example.demo;

public class CpuIntensiveTask {
    public static void performCpuIntensiveTask(long number) {
        // 매우 큰 숫자의 소인수 분해를 통해 CPU 소모
        for (long i = 2; i <= number; i++) {
            while (number % i == 0) {
                number /= i;
            }
        }
    }
}
