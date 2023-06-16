package io.backend.interfaces;

import io.backend.DTO.UserDTO;

import java.util.List;

public interface IUser {

    List<UserDTO> all(UserDTO dto);
    
    void add(UserDTO dto);

    void update(UserDTO dto);

    void delete(UserDTO dto);
    
}
