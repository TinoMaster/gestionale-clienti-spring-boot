package com.oscar.gestionaleclienti.dto;

import java.util.List;

public class ClienteReadDto {

	private long id;
	private String nome;
	private String cognome;
	private String email;
	private List<FattureReadDto> fatture;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<FattureReadDto> getFatture() {
		return fatture;
	}

	public void setFatture(List<FattureReadDto> fatture) {
		this.fatture = fatture;
	}

}
