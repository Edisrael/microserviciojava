package com.tuempresa.miproyecto.repository;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tuempresa.miproyecto.entity.FacturaConcepto;

public interface FacturaConceptoRepository extends JpaRepository<FacturaConcepto, Long> {

	Optional<FacturaConcepto> findByDescripcion(String descripcion);

	/** Una factura tiene MUCHOS conceptos */
	List<FacturaConcepto> findByFacturaId(Long facturaId);

	/**
	 * Un solo DELETE ... WHERE factura_id = ?
	 * (mejor que deleteAll(lista), que borra 1 por 1)
	 */
	void deleteByFacturaId(Long facturaId);

	List<FacturaConcepto> findByCantidad(BigDecimal cantidad);

	boolean existsByDescripcion(String descripcion);

	long countByCantidad(BigDecimal cantidad);

}
