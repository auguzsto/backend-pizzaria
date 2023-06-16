package io.backend.controller;

import io.backend.DTO.CartDTO;
import io.backend.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    @CrossOrigin
    public List<CartDTO> all(CartDTO cartDTO) {
        return cartService.all(cartDTO);
    }

    @PostMapping
    @CrossOrigin
    public void add(@RequestBody CartDTO cartDTO) {
        cartService.add(cartDTO);
    }

    @DeleteMapping
    @CrossOrigin
    public void delete(CartDTO cartDTO) {
        cartService.delete(cartDTO);
    }

    @PatchMapping
    @CrossOrigin
    public void updateById(@RequestBody CartDTO cartDTO) {
        cartService.updateById(cartDTO);
    }

}
