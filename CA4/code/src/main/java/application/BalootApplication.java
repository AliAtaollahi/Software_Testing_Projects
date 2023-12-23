package application;

import service.Baloot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "controllers")
public class BalootApplication {
    public static void main(String[] args) {
        Baloot.getInstance().fetchAndStoreData();
        SpringApplication.run(BalootApplication.class, args);

    }
}
