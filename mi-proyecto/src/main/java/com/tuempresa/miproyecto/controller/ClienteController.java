package com.tuempresa.miproyecto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tuempresa.miproyecto.client.LaravelApiClient;
import com.tuempresa.miproyecto.dto.ClienteRequest;
import com.tuempresa.miproyecto.dto.ClienteResponse;
import com.tuempresa.miproyecto.dto.ParticipanteItem;
import com.tuempresa.miproyecto.dto.ParticipantesResponse;
import com.tuempresa.miproyecto.service.ClienteService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private final ClienteService clienteService;
	private final LaravelApiClient laravelApiClient;

	public ClienteController(ClienteService clienteService, LaravelApiClient laravelApiClient) {
		this.clienteService = clienteService;
		this.laravelApiClient = laravelApiClient;
	}

	@GetMapping
	public List<ClienteResponse> listar() {
		return clienteService.listar();
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody ClienteRequest request) {
		try {
			ClienteResponse creado = clienteService.crear(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(creado);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/activos")
	public List<ClienteResponse> listarActivos() {
		return clienteService.listarActivos();
	}

	@GetMapping("/buscar")
	public List<ClienteResponse> buscar(@RequestParam String q) {
		return clienteService.buscar(q);
	}

	@GetMapping("/por-rfc/{rfc}")
	public ResponseEntity<ClienteResponse> obtenerPorRfc(@PathVariable String rfc) {
		return clienteService.obtenerPorRfc(rfc)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponse> obtener(@PathVariable Long id) {
		return clienteService.obtenerPorId(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/{idOfertaAcademica}/participantes-oferta")
	public ParticipantesResponse participantesDeOferta(@PathVariable Integer idOfertaAcademica) {
		ParticipantesResponse participantes = laravelApiClient.getParticipantes(idOfertaAcademica);
		for (ParticipanteItem item : participantes.participantes()) {
			System.out.println(item.Participante());
		}
		return participantes;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> borrar(@PathVariable Long id) {
		try {
			clienteService.eliminar(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
