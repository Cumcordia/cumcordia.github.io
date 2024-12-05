package kz.goldenfish.goldenfish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class GoldenFishApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoldenFishApplication.class, args);
    }

}
