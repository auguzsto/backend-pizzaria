package io.backend.DTO;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
    
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Double priceOffer;
    private UUID idUser;
    
}
