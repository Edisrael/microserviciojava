package com.tuempresa.miproyecto.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tuempresa.miproyecto.client.LaravelApiClient;
import com.tuempresa.miproyecto.dto.ClienteRequest;
import com.tuempresa.miproyecto.dto.ClienteResponse;
import com.tuempresa.miproyecto.entity.Cliente;
import com.tuempresa.miproyecto.dto.ParticipantesResponse;
import com.tuempresa.miproyecto.repository.ClienteRepository;
import com.tuempresa.miproyecto.dto.ParticipanteItem;
import com.tuempresa.miproyecto.repository.FacturaRepository;
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private final ClienteRepository clienteRepository;
	private final LaravelApiClient laravelApiClient;
	private final FacturaRepository facturaRepository;

	public ClienteController(ClienteRepository clienteRepository, LaravelApiClient laravelApiClient, FacturaRepository facturaRepository) {
		this.clienteRepository = clienteRepository;
		this.laravelApiClient = laravelApiClient;
		this.facturaRepository = facturaRepository;
	}

	@GetMapping
	public List<ClienteResponse> listar() {
		return clienteRepository.findAll().stream().map(ClienteResponse::from).toList();
	}

	/**
	 * POST /api/clientes
	 * Entrada: ClienteRequest (DTO).
	 * Salida: ClienteResponse (DTO) — no devolvemos la entidad JPA.
	 */
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody ClienteRequest request) {
		if (request.rfc() == null || request.rfc().isBlank()) {
			return ResponseEntity.badRequest().body("El RFC es obligatorio");
		}
		if (request.razonSocial() == null || request.razonSocial().isBlank()) {
			return ResponseEntity.badRequest().body("La razón social es obligatoria");
		}
		if (clienteRepository.existsByRfc(request.rfc())) {
			return ResponseEntity.badRequest().body("Ya existe un cliente con ese RFC");
		}

		Cliente cliente = new Cliente();
		cliente.setRfc(request.rfc());
		cliente.setRazonSocial(request.razonSocial());
		cliente.setCorreoElectronico(request.correoElectronico());
		cliente.setTelefono(request.telefono());
		cliente.setCodigoPostalFiscal(request.codigoPostalFiscal());
		cliente.setRegimenFiscal(request.regimenFiscal());
		cliente.setEstado(request.estado() == null || request.estado().isBlank() ? "ACTIVO" : request.estado());

		LocalDateTime ahora = LocalDateTime.now();
		cliente.setCreadoEn(ahora);
		cliente.setActualizadoEn(ahora);

		Cliente guardado = clienteRepository.save(cliente);
		// Aquí está el cambio: body(ClienteResponse), no body(Cliente)
		return ResponseEntity.status(HttpStatus.CREATED).body(ClienteResponse.from(guardado));
	}

	@GetMapping("/activos")
	public List<ClienteResponse> listarActivos() {
		return clienteRepository.findByEstado("ACTIVO").stream().map(ClienteResponse::from).toList();
	}

	@GetMapping("/buscar")
	public List<ClienteResponse> buscar(@RequestParam String q) {
		return clienteRepository.findByRazonSocialContainingIgnoreCase(q).stream()
				.map(ClienteResponse::from)
				.toList();
	}

	@GetMapping("/por-rfc/{rfc}")
	public ResponseEntity<ClienteResponse> obtenerPorRfc(@PathVariable String rfc) {
		return clienteRepository.findByRfc(rfc)
				.map(ClienteResponse::from)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponse> obtener(@PathVariable Long id) {
		return clienteRepository.findById(id)
				.map(ClienteResponse::from)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/{idOfertaAcademica}/participantes-oferta")
    public ParticipantesResponse participantesDeOferta(@PathVariable Integer idOfertaAcademica) {
     	Integer ofertaAcademicaId = idOfertaAcademica;
		ParticipantesResponse participantes = laravelApiClient.getParticipantes(ofertaAcademicaId);
		for (ParticipanteItem item : participantes.participantes()) {
			System.out.println(item.Participante());
		}
        return participantes;
		
    }



	@DeleteMapping("/{id}")
	public ResponseEntity<?> borrar(@PathVariable Long id) {
		if (!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		if (facturaRepository.existsByClienteId(id)) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("No se puede eliminar el cliente porque tiene facturas asociadas");
		}
		clienteRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
