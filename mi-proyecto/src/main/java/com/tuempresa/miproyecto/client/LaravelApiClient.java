package com.tuempresa.miproyecto.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Cliente HTTP para llamar APIs Laravel.
 * Equivalente a Http::baseUrl(...)->withToken(...)->get(...) en Laravel.
 */
@Component
public class LaravelApiClient {

	private final RestClient restClient;

	public LaravelApiClient(
			@Value("${laravel.api.base-url}") String baseUrl,
			@Value("${laravel.api.token:}") String token) {

		RestClient.Builder builder = RestClient.builder()
				.baseUrl(baseUrl)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		if (!token.isBlank()) {
			builder.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		}

		this.restClient = builder.build();
	}

	public String get(String path) {
		return restClient.get()
				.uri(path)
				.retrieve()
				.body(String.class);
	}

	public String post(String path, Object body) {
		return restClient.post()
				.uri(path)
				.contentType(MediaType.APPLICATION_JSON)
				.body(body)
				.retrieve()
				.body(String.class);
	}

	public String getParticipantes(Long idOfertaAcademica) {
		return get("/api/cursos/getDataTableParticipants?idOfertaAcademica=" + idOfertaAcademica);
	}

}
