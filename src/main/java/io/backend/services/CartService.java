package io.backend.services;

import io.backend.DTO.CartDTO;
import io.backend.entity.Cart;
import io.backend.entity.Item;
import io.backend.repository.CartRepository;
import io.backend.repository.ItemRepository;
import io.backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    public List<CartDTO> all(CartDTO cartDTO) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Cart> cart = Example.of(modelMapper.map(cartDTO, Cart.class), matcher);
        return cartRepository.findAll(cart).stream().map(
                c -> modelMapper.map(c, CartDTO.class)
        ).collect(Collectors.toList());
    }

    @Transactional
    public void add(CartDTO cartDTO) {
        //Check user.
        userRepository.findById(cartDTO.getIdUser()).map(
                user -> {
                    List<Item> items = itemRepository.findAllById(cartDTO.getIdItem());
                    Cart cart = new Cart();
                    cart.setUser(user);
                    cart.setItem(items);
                    cartRepository.save(cart);
                    return cart;
                }
        ).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário inválido")
        );
    }

    public void delete(CartDTO cartDTO) {
        cartRepository.delete(modelMapper.map(cartDTO, Cart.class));
    }

    @Transactional
    public void updateById(CartDTO cartDTO) {
       cartRepository.findById(cartDTO.getId()).map(
               cart -> {
                   List<Item> items = itemRepository.findAllById(cartDTO.getIdItem());
                   cart.setItem(items);
                   cartRepository.save(cart);
                   return cart;
               }
       ).orElseThrow(
               () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrinho inválido")
       );
    }
}
