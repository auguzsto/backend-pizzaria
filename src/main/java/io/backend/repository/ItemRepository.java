package io.backend.repository;

import java.util.List;
import java.util.UUID;

import io.backend.DTO.ItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.backend.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID>{
    List<Item> findByPriceOffer(ItemDTO dto);
}
