package kseoni.ch.pkmn.restapi.controllers.v1;

import kseoni.ch.pkmn.shared.models.requests.UserRegisterRequest;
import kseoni.ch.pkmn.core.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public String register(@RequestBody UserRegisterRequest registerRequest){
        return userService.registerUser(
                registerRequest.getUsername(),
                registerRequest.getPassword());
    }


}
