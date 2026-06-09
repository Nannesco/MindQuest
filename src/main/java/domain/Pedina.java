package domain;

public abstract class Pedina {

	private int puntiConoscenza;
	private String personaggio;
	private Casella casellaCorrente;
	private Dado dado;

	public Pedina(Casella casellaCorrente, int puntiConoscenza, Dado dado) {
		this.casellaCorrente = casellaCorrente;
		this.puntiConoscenza = puntiConoscenza;
		this.dado = dado;
	}

	public void muovi(int distanza, Tabellone tabellone) {
		int posizioneAttuale = this.casellaCorrente.getNumeroCasella();
        this.casellaCorrente = tabellone.calcolaNuovaPosizione(posizioneAttuale, distanza);		
	}

	public void aggiornaPosizione(Casella nuovaPosizione) {
		this.casellaCorrente = nuovaPosizione;
	}

	public void aggiungiPuntiConoscenza(int punti) {
		this.puntiConoscenza += punti;
	}

	public Dado getDado() {
		return dado;
	}

	public void setDado(Dado nuovoDado) {
		this.dado = nuovoDado;

	}

	public void rimuoviPuntiConoscenza(int punti) {
        this.puntiConoscenza -= punti;
        if (this.puntiConoscenza < 0) {
            this.puntiConoscenza = 0;
        }
    }

	public void raddoppiaPuntiConoscenza() {
		this.puntiConoscenza *= 2;
	}

	public int getPuntiConoscenza() {
    	return this.puntiConoscenza;
    }

    public String getPersonaggio() {
        return personaggio;
    }

    public Casella getCasellaCorrente() {
        return casellaCorrente;
    }
	
	public String onDadoLanciato(int passi) {
        return ""; 
    }

    public String onRispostaCorretta(Domanda domanda) {
        return ""; 
    }

    public String onRispostaErrata(Domanda domanda) {
        return ""; 
    }

        @Override
	public abstract String toString();


	


}