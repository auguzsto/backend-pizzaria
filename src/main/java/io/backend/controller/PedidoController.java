package io.backend.controller;

import io.backend.DTO.PedidoDTO;
import io.backend.interfaces.IPedido;
import io.backend.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController implements IPedido {

    @Autowired
    private PedidoService pedidoService;

    @Override
    @GetMapping
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<PedidoDTO> all(PedidoDTO dto) {
        return pedidoService.all(dto);
    }

    @Override
    @PostMapping
    @CrossOrigin
    public void add(@RequestBody PedidoDTO dto) {
        pedidoService.add(dto);
    }

    @Override
    public void update(PedidoDTO dto) {

    }

    @Override
    @DeleteMapping
    @CrossOrigin
    public void delete(PedidoDTO dto) {
        pedidoService.delete(dto);
    }


}
