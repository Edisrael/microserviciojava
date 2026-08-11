package com.tuempresa.miproyecto.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tuempresa.miproyecto.entity.ProductoServicio;

public interface ProductoServicioRepository extends JpaRepository<ProductoServicio, Long> {

	Optional<ProductoServicio> findByClaveSat(String claveSat);

	List<ProductoServicio> findByDescripcionContainingIgnoreCase(String termino);

	boolean existsByClaveSat(String claveSat);

	long countByDescripcionContainingIgnoreCase(String termino);

}
