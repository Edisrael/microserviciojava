package com.tuempresa.miproyecto.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tuempresa.miproyecto.client.LaravelApiClient;
import com.tuempresa.miproyecto.entity.Cliente;
import com.tuempresa.miproyecto.dto.ParticipantesResponse;
import com.tuempresa.miproyecto.repository.ClienteRepository;
import com.tuempresa.miproyecto.dto.ParticipanteItem;
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private final ClienteRepository clienteRepository;
	private final LaravelApiClient laravelApiClient;

	public ClienteController(ClienteRepository clienteRepository, LaravelApiClient laravelApiClient) {
		this.clienteRepository = clienteRepository;
		this.laravelApiClient = laravelApiClient;
	}

	@GetMapping
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	@GetMapping("/activos")
	public List<Cliente> listarActivos() {
		return clienteRepository.findByEstado("ACTIVO");
	}

	@GetMapping("/buscar")
	public List<Cliente> buscar(@RequestParam String q) {
		return clienteRepository.findByRazonSocialContainingIgnoreCase(q);
	}

	@GetMapping("/por-rfc/{rfc}")
	public Cliente obtenerPorRfc(@PathVariable String rfc) {
		return clienteRepository.findByRfc(rfc).orElse(null);
	}

	@GetMapping("/{id}")
	public Cliente obtener(@PathVariable Long id) {
		return clienteRepository.findById(id).orElse(null);
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

}
