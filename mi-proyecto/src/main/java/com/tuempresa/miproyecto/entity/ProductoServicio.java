package com.tuempresa.miproyecto.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productos_servicios")
@Getter
@Setter
@NoArgsConstructor
public class ProductoServicio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "actualizado_en", nullable = false)
	private LocalDateTime actualizadoEn;

	@Column(name = "creado_en", nullable = false)
	private LocalDateTime creadoEn;

	@Column(name = "clave_sat", nullable = false)
	private String claveSat;

	@Column(nullable = false)
	private String descripcion;

	@Column(nullable = false)
	private String estado;

	// BD: NUMERIC → BigDecimal (Double mapea a FLOAT y falla el validate)
	@Column(nullable = false)
	private BigDecimal iva;

	@Column(name = "precio_unitario", nullable = false)
	private BigDecimal precioUnitario;

	@Column(name = "unidad_sat", nullable = false)
	private String unidadSat;

	@OneToMany(mappedBy = "productoServicio")
	private List<FacturaConcepto> facturasConceptos;

}
