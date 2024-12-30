package kseoni.ch.pkmn.configserver;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfigCheck {

    @Value("${spring.cloud.config.server.native.search-locations}")
    private String nativeLocations;

    @PostConstruct
    public void init(){
        log.info("Native search locations: {}",nativeLocations);
    }

}
