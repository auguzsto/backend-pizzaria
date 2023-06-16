package io.backend.interfaces;

import io.backend.DTO.PedidoDTO;

import java.util.List;

public interface IPedido {

    List<PedidoDTO> all(PedidoDTO dto);

    void add(PedidoDTO dto);

    void update(PedidoDTO dto);

    void delete(PedidoDTO dto);

}
