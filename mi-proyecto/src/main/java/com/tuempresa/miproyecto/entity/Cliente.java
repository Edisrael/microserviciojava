package com.tuempresa.miproyecto.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 13)
	private String rfc;

	@Column(name = "razon_social", nullable = false, length = 200)
	private String razonSocial;

	@Column(name = "correo_electronico", length = 120)
	private String correoElectronico;

	@Column(length = 30)
	private String telefono;

	@Column(name = "codigo_postal_fiscal", length = 10)
	private String codigoPostalFiscal;

	@Column(name = "regimen_fiscal", length = 120)
	private String regimenFiscal;

	@Column(nullable = false, length = 20)
	private String estado;

	@Column(name = "creado_en", nullable = false, updatable = false)
	private LocalDateTime creadoEn;

	@Column(name = "actualizado_en", nullable = false)
	private LocalDateTime actualizadoEn;

}
