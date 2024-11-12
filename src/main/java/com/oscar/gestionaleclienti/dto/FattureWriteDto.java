package com.oscar.gestionaleclienti.dto;

import java.util.List;

import lombok.Data;

@Data
public class FattureWriteDto {

	private String numeroFattura;
	private int iva;
	private String scadenza;
	private List<RigheDetaglioWriteDto> prodotti;
	private long cliente;

}
