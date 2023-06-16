package io.backend.entity;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class User {
    
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Campo e-mail deve ser preenchido")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Campo senha deve ser preenchido")
    private String password;

    private Integer vendor;

    @NotNull
    private String address;

    @NotNull @Column(unique = true)
    private String numberPhone;

    @NotNull @Column(unique = true)
    private String basicToken;
    
}
