package com.oscar.gestionaleclienti.dto;

import lombok.Data;

@Data
public class RigheDetaglioReadDto {

	private long id;
	private String name;
	private long price;
	private int quantita;
	private String description;

	private long fattura;

}
