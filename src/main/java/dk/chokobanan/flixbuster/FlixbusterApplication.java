package dk.chokobanan.flixbuster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication(scanBasePackages = {"dk.chokobanan.flixbuster"})
@EnableNeo4jRepositories("dk.chokobanan.flixbuster.repository")
public class FlixbusterApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlixbusterApplication.class, args);

    }
}
