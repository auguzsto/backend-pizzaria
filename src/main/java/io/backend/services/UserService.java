package io.backend.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.backend.DTO.UserDTO;
import io.backend.entity.User;
import io.backend.interfaces.IUser;
import io.backend.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserDTO> all(UserDTO dto) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<User> user = Example.of(modelMapper.map(dto, User.class), matcher);
        return userRepository.findAll(user).stream().map(
                u -> modelMapper.map(u, UserDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    public void add(UserDTO dto) {
        //Check if username already exists.
        userRepository.findByEmail(dto.getEmail()).map(
            user -> {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado");
            }
        );
        
        //Create new user.
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setNumberPhone(dto.getNumberPhone());
        user.setAddress(dto.getAddress());
        user.setVendor(0);
        user.setBasicToken(HttpHeaders.encodeBasicAuth(dto.getEmail(), dto.getPassword(), StandardCharsets.ISO_8859_1));
       
        userRepository.save(user);
        
    }

    @Override
    public void update(UserDTO dto) {
        //Check if user is exists.
        userRepository.findById(dto.getId()).map(
                user -> {

                    //Set password
                    if(dto.getPassword() == null) dto.setPassword(user.getPassword());
                    if(!dto.getPassword().equals(user.getPassword())) {
                        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
                        user.setBasicToken(HttpHeaders.encodeBasicAuth(user.getEmail(), dto.getPassword(), StandardCharsets.ISO_8859_1));
                    }

                    //Set number phone
                    if(dto.getNumberPhone() == null) dto.setNumberPhone(user.getNumberPhone());
                    if(!dto.getNumberPhone().equals(user.getNumberPhone())) user.setNumberPhone(dto.getNumberPhone());

                    //Set Address
                    if(dto.getAddress() == null) dto.setAddress(user.getAddress());
                    if(!dto.getAddress().equals(user.getAddress())) user.setAddress(dto.getAddress());

                    //Set vendor
                    if(dto.getVendor() == null) dto.setVendor(user.getVendor());
                    if(!dto.getVendor().equals(user.getVendor())) user.setVendor(dto.getVendor());

                    return userRepository.save(user);

                }
        ).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário inválido")
        );
    }

    @Override
    public void delete(UserDTO dto) {
        //Check if user exists.
        User user = userRepository.findById(dto.getId()).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário inválido")
        );

        //Delete user.
        userRepository.delete(user);
    }
    
}
