package com.oscar.gestionaleclienti.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class FattureReadDto {

	private long id;
	private String numeroFattura;
	private long importo;
	private int iva;
	private Date scadenza;

	private List<RigheDetaglioReadDto> prodotti;
	private long cliente;

}
