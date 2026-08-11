package com.tuempresa.miproyecto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuempresa.miproyecto.repository.FacturaRepository;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

	private final FacturaRepository facturaRepository;

	public FacturaController(FacturaRepository facturaRepository) {
		this.facturaRepository = facturaRepository;
	}

	/**
	 * DELETE /api/facturas/{id}
	 * Tu lógica estaba bien; el ajuste es el recurso (facturas, no clientes).
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> borrar(@PathVariable Long id) {
		if (!facturaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		facturaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
