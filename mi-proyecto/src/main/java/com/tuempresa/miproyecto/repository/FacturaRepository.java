package com.tuempresa.miproyecto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuempresa.miproyecto.entity.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

	// findById ya lo trae JpaRepository — no hace falta redefinirlo

	Optional<Factura> findByFolio(Integer folio);

	List<Factura> findBySerie(String serie);

	/** Un cliente puede tener MUCHAS facturas → List, no Optional */
	List<Factura> findByClienteId(Long id);

	boolean existsByClienteId(Long id);

	List<Factura> findBySerieOrderByFolioAsc(String serie);

	List<Factura> findByObservacionesContainingIgnoreCase(String termino);

	boolean existsByFolio(Integer folio);

	long countBySerie(String serie);

}
