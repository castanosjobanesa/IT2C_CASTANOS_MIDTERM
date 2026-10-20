package com.scc.model;

public record ScenarioEntity(
        int id,
        String clientName,
        String trainerName,
        double sessionFee,
        String status
) {}