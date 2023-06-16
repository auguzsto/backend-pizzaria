package io.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.backend.DTO.UserDTO;
import io.backend.interfaces.IUser;
import io.backend.services.UserService;
import jakarta.websocket.server.PathParam;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController  implements IUser {

    @Autowired
    private UserService userService;

    @Override
    @GetMapping
    @CrossOrigin
    public List<UserDTO> all(UserDTO dto) {
        return userService.all(dto);
    }

    @Override
    @PostMapping
    @CrossOrigin
    public void add(@RequestBody UserDTO dto) {
        userService.add(dto);
    }

    @Override
    @PatchMapping
    @CrossOrigin
    public void update(UserDTO dto) {
        userService.update(dto);
    }

    @Override
    @DeleteMapping("/{id}")
    @CrossOrigin
    public void delete(@PathParam("id") UserDTO dto) {
        userService.delete(dto);
    }
    
}
