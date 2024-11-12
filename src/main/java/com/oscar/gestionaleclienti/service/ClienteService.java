package com.oscar.gestionaleclienti.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.gestionaleclienti.dto.ClienteReadDto;
import com.oscar.gestionaleclienti.dto.ClienteWriteDto;
import com.oscar.gestionaleclienti.exception.ClienteNotFoundException;
import com.oscar.gestionaleclienti.model.Cliente;
import com.oscar.gestionaleclienti.repository.ClienteRepository;

import jakarta.validation.Valid;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ModelMapper modelMapper;

	public List<ClienteReadDto> findAll() {
		List<Cliente> clienti = clienteRepository.findAll();
		return clienti.stream().map(cliente -> modelMapper.map(cliente, ClienteReadDto.class))
				.collect(Collectors.toList());
	}

	public ClienteReadDto save(ClienteWriteDto newClient) {
		Cliente cliente = clienteRepository.save(modelMapper.map(newClient, Cliente.class));
		return modelMapper.map(cliente, ClienteReadDto.class);
	}

	public List<ClienteReadDto> saveListOfClients(List<ClienteWriteDto> listOfClients) {
		List<Cliente> clientes = listOfClients.stream()
				.map(clienteWriteDto -> modelMapper.map(clienteWriteDto, Cliente.class)).collect(Collectors.toList());

		List<Cliente> savedClientes = clienteRepository.saveAll(clientes);

		return savedClientes.stream().map(cliente -> modelMapper.map(cliente, ClienteReadDto.class))
				.collect(Collectors.toList());
	}

	public List<ClienteReadDto> searchByContainPartOfEmail(String str) {
		List<Cliente> clienti = clienteRepository.findByEmailContaining(str);
		return clienti.stream().map(cliente -> modelMapper.map(cliente, ClienteReadDto.class))
				.collect(Collectors.toList());
	}

	public ClienteReadDto getById(long id) {
		Cliente cliente = clienteRepository.findById(id);

		if (cliente == null) {
			throw new ClienteNotFoundException("Cliente con ID: " + id + " non trovato");
		}

		return modelMapper.map(cliente, ClienteReadDto.class);
	}

	public boolean deleteById(long id) {
		if (!clienteRepository.existsById(id))
			throw new ClienteNotFoundException("La fattura con Id numero: " + id + " non esiste");
		clienteRepository.deleteById(id);
		return true;
	}

	public ClienteReadDto updateClient(long id, @Valid ClienteWriteDto newClient) {

		if (!clienteRepository.existsById(id)) {
			throw new ClienteNotFoundException("Cliente con id " + id + " non trovato");
		}

		Cliente cliente = clienteRepository.findById(id);

		cliente.setCognome(newClient.getCognome());
		cliente.setNome(newClient.getNome());
		cliente.setEmail(newClient.getEmail());

		Cliente updatedCliente = clienteRepository.save(cliente);

		return modelMapper.map(updatedCliente, ClienteReadDto.class);
	}

	public List<ClienteReadDto> findClientsLike(String pieceOfString) {
		List<Cliente> clienti = clienteRepository.findByNomeOrCognome(pieceOfString);
		return clienti.stream().map(cliente -> modelMapper.map(cliente, ClienteReadDto.class))
				.collect(Collectors.toList());
	}

}
