package kseoni.ch.pkmn;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableVaadin
//@EnableScheduling
@SpringBootApplication
public class PkmnApplication {
    public static void main(String[] args) {
        SpringApplication.run(PkmnApplication.class, args);
    }
}
