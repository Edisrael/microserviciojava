package com.tuempresa.miproyecto.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import com.tuempresa.miproyecto.dto.ParticipantesResponse;


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

	public <T> T get(String path, Class<T> responseType, Object... uriVars) {
		return restClient.get()
				.uri(path, uriVars)
				.retrieve()
				.body(responseType);
	}

	public String post(String path, Object body) {
		return restClient.post()
				.uri(path)
				.contentType(MediaType.APPLICATION_JSON)
				.body(body)
				.retrieve()
				.body(String.class);
	}

	public ParticipantesResponse getParticipantes(Integer idOfertaAcademica) {
		return get(
			"/api/cursos/getDataTableParticipants?idOfertaAcademica={idOfertaAcademica}",
			ParticipantesResponse.class,
			idOfertaAcademica
		);
	}

}
