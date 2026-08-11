package com.tuempresa.miproyecto.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tuempresa.miproyecto.repository.FacturaConceptoRepository;
import com.tuempresa.miproyecto.repository.FacturaRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Lección 1 de testing: test UNITARIO del Service.
 *
 * - NO levantamos Spring ni PostgreSQL.
 * - Los repositories son FALSOS (mocks): nosotros decidimos qué responden.
 * - Solo verificamos la LÓGICA de FacturaService.
 */
@ExtendWith(MockitoExtension.class)
class FacturaServiceTest {

	@Mock
	FacturaRepository facturaRepository;

	@Mock
	FacturaConceptoRepository facturaConceptoRepository;

	@InjectMocks
	FacturaService facturaService; // Spring no lo crea: Mockito inyecta los @Mock en el constructor

	@Test
	void eliminar_cuandoExiste_borraConceptosYFactura() {
		// 1) ARRANGE (preparar): fingimos que la factura 5 SÍ existe
		Long id = 5L;
		when(facturaRepository.existsById(id)).thenReturn(true);

		// 2) ACT (actuar): llamamos el método real del service
		facturaService.eliminar(id);

		// 3) ASSERT (verificar lo esperado):
		//    - debió borrar conceptos de esa factura
		//    - debió borrar la factura
		verify(facturaConceptoRepository).deleteByFacturaId(id);
		verify(facturaRepository).deleteById(id);
	}

	@Test
	void eliminar_cuandoNoExiste_lanzaEntityNotFound() {
		// ARRANGE: la factura 99 NO existe
		Long id = 99L;
		when(facturaRepository.existsById(id)).thenReturn(false);

		// ACT + ASSERT: esperamos que lance esta excepción
		assertThrows(EntityNotFoundException.class, () -> facturaService.eliminar(id));

		// Y además: NO debió borrar nada
		verify(facturaConceptoRepository, never()).deleteByFacturaId(id);
		verify(facturaRepository, never()).deleteById(id);
	}
}
