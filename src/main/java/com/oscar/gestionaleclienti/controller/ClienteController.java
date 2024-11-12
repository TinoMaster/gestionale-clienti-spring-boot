package com.oscar.gestionaleclienti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oscar.gestionaleclienti.dto.ClienteReadDto;
import com.oscar.gestionaleclienti.dto.ClienteWriteDto;
import com.oscar.gestionaleclienti.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clienti")
@CrossOrigin(origins = "*")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	
	@GetMapping
	public List<ClienteReadDto> findAll() {
		return clienteService.findAll();
	}
	
	@GetMapping("/delete/{id}")
	public boolean deleteOne(@PathVariable long id) {
		return clienteService.deleteById(id);
	}

	@GetMapping("/{id}")
	public ClienteReadDto getOneById(@PathVariable long id) {
		return clienteService.getById(id);
	}

	@GetMapping("/cercaemaillike/{str}")
	public List<ClienteReadDto> cercaEmailLike(@PathVariable String str) {
		return clienteService.searchByContainPartOfEmail(str);
	}

	@GetMapping("/cercaclientelike/{str}")
	public List<ClienteReadDto> cercaClienteLike(@PathVariable String str) {
		return clienteService.findClientsLike(str);
	}

	@PostMapping
	public ClienteReadDto save(@Validated @Valid @RequestBody ClienteWriteDto newClient) {
		return clienteService.save(newClient);
	}

	@PostMapping("/addlist")
	public List<ClienteReadDto> saveListOfClient(@Validated @Valid @RequestBody List<ClienteWriteDto> listOfClient) {
		return clienteService.saveListOfClients(listOfClient);
	}

	@PostMapping("/update/{id}")
	public ClienteReadDto updateClient(@PathVariable long id,
			@Validated @Valid @RequestBody ClienteWriteDto newClient) {
		return clienteService.updateClient(id, newClient);
	}
}
