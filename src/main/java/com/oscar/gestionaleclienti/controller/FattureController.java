package com.oscar.gestionaleclienti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oscar.gestionaleclienti.dto.FattureReadDto;
import com.oscar.gestionaleclienti.dto.FattureWriteDto;
import com.oscar.gestionaleclienti.service.FattureService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fatture")
@CrossOrigin(origins = "*")
public class FattureController {

	@Autowired
	private FattureService fattureService;

	@GetMapping
	public List<FattureReadDto> findAll(@RequestParam(required = false, defaultValue = "false") boolean scadute,
			@RequestParam(required = false, defaultValue = "0") long minimporto,
			@RequestParam(required = false, defaultValue = "1000000") long maximporto) {
		return fattureService.findAll(scadute, minimporto, maximporto);
	}

	@GetMapping("/{id}")
	public FattureReadDto findOneById(@PathVariable long id) {
		return fattureService.getOneById(id);
	}

	@GetMapping("/delete/{id}")
	public boolean deleteById(@PathVariable long id) {
		return fattureService.deleteOne(id);
	}

	@PostMapping
	public FattureReadDto save(@Validated @Valid @RequestBody FattureWriteDto newFatture) {
		System.out.println(newFatture);
		return fattureService.save(newFatture);
	}

	@PostMapping("/update/{id}")
	public FattureReadDto updateFattura(@PathVariable long id,
			@Validated @Valid @RequestBody FattureWriteDto newFattura) {
		return fattureService.updateOneById(id, newFattura);
	}

	@PostMapping("/savelist")
	public List<FattureReadDto> saveList(@Validated @RequestBody List<FattureWriteDto> listOfFatture) {
		return fattureService.saveListOfFatture(listOfFatture);
	}

	@DeleteMapping("/{id}")
	public void deleteOne(@PathVariable long id) {
		fattureService.deleteOne(id);
	}
}
