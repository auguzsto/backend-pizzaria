package io.backend.interfaces;

import java.util.List;

import io.backend.DTO.ItemDTO;

public interface IItem {

    List<ItemDTO> all(ItemDTO dto);

    List<ItemDTO> allOffer(ItemDTO dto);

    void add(ItemDTO dto);

    void update(ItemDTO dto);

    void delete(ItemDTO dto);

}
