package com.tuempresa.miproyecto.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuempresa.miproyecto.dto.UsuarioRequest;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@PostMapping
	public String recibir(@RequestBody UsuarioRequest request) {

		return "idUsuario recibido: " + request.idUsuario();
	}

}
