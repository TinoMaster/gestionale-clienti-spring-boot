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

import com.oscar.gestionaleclienti.dto.FattureReadDto;
import com.oscar.gestionaleclienti.dto.FattureWriteDto;
import com.oscar.gestionaleclienti.dto.RigheDetaglioReadDto;
import com.oscar.gestionaleclienti.dto.RigheDetaglioWriteDto;
import com.oscar.gestionaleclienti.service.RigaFatturaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prodotti")
@CrossOrigin(origins = "*")
public class RigaFatturaController {

	@Autowired
	private RigaFatturaService rigaFatturaService;

	@GetMapping
	public List<RigheDetaglioReadDto> getAll() {
		return rigaFatturaService.findAll();
	}

	@GetMapping("/{id}")
	public RigheDetaglioReadDto getOneById(@PathVariable long id) {
		return rigaFatturaService.findOneById(id);
	}

	@GetMapping("/delete/{id}")
	public boolean deleteById(@PathVariable long id) {
		return rigaFatturaService.deleteOne(id);
	}

	@PostMapping
	public RigheDetaglioReadDto save(@Validated @Valid @RequestBody RigheDetaglioWriteDto prodottoToSave) {
		return rigaFatturaService.save(prodottoToSave);
	}

	@PostMapping("/update/{id}")
	public RigheDetaglioReadDto updateOneById(@PathVariable long id,
			@Validated @Valid @RequestBody RigheDetaglioWriteDto newRigaFattura) {
		return rigaFatturaService.updateOneById(id, newRigaFattura);
	}
}
