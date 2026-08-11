package com.tuempresa.miproyecto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuempresa.miproyecto.dto.ClienteRequest;
import com.tuempresa.miproyecto.dto.ClienteResponse;
import com.tuempresa.miproyecto.entity.Cliente;
import com.tuempresa.miproyecto.repository.ClienteRepository;
import com.tuempresa.miproyecto.repository.FacturaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;
	private final FacturaRepository facturaRepository;

	public ClienteService(ClienteRepository clienteRepository, FacturaRepository facturaRepository) {
		this.clienteRepository = clienteRepository;
		this.facturaRepository = facturaRepository;
	}

	public List<ClienteResponse> listar() {
		return clienteRepository.findAll().stream().map(ClienteResponse::from).toList();
	}

	public List<ClienteResponse> listarActivos() {
		return clienteRepository.findByEstado("ACTIVO").stream().map(ClienteResponse::from).toList();
	}

	public List<ClienteResponse> buscar(String termino) {
		return clienteRepository.findByRazonSocialContainingIgnoreCase(termino).stream()
				.map(ClienteResponse::from)
				.toList();
	}

	public Optional<ClienteResponse> obtenerPorId(Long id) {
		return clienteRepository.findById(id).map(ClienteResponse::from);
	}

	public Optional<ClienteResponse> obtenerPorRfc(String rfc) {
		return clienteRepository.findByRfc(rfc).map(ClienteResponse::from);
	}

	@Transactional
	public ClienteResponse crear(ClienteRequest request) {
		if (request.rfc() == null || request.rfc().isBlank()) {
			throw new IllegalArgumentException("El RFC es obligatorio");
		}
		if (request.razonSocial() == null || request.razonSocial().isBlank()) {
			throw new IllegalArgumentException("La razón social es obligatoria");
		}
		if (clienteRepository.existsByRfc(request.rfc())) {
			throw new IllegalArgumentException("Ya existe un cliente con ese RFC");
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

		return ClienteResponse.from(clienteRepository.save(cliente));
	}

	@Transactional
	public void eliminar(Long id) {
		if (!clienteRepository.existsById(id)) {
			throw new EntityNotFoundException("Cliente no encontrado: " + id);
		}
		if (facturaRepository.existsByClienteId(id)) {
			// Conflicto de negocio: el recurso existe, pero no se puede borrar
			throw new IllegalStateException("No se puede eliminar el cliente porque tiene facturas asociadas");
		}
		clienteRepository.deleteById(id);
	}
}
