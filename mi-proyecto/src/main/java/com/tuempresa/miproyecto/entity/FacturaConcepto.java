package com.tuempresa.miproyecto.entity;

import java.math.BigDecimal;
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
@Table(name = "facturas_conceptos")
@Getter
@Setter
@NoArgsConstructor
public class FacturaConcepto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column( nullable = false)
	private BigDecimal cantidad;

	@Column(nullable = false)
	private String descripcion;

	@Column(nullable = false)
	private BigDecimal importe;

	@Column(nullable = false)
	private BigDecimal iva;

	@Column(nullable = false, name = "precio_unitario")
	private BigDecimal precioUnitario;

	@Column(nullable = false)
	private BigDecimal total;

	@ManyToOne
	@JoinColumn(name = "factura_id", nullable = false)
	private Factura factura;

	@ManyToOne
	@JoinColumn(name = "producto_servicio_id", nullable = false)
	private ProductoServicio productoServicio;

}
