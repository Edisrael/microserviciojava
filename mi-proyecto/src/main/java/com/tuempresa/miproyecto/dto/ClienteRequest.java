package com.tuempresa.miproyecto.dto;

/**
 * DTO = Data Transfer Object.
 * Es lo que llega en el JSON del POST. No es la entidad de BD.
 * El cliente NO manda id ni fechas: eso lo genera el servidor.
 */
public record ClienteRequest(
		String rfc,
		String razonSocial,
		String correoElectronico,
		String telefono,
		String codigoPostalFiscal,
		String regimenFiscal,
		String estado
) {
}
