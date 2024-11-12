package com.oscar.gestionaleclienti.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "prodotti")
@Data
public class RigaFattura {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Basic(optional = false)
	private String name;
	private long price;
	private int quantita;
	private String description;

	@ManyToOne
	@JoinColumn(name = "fattura")
	private Fattura fattura;

}
