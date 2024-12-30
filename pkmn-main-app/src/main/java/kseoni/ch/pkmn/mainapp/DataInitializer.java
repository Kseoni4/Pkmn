package kseoni.ch.pkmn.mainapp;

import jakarta.annotation.PostConstruct;
import kseoni.ch.pkmn.core.dao.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Import({UserDao.class})
@RequiredArgsConstructor
public class DataInitializer {

    private final UserDao userDao;

    @PostConstruct
    private void init(){

        if (userDao.existsUser("pkmn-user")){
            return;
        }

        userDao.saveUser(
                "pkmn-user",
                "user",
                "ROLE_USER"
        );

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
