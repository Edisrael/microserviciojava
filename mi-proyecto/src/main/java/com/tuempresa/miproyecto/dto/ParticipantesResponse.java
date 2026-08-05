package com.tuempresa.miproyecto.dto;

import java.util.List;
public record ParticipantesResponse(
    List<ParticipanteItem> participantes
) {}