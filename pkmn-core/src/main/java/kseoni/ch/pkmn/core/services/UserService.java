package kseoni.ch.pkmn.core.services;

public interface UserService {

    String registerUser(String username, String password);

    String loginUser(String username, String password);
}
