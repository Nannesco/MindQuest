package domain;

import java.util.ArrayList;

public class Domanda {

	private final String testo;
	private final ArrayList<String> opzioni;
	private final char rispostaCorretta;
	private final String difficolta;
	private final String materia;

	public Domanda(String testo, ArrayList<String> opzioni, char rispostaCorretta, String difficolta, String materia){
		this.testo = testo;
		this.opzioni = opzioni;
		this.rispostaCorretta = rispostaCorretta;
		this.difficolta = difficolta;
		this.materia = materia;
	}

	public String getTesto() {
		return this.testo;
		
	}

	public ArrayList<String> getOpzioni() {
		return this.opzioni;
	}

	public String getMateria() {
		return this.materia;
	}

	public String getDifficolta() {
		return this.difficolta;
	}

	public char getRispostaCorretta() {
		return this.rispostaCorretta;
	}

}