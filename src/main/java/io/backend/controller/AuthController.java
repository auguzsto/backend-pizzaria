package io.backend.controller;

import io.backend.DTO.AuthDTO;
import io.backend.DTO.UserDTO;

import io.backend.interfaces.IAuth;
import io.backend.services.AuthService;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController implements IAuth {

    @Autowired
    private AuthService authService;

    @Override
    @PostMapping
    @CrossOrigin
    public AuthDTO login(@RequestBody UserDTO dto) {
        return authService.login(dto);
    }

    @PostMapping("/logout")
    @CrossOrigin
    public void logout() throws ServletException {
        authService.logout();
    }
}
