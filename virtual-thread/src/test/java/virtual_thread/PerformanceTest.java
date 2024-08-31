package virtual_thread;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import virtual_thread.domain.user.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PerformanceTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testUserAPICreateWithVirtualThreads() throws InterruptedException {
        String url = "http://localhost:8080/api/users";
        int numThreads = 500;
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            executor.submit(() -> {
                User user = User.builder()
                        .username("user" + index)
                        .build();
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "application/json");
                HttpEntity<User> request = new HttpEntity<>(user, headers);

                ResponseEntity<User> response = restTemplate.postForEntity(url, request, User.class);
                System.out.println("Created user: " + response.getBody());
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();
        System.out.println("Total Time with Virtual Threads: " + (endTime - startTime) + " ms");
    }
}