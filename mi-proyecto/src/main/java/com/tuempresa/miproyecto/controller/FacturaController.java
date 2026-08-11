package com.tuempresa.miproyecto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuempresa.miproyecto.service.FacturaService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

	private final FacturaService facturaService;

	public FacturaController(FacturaService facturaService) {
		this.facturaService = facturaService;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> borrar(@PathVariable Long id) {
		try {
			facturaService.eliminar(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
