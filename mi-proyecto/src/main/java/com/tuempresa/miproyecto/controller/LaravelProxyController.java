package com.tuempresa.miproyecto.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.tuempresa.miproyecto.client.LaravelApiClient;

/**
 * Ejemplo: Spring llama a una ruta de Laravel y reenvía la respuesta.
 * Cambia "/api/clientes" por la ruta real de tu API Laravel.
 */
@RestController
@RequestMapping("/api/laravel")
public class LaravelProxyController {

	private final LaravelApiClient laravelApiClient;

	public LaravelProxyController(LaravelApiClient laravelApiClient) {
		this.laravelApiClient = laravelApiClient;
	}

	@GetMapping("/participantes")
	public String participantesDesdeLaravel(@RequestParam Long idOfertaAcademica) {
		return laravelApiClient.getParticipantes(idOfertaAcademica);
	}

}
