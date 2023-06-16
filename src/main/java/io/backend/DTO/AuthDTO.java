package io.backend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AuthDTO {
    private UUID id;
    private String email;
    private String address;
    private String numberPhone;
    private Integer vendor;
    private String basicToken;
}
