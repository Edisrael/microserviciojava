package com.tuempresa.miproyecto.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tuempresa.miproyecto.service.FacturaService;

import jakarta.persistence.EntityNotFoundException;

/**
 * Lección 2: test del CONTROLLER (capa HTTP).
 *
 * MockMvc = Postman automático.
 * No levantamos Tomcat ni PostgreSQL.
 * El FacturaService es mock: solo nos importa cómo el controller traduce a status HTTP.
 */
@ExtendWith(MockitoExtension.class)
class FacturaControllerTest {

	@Mock
	FacturaService facturaService;

	@InjectMocks
	FacturaController facturaController;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		// Armamos un "servidor de mentira" solo con este controller
		mockMvc = MockMvcBuilders.standaloneSetup(facturaController).build();
	}

	@Test
	void borrar_cuandoExiste_responde204() throws Exception {
		// ARRANGE: el service no lanza error (borrado OK)
		doNothing().when(facturaService).eliminar(5L);

		// ACT: simulamos DELETE /api/facturas/5
		// ASSERT: esperamos HTTP 204 No Content
		mockMvc.perform(delete("/api/facturas/5"))
				.andExpect(status().isNoContent());
	}

	@Test
	void borrar_cuandoNoExiste_responde404() throws Exception {
		// ARRANGE: el service dice "no encontrado"
		doThrow(new EntityNotFoundException("Factura no encontrada: 99"))
				.when(facturaService).eliminar(99L);

		// ACT + ASSERT: DELETE /api/facturas/99 → 404
		mockMvc.perform(delete("/api/facturas/99"))
				.andExpect(status().isNotFound());
	}
}
