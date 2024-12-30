package kseoni.ch.pkmn.mainapp;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableVaadin
@SpringBootApplication
@EntityScan(basePackages = "kseoni.ch.pkmn.*")
@EnableJpaRepositories(basePackages = "kseoni.ch.pkmn.*")
public class PkmnMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(PkmnMainApplication.class, args);
    }
}