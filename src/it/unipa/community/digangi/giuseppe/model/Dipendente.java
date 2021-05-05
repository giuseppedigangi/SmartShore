package it.unipa.community.digangi.giuseppe.model;

public class Dipendente {


	private int id;
	private String Email;
	private String Nome;
	private String Cognome;
	private String Password;
	private String Sesso;
	private String Ruolo;
	/*
	 * public enum Ruolo{ Amministratore, Bagnino, Barista, Cassiere, Controllore; }
	 * public Ruolo ruolo;
	 */
	
	
	
	public Dipendente(int id, String email, String nome, String cognome, String password, String sesso) {
		super();
		this.id = id;
		Email = email;
		Nome = nome;
		Cognome = cognome;
		Password = password;
		Sesso = sesso;
	}
	public Dipendente(String email, String nome, String cognome) {
		super();
		Email = email;
		Nome = nome;
		Cognome = cognome;
	}

	public Dipendente() {
		super();
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String Email) {
		this.Email = Email;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		this.Nome = nome;
	}

	public String getCognome() {
		return Cognome;
	}

	public void setCognome(String cognome) {
		this.Cognome = cognome;
	}

	public String getSesso() {
		return Sesso;
	}

	public void setSesso(String sesso) {
		this.Sesso = sesso;
	}



	/*
	 * public void setRuolo(Ruolo ruolo) { this.ruolo = ruolo; }
	 */
	
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public Dipendente(String email, String password) {
		// TODO Auto-generated constructor stub
	}
	public void setRuolo(String ruolo) {
		Ruolo = ruolo;
	}
	public String getRuolo() {
		return Ruolo;
	}

	
	/*
	 * public String convertToDatabaseColumn(Ruolo ruolo) { if (ruolo == null) {
	 * return null; } return ruolo.toString(); }
	 * 
	 * 
	 * public Ruolo convertToEntityAttribute(String string) { if (string == null) {
	 * return null; } try { return Ruolo.valueOf(string); } catch
	 * (IllegalArgumentException e) { return null; } }
	 */
	   
}
