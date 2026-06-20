package domain.dto;

import java.util.List;

public record InfoGiocatori(
    List<String> nomi, 
    List<Integer> id, 
    List<Integer> posizioni, 
    List<Integer> punti
){}
