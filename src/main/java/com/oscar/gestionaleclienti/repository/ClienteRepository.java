package com.oscar.gestionaleclienti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oscar.gestionaleclienti.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Cliente findById(long id);

	List<Cliente> findByEmailContaining(String str);

	@Query(value = "SELECT * FROM CLIENTE WHERE LOWER(NOME) LIKE LOWER(CONCAT('%', :cl, '%')) OR LOWER(COGNOME) LIKE LOWER(CONCAT('%', :cl, '%'))", nativeQuery = true)
	List<Cliente> findByNomeOrCognome(@Param("cl") String cl);
}
