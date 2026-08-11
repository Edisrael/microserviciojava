package com.tuempresa.miproyecto.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "facturas")
@Getter
@Setter
@NoArgsConstructor
public class Factura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "actualizado_en", nullable = false)
	private LocalDateTime actualizadoEn;

	@Column(name = "creado_en", nullable = false, updatable = false)
	private LocalDateTime creadoEn;

	@Column(nullable = false, length = 20)
	private String estatus;

	// En BD es tipo DATE, no TIMESTAMP → LocalDate (no LocalDateTime)
	@Column(nullable = false)
	private LocalDate fecha;
	
	@Column(nullable = false)
	private Integer folio;

	// En BD es NUMERIC → BigDecimal (no Double; Double mapea a FLOAT)
	@Column(nullable = false)
	private BigDecimal iva;

	@Column(length = 1000)
	private String observaciones;

	@Column(nullable = false, length = 10)
	private String serie;

	@Column(nullable = false)
	private BigDecimal subtotal;

	@Column(nullable = false)
	private BigDecimal total;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

}
