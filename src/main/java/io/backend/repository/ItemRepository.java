package io.backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.backend.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID>{
    @Query(value = "SELECT * FROM item WHERE price_offer > 1;", nativeQuery = true)
    List<Item> findAllPriceOffer();
}
