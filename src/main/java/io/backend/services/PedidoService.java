package io.backend.services;

import io.backend.DTO.PedidoDTO;
import io.backend.entity.Pedido;
import io.backend.entity.Item;
import io.backend.interfaces.IPedido;
import io.backend.repository.PedidoRepository;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService implements IPedido {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PedidoDTO> all(PedidoDTO dto) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Pedido> pedido = Example.of(modelMapper.map(dto, Pedido.class), matcher);
        return pedidoRepository.findAll(pedido).stream().map(
                p -> modelMapper.map(p, PedidoDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void add(PedidoDTO dto) {
        //Check if user are already.
        userRepository.findById(dto.getIdUser()).map(
                user -> {
                    //Creating order.
                    List<Item> items = itemRepository.findAllById(dto.getIdItem());
                    Pedido pedido = new Pedido();
                    pedido.setItem(items);
                    pedido.setCreatedOrder(LocalDateTime.now());
                    pedido.setTotal(
                            items.stream().mapToDouble(
                                    Item::getPrice
                            ).sum()
                    );
                    pedido.setUser(user);
                    pedidoRepository.save(pedido);
                    return pedido;
                }
                )
                //Case error.
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário inválido")
                );
    }

    @Override
    public void update(PedidoDTO dto) {

    }

    @Override
    public void delete(PedidoDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido inválido")
        );

        pedidoRepository.delete(pedido);
    }

}
