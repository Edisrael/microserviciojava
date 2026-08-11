package com.tuempresa.miproyecto.dto;

import java.time.LocalDateTime;

import com.tuempresa.miproyecto.entity.Cliente;

/**
 * DTO de salida: decide QUÉ campos ve el cliente de la API.
 * Así no exponemos la entidad JPA completa ni detalles internos de BD.
 */
public record ClienteResponse(
		Long id,
		String rfc,
		String razonSocial,
		String correoElectronico,
		String telefono,
		String estado,
		LocalDateTime creadoEn
) {

	public static ClienteResponse from(Cliente cliente) {
		return new ClienteResponse(
				cliente.getId(),
				cliente.getRfc(),
				cliente.getRazonSocial(),
				cliente.getCorreoElectronico(),
				cliente.getTelefono(),
				cliente.getEstado(),
				cliente.getCreadoEn()
		);
	}
}
