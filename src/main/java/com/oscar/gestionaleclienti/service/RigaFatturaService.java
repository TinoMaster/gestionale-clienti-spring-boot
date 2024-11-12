package com.oscar.gestionaleclienti.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.gestionaleclienti.dto.FattureReadDto;
import com.oscar.gestionaleclienti.dto.RigheDetaglioReadDto;
import com.oscar.gestionaleclienti.dto.RigheDetaglioWriteDto;
import com.oscar.gestionaleclienti.exception.FatturaNotFoundException;
import com.oscar.gestionaleclienti.exception.RigaFatturaNotFoundException;
import com.oscar.gestionaleclienti.model.Cliente;
import com.oscar.gestionaleclienti.model.Fattura;
import com.oscar.gestionaleclienti.model.RigaFattura;
import com.oscar.gestionaleclienti.repository.FattureRepository;
import com.oscar.gestionaleclienti.repository.ProdottoRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class RigaFatturaService {

	@Autowired
	private ProdottoRepository prodottoRepository;

	@Autowired
	private FattureRepository fattureRepository;

	@Autowired
	private ModelMapper modelMapper;

	@PostConstruct
	public void init() {
		modelMapper.addMappings(new PropertyMap<RigaFattura, RigheDetaglioReadDto>() {
			@Override
			protected void configure() {
				map().setFattura(source.getFattura().getId());
			}
		});
	}

	public List<RigheDetaglioReadDto> findAll() {
		List<RigaFattura> rigaFattura = prodottoRepository.findAll();

		return rigaFattura.stream().map(prodotto -> modelMapper.map(prodotto, RigheDetaglioReadDto.class))
				.collect(Collectors.toList());
	}

	public RigheDetaglioReadDto findOneById(long id) {
		RigaFattura riga = prodottoRepository.findById(id);

		if (riga == null)
			throw new RigaFatturaNotFoundException("prodotto con id " + id + " non trovato");

		return modelMapper.map(riga, RigheDetaglioReadDto.class);
	}

	@Transactional
	public RigheDetaglioReadDto save(RigheDetaglioWriteDto rigaFatturaToSave) {
		Fattura fattura = fattureRepository.findById(rigaFatturaToSave.getFattura());

		if (fattura == null)
			throw new FatturaNotFoundException("Non si puo creare il produtto, fattura non esiste");

		RigaFattura rigaFattura = modelMapper.map(rigaFatturaToSave, RigaFattura.class);
		rigaFattura.setFattura(fattura);

		RigaFattura savedRiga = prodottoRepository.save(rigaFattura);

		long totalImporto = fattura.getProdotti().stream().mapToLong(p -> p.getPrice() * p.getQuantita()).sum();

		fattura.setImporto(totalImporto);
		fattureRepository.save(fattura);

		return modelMapper.map(savedRiga, RigheDetaglioReadDto.class);
	}

	@Transactional
	public boolean deleteOne(long idRiga) {
		if (!prodottoRepository.existsById(idRiga))
			throw new RigaFatturaNotFoundException("La Riga Fattura col id: " + idRiga + " non esiste");

		RigaFattura riga = prodottoRepository.findById(idRiga);
		prodottoRepository.deleteById(idRiga);

		long idFattura = riga.getFattura().getId();
		Fattura fattura = fattureRepository.findById(idFattura);

		long importoToDelete = riga.getPrice() * riga.getQuantita();
		fattura.setImporto(fattura.getImporto() - importoToDelete);
		fattureRepository.save(fattura);
		return true;
	}

	@Transactional
	public RigheDetaglioReadDto updateOneById(long id, RigheDetaglioWriteDto rigaFatturaToUpdate) {
		if (!prodottoRepository.existsById(id)) {
			throw new FatturaNotFoundException("Fattura con id " + id + " non trovata");
		}

		RigaFattura prodotto = prodottoRepository.findById(id);

		prodotto.setName(rigaFatturaToUpdate.getName());
		prodotto.setPrice(rigaFatturaToUpdate.getPrice());
		prodotto.setQuantita(rigaFatturaToUpdate.getQuantita());
		prodotto.setDescription(rigaFatturaToUpdate.getDescription());

		RigaFattura updatedProdotto = prodottoRepository.save(prodotto);

		return modelMapper.map(updatedProdotto, RigheDetaglioReadDto.class);
	}
}
