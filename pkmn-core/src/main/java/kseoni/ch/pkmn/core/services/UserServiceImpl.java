package kseoni.ch.pkmn.core.services;

import kseoni.ch.pkmn.core.dao.UserDao;
import kseoni.ch.pkmn.shared.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final JwtService jwtService;

    @Override
    public String registerUser(String username, String password) {

        log.debug("Registering user {} {}",username, password);

        UserDetails user = userDao.saveUser(
                username,
                password,
                "ROLE_USER"
        );

        log.debug("{}",userDao.getUserByUsername(user.getUsername()));

        String token = jwtService.createToken(user.getUsername(), user.getAuthorities().stream().findFirst().orElse(null));

        log.debug("User {} registered with token {}",user.getUsername(), token);

        return token;
    }

    @Override
    public String loginUser(String username, String password) {
        return "";
    }
}

