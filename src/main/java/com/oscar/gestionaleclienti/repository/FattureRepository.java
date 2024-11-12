package com.oscar.gestionaleclienti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscar.gestionaleclienti.model.Fattura;
import java.util.Date;

@Repository
public interface FattureRepository extends JpaRepository<Fattura, Long> {

	Fattura findById(long id);

	List<Fattura> findByScadenzaBefore(Date currentDate);

	List<Fattura> findByImportoBetween(long minImporto, long maxImporto);
}
