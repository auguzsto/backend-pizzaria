package io.backend.entity;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Item {
    
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Campo nome deve ser preenchido.")
    private String name;

    private String description;

    @NotNull(message = "Campo price deve ser preenchido.")
    private Double price;

    private Double priceOffer;
    
    @ManyToOne
    private User user;

}
