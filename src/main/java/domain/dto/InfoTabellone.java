package domain.dto;

import java.util.List;

public record InfoTabellone(
    int numeroCaselle, 
    List<Boolean> mappaEventi) {}

