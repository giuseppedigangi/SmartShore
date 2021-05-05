package it.unipa.community.digangi.giuseppe.model;

public class Prodotto {
	private int IDProdotto;
	private String Nome;
	private String Categoria;
	private float Prezzo;
	
	public Prodotto() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Prodotto(int iDProdotto, String nome, float prezzo) {
		super();
		IDProdotto = iDProdotto;
		Nome = nome;
		Prezzo = prezzo;
	}


	public int getIDProdotto() {
		return IDProdotto;
	}

	public void setIDProdotto(int iDProdotto) {
		IDProdotto = iDProdotto;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getCategoria() {
		return Categoria;
	}

	public void setCategoria(String categoria) {
		Categoria = categoria;
	}

	public float getPrezzo() {
		return Prezzo;
	}

	public void setPrezzo(float prezzo) {
		Prezzo = prezzo;
	}

	
	
	

}
