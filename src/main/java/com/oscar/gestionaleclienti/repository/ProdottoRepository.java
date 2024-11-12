package com.oscar.gestionaleclienti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscar.gestionaleclienti.model.RigaFattura;

@Repository
public interface ProdottoRepository extends JpaRepository<RigaFattura, Long> {

	RigaFattura findById(long id);

}
