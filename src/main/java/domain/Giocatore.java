package domain;

public class Giocatore {

	private String username;
	private final int idGiocatore;
	private final Pedina pedina;

	public Giocatore(String username, int idGiocatore, Pedina pedina) {
		this.username = username;
		this.idGiocatore = idGiocatore;
		this.pedina = pedina;
	}

	public String getUsername() {
		return username;
	}

	public int getIdGiocatore() {
		return idGiocatore;
	}

	public Pedina getPedina() {
		return pedina;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

        @Override
	public String toString() {
		return username;
	}

}