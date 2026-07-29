package com.tuempresa.miproyecto.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tuempresa.miproyecto.entity.Cliente;
import com.tuempresa.miproyecto.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private final ClienteRepository clienteRepository;

	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
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

}
