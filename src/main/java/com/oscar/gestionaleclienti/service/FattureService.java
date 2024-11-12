package com.oscar.gestionaleclienti.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oscar.gestionaleclienti.dto.FattureReadDto;
import com.oscar.gestionaleclienti.dto.FattureWriteDto;
import com.oscar.gestionaleclienti.exception.ClienteNotFoundException;
import com.oscar.gestionaleclienti.exception.FatturaNotFoundException;
import com.oscar.gestionaleclienti.model.Cliente;
import com.oscar.gestionaleclienti.model.Fattura;
import com.oscar.gestionaleclienti.repository.ClienteRepository;
import com.oscar.gestionaleclienti.repository.FattureRepository;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Service
public class FattureService {

	@Autowired
	private FattureRepository fattureRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ModelMapper modelMapper;

	@PostConstruct
	public void init() {
		modelMapper.addMappings(new PropertyMap<Fattura, FattureReadDto>() {
			@Override
			protected void configure() {
				map().setCliente(source.getCliente().getId());
			}
		});
	}

	public List<FattureReadDto> findAll(boolean scadute, long minImporto, long maxImporto) {
		List<Fattura> fatture = fattureRepository.findAll();

		if (scadute) {
			fatture = fatture.stream().filter(fattura -> fattura.getScadenza().before(new Date()))
					.collect(Collectors.toList());
		}

		fatture = fatture.stream()
				.filter(fattura -> fattura.getImporto() >= minImporto && fattura.getImporto() <= maxImporto)
				.collect(Collectors.toList());

		return fatture.stream().map(fattura -> modelMapper.map(fattura, FattureReadDto.class))
				.collect(Collectors.toList());
	}

	@Transactional
	public FattureReadDto save(FattureWriteDto newFatture) {

		Cliente cliente = clienteRepository.findById(newFatture.getCliente());

		if (cliente == null)
			throw new ClienteNotFoundException("Non si puo creare la fattura, cliente non esiste");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(newFatture.getScadenza(), formatter);

		// Convertir LocalDate a java.util.Date
		Date scadenzaDate = java.sql.Date.valueOf(localDate);

		Fattura fattura = modelMapper.map(newFatture, Fattura.class);
		fattura.setImporto(0);
		fattura.setScadenza(scadenzaDate);
		fattura.setCliente(cliente);

		Fattura savedFattura = fattureRepository.save(fattura);

		return modelMapper.map(savedFattura, FattureReadDto.class);
	}

	public List<FattureReadDto> saveListOfFatture(List<FattureWriteDto> listOfFatture) {
		List<FattureReadDto> listToReturn = new ArrayList<FattureReadDto>();

		for (FattureWriteDto fattureReadDto : listOfFatture) {
			listToReturn.add(this.save(fattureReadDto));
		}

		return listToReturn;
	}

	public boolean deleteOne(long idFattura) {
		if (!fattureRepository.existsById(idFattura))
			throw new FatturaNotFoundException("La fattura con Id numero: " + idFattura + " non esiste");
		fattureRepository.deleteById(idFattura);
		return true;
	}

	public FattureReadDto getOneById(long id) {
		Fattura fattura = fattureRepository.findById(id);

		if (fattura == null) {
			throw new ClienteNotFoundException("Fattura con ID: " + id + " non trovata");
		}

		return modelMapper.map(fattura, FattureReadDto.class);
	}

	public FattureReadDto updateOneById(long id, @Valid FattureWriteDto newFattura) {
		if (!fattureRepository.existsById(id)) {
			throw new FatturaNotFoundException("Fattura con id " + id + " non trovata");
		}

		Fattura fattura = fattureRepository.findById(id);

		Cliente cliente = clienteRepository.findById(newFattura.getCliente());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(newFattura.getScadenza(), formatter);

		// Convertir LocalDate a java.util.Date
		Date scadenzaDate = java.sql.Date.valueOf(localDate);

		fattura.setNumeroFattura(newFattura.getNumeroFattura());

		fattura.setIva(newFattura.getIva());
		fattura.setScadenza(scadenzaDate);
		fattura.setCliente(cliente);

		Fattura updatedFattura = fattureRepository.save(fattura);

		return modelMapper.map(updatedFattura, FattureReadDto.class);
	}

//	private List<Fattura> findFattureScadute() {
//		return fattureRepository.findByScadenzaBefore(new Date());
//
//	}
//
//	private List<Fattura> findByRangeOfImporto(long minImporto, long maxImporto) {
//		return fattureRepository.findByImportoBetween(minImporto, maxImporto);
//
//	}

}
