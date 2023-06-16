package io.backend.services;

import io.backend.DTO.AuthDTO;
import io.backend.DTO.UserDTO;
import io.backend.interfaces.IAuth;
import io.backend.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;


@Service
public class AuthService implements IAuth {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AuthDTO login(UserDTO dto) {
        String generatorToken = HttpHeaders.encodeBasicAuth(dto.getEmail(),
                dto.getPassword(),
                StandardCharsets.ISO_8859_1);
        return userRepository.findByBasicToken(generatorToken).map(
                user -> {
                    AuthDTO auth = new AuthDTO();
                    auth.setId(user.getId());
                    auth.setEmail(user.getEmail());
                    auth.setAddress(user.getAddress());
                    auth.setNumberPhone(user.getNumberPhone());
                    auth.setVendor(user.getVendor());
                    auth.setBasicToken(user.getBasicToken());
                    return auth;
                }
        ).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário ou senha inválidos")
        );
    }

    public AuthDTO checkToken(String basicToken) {
        return userRepository.findByBasicToken(basicToken).map(
                user -> modelMapper.map(user, AuthDTO.class)
        ).orElseThrow();
    }

    public void logout() throws ServletException {
        httpServletRequest.logout();
    }
}
