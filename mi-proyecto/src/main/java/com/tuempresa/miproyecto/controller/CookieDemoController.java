package com.tuempresa.miproyecto.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ejercicio didáctico de cookies.
 *
 * Flujo:
 * 1) POST crea la cookie (Set-Cookie en la respuesta)
 * 2) El browser la guarda
 * 3) GET la recibe sola (header Cookie) y el servidor la lee
 * 4) DELETE la borra (Max-Age=0)
 */
@RestController
@RequestMapping("/api/demo/cookie")
public class CookieDemoController {

	private static final String COOKIE_NAME = "demo_usuario";

	public record NombreRequest(String nombre) {
	}

	/** Crea / actualiza la cookie */
	@PostMapping
	public ResponseEntity<Map<String, Object>> crear(@RequestBody NombreRequest body) {
		if (body == null || body.nombre() == null || body.nombre().isBlank()) {
			return ResponseEntity.badRequest().body(Map.of(
					"ok", false,
					"mensaje", "El nombre es obligatorio"));
		}

		String nombre = body.nombre().trim();

		ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, nombre)
				.httpOnly(true) // JS del browser NO puede leerla (más seguro)
				.secure(false) // en local usamos http; en prod iría true (https)
				.sameSite("Lax")
				.path("/")
				.maxAge(Duration.ofHours(1))
				.build();

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(Map.of(
						"ok", true,
						"mensaje", "Cookie creada para: " + nombre,
						"cookie", COOKIE_NAME,
						"hint", "HttpOnly=true → no la verás en document.cookie; el server sí la lee"));
	}

	/** Lee la cookie que el browser reenvió solo */
	@GetMapping
	public ResponseEntity<Map<String, Object>> leer(
			@CookieValue(name = COOKIE_NAME, required = false) String nombre) {

		if (nombre == null || nombre.isBlank()) {
			return ResponseEntity.ok(Map.of(
					"ok", true,
					"tieneCookie", false,
					"mensaje", "No hay cookie. Primero haz POST con un nombre."));
		}

		return ResponseEntity.ok(Map.of(
				"ok", true,
				"tieneCookie", true,
				"nombre", nombre,
				"mensaje", "Hola de nuevo, " + nombre + " (te reconocí por la cookie)"));
	}

	/** Borra la cookie */
	@DeleteMapping
	public ResponseEntity<Map<String, Object>> borrar() {
		ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, "")
				.httpOnly(true)
				.secure(false)
				.sameSite("Lax")
				.path("/")
				.maxAge(0) // expirar ya = borrar
				.build();

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(Map.of(
						"ok", true,
						"mensaje", "Cookie eliminada"));
	}
}
