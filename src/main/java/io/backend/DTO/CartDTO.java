package io.backend.DTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CartDTO {

    private UUID id;
    private List<UUID> idItem;
    private List<ItemDTO> item;
    private UUID idUser;
}
