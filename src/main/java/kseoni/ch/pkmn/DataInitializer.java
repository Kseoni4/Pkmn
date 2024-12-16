package kseoni.ch.pkmn;

import jakarta.annotation.PostConstruct;
import kseoni.ch.pkmn.dao.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserDao userDao;

    @PostConstruct
    private void init(){
        if (userDao.existsUser("pkmn-admin")){
            return;
        }

        userDao.saveUser(
                "pkmn-admin",
                "admin",
                "ROLE_ADMIN"
        );
    }
}
