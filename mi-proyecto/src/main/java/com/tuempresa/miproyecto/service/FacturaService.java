package com.tuempresa.miproyecto.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuempresa.miproyecto.repository.FacturaConceptoRepository;
import com.tuempresa.miproyecto.repository.FacturaRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Capa de negocio de Facturas.
 * Aquí vive la lógica; el Controller solo habla HTTP.
 */
@Service
public class FacturaService {

	private final FacturaRepository facturaRepository;
	private final FacturaConceptoRepository facturaConceptoRepository;

	public FacturaService(
			FacturaRepository facturaRepository,
			FacturaConceptoRepository facturaConceptoRepository) {
		this.facturaRepository = facturaRepository;
		this.facturaConceptoRepository = facturaConceptoRepository;
	}

	/**
	 * Elimina factura + conceptos.
	 * No toca ProductoServicio (catálogo).
	 */
	@Transactional
	public void eliminar(Long id) {
		if (!facturaRepository.existsById(id)) {
			throw new EntityNotFoundException("Factura no encontrada: " + id);
		}
		facturaConceptoRepository.deleteByFacturaId(id);
		facturaRepository.deleteById(id);
	}
}
