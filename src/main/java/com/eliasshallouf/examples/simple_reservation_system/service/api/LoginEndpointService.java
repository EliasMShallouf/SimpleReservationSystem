package com.eliasshallouf.examples.simple_reservation_system.service.api;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.User;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.request.LoginModel;
import com.eliasshallouf.examples.simple_reservation_system.service.db.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class LoginEndpointService {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody LoginModel loginModel) {
        return userService.login(loginModel.getEmail(), loginModel.getPassword());
    }
}
