package com.oscar.gestionaleclienti.dto;

import lombok.Data;

@Data
public class RigheDetaglioWriteDto {

	private String name;
	private long price;
	private int quantita;
	private String description;

	private long fattura;

}
