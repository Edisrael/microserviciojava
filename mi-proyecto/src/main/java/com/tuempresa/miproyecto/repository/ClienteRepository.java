package com.tuempresa.miproyecto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuempresa.miproyecto.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	// findBy + nombre del campo en Java (camelCase, no el de la BD)
	Optional<Cliente> findByRfc(String rfc);

	List<Cliente> findByEstado(String estado);

	List<Cliente> findByEstadoOrderByRazonSocialAsc(String estado);

	List<Cliente> findByRazonSocialContainingIgnoreCase(String termino);

	boolean existsByRfc(String rfc);

	long countByEstado(String estado);

}
