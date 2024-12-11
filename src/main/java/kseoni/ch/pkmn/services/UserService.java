package kseoni.ch.pkmn.services;

public interface UserService {

    String registerUser(String username, String password);

    String loginUser(String username, String password);
}
