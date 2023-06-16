package io.backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import io.backend.DTO.ItemDTO;
import io.backend.entity.Item;
import io.backend.interfaces.IItem;
import io.backend.repository.ItemRepository;
import io.backend.repository.UserRepository;

@Service
public class ItemService implements IItem {
    
    @Autowired
    private ItemRepository itemRepository;

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

	@Override
	public List<ItemDTO> all(ItemDTO dto) {
		//Return list of the products
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example<Item> item = Example.of(modelMapper.map(dto, Item.class), matcher);
		return itemRepository.findAll(item).stream().map(
				i -> modelMapper.map(i, ItemDTO.class)
		).collect(Collectors.toList());
	}

	@Override
	public List<ItemDTO> allOffer(ItemDTO dto) {
		return itemRepository.findByPriceOffer(dto).stream().map(
				item -> modelMapper.map(item, ItemDTO.class)
		).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void add(ItemDTO dto) {
		//Get user && check if user exists.
		userRepository.findById(dto.getIdUser()).map(
				user -> {
					if(user.getVendor() == 0) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não é vendedor");
					}

					Item item = new Item();
					item.setName(dto.getName());
					item.setPrice(dto.getPrice());
					if(dto.getPriceOffer() == null) item.setPriceOffer(0.0); else item.setPriceOffer(dto.getPriceOffer());
					if(dto.getDescription() == null) item.setDescription("Não há descrição neste item"); else item.setDescription(dto.getDescription());
					item.setUser(user);

					return itemRepository.save(item);

				}
		).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário inválido"));
	}

	@Override
	public void update(ItemDTO dto) {
		//Check if item is exists.
		itemRepository.findById(dto.getId()).map(
				item -> {

					//Set name
					if(dto.getName() == null) dto.setName(item.getName());
					if(!dto.getName().equals(item.getName())) item.setName(dto.getName());

					//Set price
					if(dto.getPrice() == null) dto.setPrice(item.getPrice());
					if(!dto.getPrice().equals(item.getPrice())) item.setPrice(dto.getPrice());

					//Set price offer
					if(dto.getPriceOffer() == null) dto.setPriceOffer(item.getPriceOffer());
					if(!dto.getPriceOffer().equals(item.getPriceOffer())) item.setPriceOffer(dto.getPriceOffer());

					//Set description
					if(dto.getDescription() == null) dto.setDescription(item.getDescription());
					if(!dto.getDescription().equals(item.getDescription())) item.setDescription(dto.getDescription());

					return itemRepository.save(item);

				}
		).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item inválido")
		);
	}

	@Override
	public void delete(ItemDTO dto) {
		//Check if product exists.
		Item item = itemRepository.findById(dto.getId()).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto inválido.")
		);

		//Delete product.
		itemRepository.delete(item);
	}
   
}
