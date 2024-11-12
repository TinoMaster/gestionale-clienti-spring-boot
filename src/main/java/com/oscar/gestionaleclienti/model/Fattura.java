package com.oscar.gestionaleclienti.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Fattura {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Basic(optional = false)
	private String numeroFattura;
	private long importo;
	private int iva;
	private Date scadenza;

	@OneToMany(mappedBy = "fattura", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RigaFattura> prodotti;

	@ManyToOne
	@JoinColumn(name = "cliente")
	private Cliente cliente;

}
