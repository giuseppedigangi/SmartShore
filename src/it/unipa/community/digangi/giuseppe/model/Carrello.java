package it.unipa.community.digangi.giuseppe.model;


import java.util.Map;
import java.util.LinkedHashMap;

public class Carrello {

	
	private Map<Prodotto, Integer> prodotti; 
	

	public Carrello() {
		this.prodotti = new LinkedHashMap<Prodotto, Integer>();
		
	}
	
	
	public Carrello(Map<Prodotto, Integer> prodotti) {
		this.prodotti = prodotti;
	}
	

	public Map<Prodotto, Integer> getProdotti() {
		return prodotti;
	}
	
	
	
	public void aggiungiProd(Prodotto prodotto, int quantità) {
			prodotti.put(prodotto, quantità);
	}
	



	public void rimuoviProd(int id) {
		Prodotto prod = null;
		for (Prodotto p : prodotti.keySet()) {
        	if(p.getIDProdotto() == id)
        		prod=p;
        		
      
        	}
		prodotti.remove(prod);	
	}
	
	public void eliminaCarrello() {
		prodotti.clear();
	}


}
