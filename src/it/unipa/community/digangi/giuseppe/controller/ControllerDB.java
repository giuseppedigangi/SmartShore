package it.unipa.community.digangi.giuseppe.controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.json.JSONArray;
import org.json.JSONObject;

import it.unipa.community.digangi.giuseppe.controller.*;
import it.unipa.community.digangi.giuseppe.model.Dipendente;
import it.unipa.community.digangi.giuseppe.model.Utente;




public class ControllerDB {
	
	private static ConnectionDB cdb = new ConnectionDB();
	private static PreparedStatement prst;
	
	

	public static Dipendente loginDipendente(String email, String password) throws NamingException, SQLException {
		Connection conn=cdb.startConnection();
		Dipendente dip=null;
		String query = "SELECT * FROM dipendente WHERE EmailDipendente=? AND PasswordDipendente=?";
		
		prst = conn.prepareStatement(query);
		prst.setString(1, email);
		prst.setString(2, password);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		if (rs.next()) {
			dip=new Dipendente();
			dip.setId(rs.getInt("IDDipendente"));
			dip.setNome(rs.getString("NomeDipendente"));
			dip.setCognome(rs.getString("CognomeDipendente"));
			dip.setRuolo(rs.getString("Ruolo"));
			
			conn.close();
			return dip;
		}
			conn.close();
			return dip;
	}
	public static Utente loginUtente(String email, String password) throws SQLException, NamingException {
		Connection conn=cdb.startConnection();
		
		Utente ut=null;
		String query="Select * FROM utente WHERE EmailUtente=? AND PasswordUtente=?";
		prst=conn.prepareStatement(query);
		prst.setString(1, email);
		prst.setString(2, password);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		if (rs.next()) {
			ut=new Utente();
			ut.setId(rs.getInt("IDUtente"));
			ut.setNome(rs.getString("NomeUtente"));
			ut.setCognome(rs.getString("CognomeUtente"));
			
			conn.close();
			
			return ut;
			
		}
			conn.close();
			return ut;
				
	}
	
	public static int registrazioneUtente(String nome, String cognome, String email, String password,String sesso) throws NamingException, SQLException {
		Connection conn=cdb.startConnection();
		int flag;
	
		String que="Select * FROM utente WHERE EmailUtente=?";
		prst=conn.prepareStatement(que);
		prst.setString(1, email);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		if (rs.next()) {
			/*Se esiste già un utente con quella mail imposto il flag a zero e lo ritorno*/
			flag=0;
			
		}else {
			String query="INSERT INTO utente(NomeUtente,CognomeUtente,EMailUtente,PasswordUtente,Sesso) VALUES(?,?,?,?,?)";
			prst=conn.prepareStatement(query);
			prst.setString(1, nome);
			prst.setString(2, cognome);
			prst.setString(3, email);
			prst.setString(4, password);
			prst.setString(5, sesso);
			prst.executeUpdate();
			flag=1;
		}
		conn.close();
		return flag;
		
	}
	
	
	/*Innanzitutto controllo se la mail fornita alla biglietteria dall'utente esiste già
	 * Nel caso in cui non è presente posso procedere con la registrazione temporanea
	 * */
	public static int verificaEmailDuplicata(String email) throws NamingException, SQLException {
	int flag;
	Connection conn=cdb.startConnection();
	String que="Select * FROM utente WHERE EmailUtente=?";
	prst=conn.prepareStatement(que);
	prst.setString(1, email);
	prst.executeQuery();
	ResultSet rs=prst.getResultSet();
	if (rs.next()) {
		/*Se esiste già un utente con quella mail imposto il flag a zero e lo ritorno*/
		flag=0;
	}
	else {
		flag=1;
	}
	conn.close();
	return flag;
	
	}
/**/	
	public static JSONArray displayPrenotazioni () throws Exception {
		JSONArray json =null;
		Connection conn=cdb.startConnection();
		
		String query="SELECT * FROM prenotazione";
		prst=conn.prepareStatement(query);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		
			json=new JSONArray();
			json=MyUtils.convertToJSONArray(rs);
			cdb.closeConnection();
		return json;

		
	}
	
/*Mi faccio ritornare la lista dei dipendenti per ostrarli all'amministratore*/	
	public static List<Dipendente> displayPersonale() throws Exception {
		List <Dipendente> dipendenti =new ArrayList<Dipendente>();
		Connection conn=cdb.startConnection();
		
		String query="SELECT * FROM dipendente";
		prst=conn.prepareStatement(query);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		while(rs.next()) {
			 Dipendente dip = new Dipendente();
			 		dip.setId(rs.getInt("IDDipendente"));
			 		dip.setNome(rs.getString("NomeDipendente"));
					dip.setCognome(rs.getString("CognomeDipendente"));
					dip.setSesso(rs.getString("Sesso"));
					dip.setEmail(rs.getString("EmailDipendente"));
					dip.setRuolo(rs.getString("Ruolo"));
				 
					 dipendenti.add(dip);
			
		}	
		conn.close();
		return dipendenti;
	}
	
	/*Mi faccio ritornare tutti i posti per stamparli a schermo*/
	public static JSONArray getPosti() throws Exception {
		JSONArray posti =null;
		Connection conn=cdb.startConnection();
		
		String query="SELECT * FROM posto";
		prst=conn.prepareStatement(query);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		
		posti=new JSONArray();
		posti=MyUtils.convertToJSONArray(rs);
		
		conn.close();
		return posti;
		
	}

	
	/*Mi faccio ritornare le postazioni prenotate, a seconda della data e del turno selezionate dall'utente*/
	public static JSONArray richiediPostazioniPrenotate(String data, String fasciaOraria) throws Exception {
		
		JSONArray postiOccupati=null;
	
		Connection conn=cdb.startConnection();
		
		if(Integer.parseInt(fasciaOraria)!=3) {
		String query="Select * FROM prenotazione WHERE (Turno=? OR Turno=3) AND DataPrenotazione=?";
		prst=conn.prepareStatement(query);
		prst.setString(1, fasciaOraria);
		prst.setString(2, data);
		
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		if(rs!=null) {
		
		postiOccupati = MyUtils.convertToJSONArray(rs);
			}
		conn.close();
		}else {
			String query="Select * FROM prenotazione WHERE (Turno=? OR (Turno=1 OR Turno=2)) AND DataPrenotazione=?";
			prst=conn.prepareStatement(query);
			prst.setString(1, fasciaOraria);
			prst.setString(2, data);
			
			prst.executeQuery();
			ResultSet rs=prst.getResultSet();
			if(rs!=null) {
			postiOccupati = MyUtils.convertToJSONArray(rs);
			}
			conn.close();
		}
		
		return postiOccupati;
	}
	/*Controllo se esiste una prenotazione  per un determinato posto, nella stessa fascia oraria e  stessa data*/
	public static int checkPosto(int turno,String data,int idPosto) throws SQLException, NamingException {
		Connection conn=cdb.startConnection();
		int flag;
		
		/*Controllo se esiste una prenotazione  per un determinato posto, nella stessa fascia oraria e  stessa data*/
		String que="SELECT * FROM prenotazione WHERE (Turno=? OR Turno=3) AND DataPrenotazione=? AND Ref_IdPosto=? ";
		prst=conn.prepareStatement(que); 
		prst.setInt(1, turno);
		prst.setString(2, data);
		prst.setInt(3,idPosto);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		if(rs.next()) {
			flag=0;
		}else {
			flag=1;
		}
		conn.close();
		return flag;
	}
	
	/*Aggiungo la prenotazione dell'utente*/
	public static void effettuaPrenotazion(int turno,String data,int idPosto,int idUtente,int nrLettini, float prezzo) throws Exception, SQLException{
		Connection conn=cdb.startConnection();
		
		String query="INSERT INTO prenotazione(Turno,DataPrenotazione,Ref_IdPosto,Ref_IDUtente,NumeroLettini,PrezzoTotale) VALUES(?,?,?,?,?,?)";
		

		prst=conn.prepareStatement(query); 
		prst.setInt(1, turno);
		prst.setString(2, data);
		prst.setInt(3,idPosto);
		prst.setInt(4,idUtente);
		prst.setInt(5, nrLettini);
		prst.setFloat(6, prezzo);
	
		prst.executeUpdate();
		
		
		conn.close();
		return ;
	}

	public static void annullaPrenotazione(int idPrenotazione, int idUtente) throws Exception, SQLException {
		Connection conn=cdb.startConnection();
		
		String query = "DELETE FROM prenotazione WHERE IDPrenotazione=? and Ref_IDUtente=?";
		prst=conn.prepareStatement(query);
		prst.setInt(1,idPrenotazione);
		prst.setInt(2,idUtente);
		prst.executeUpdate();
		
		conn.close();
	}
	
	/*Mi faccio dare tutti i prodotti per stamparli nella sezione Bar*/
	public static JSONArray getProdotti() throws Exception {
		JSONArray prodotti=null;
		
		Connection conn=cdb.startConnection();
		
		String query="Select * FROM prodotto";
		prst=conn.prepareStatement(query);
		
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		if(rs!=null) {
			
			prodotti = MyUtils.convertToJSONArray(rs);
			}
		conn.close();
		return prodotti;
	}
	
	/*Richiedo l'ultimo id dell' ordinazione inserita per impostare l'id corretto della nuova ordinazione*/
	public static int getLastIDOrdine() throws Exception {
		Connection conn=cdb.startConnection();
		
		String que="SELECT IDOrdinazione from smartshore_db.ordinazione ORDER BY IDOrdinazione DESC LIMIT 1";
		prst=conn.prepareStatement(que);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		int id=0;
		if(rs.next()) {
		id=rs.getInt("IDOrdinazione");
		}
		conn.close();
		return id;
	}
/*Inserisco i prodotti ordinati dall'utente*/	
	public static void inserisciOrdini(int idOrdine, int idUtente, int idProdotto, String data, int qtProdotto, float prezzoProdotto) throws Exception{
		Connection conn=cdb.startConnection();
		
		String query="INSERT INTO ordinazione(IDOrdinazione,Ref_IDUtente,Ref_IdProdotto,DataOrdinazione,QtProdotto,PrezzoAggregato) VALUES(?,?,?,?,?,?)";
		prst=conn.prepareStatement(query); 
		prst.setInt(1, idOrdine);
		prst.setInt(2,idUtente);
		prst.setInt(3,idProdotto);
		prst.setString(4, data);
		prst.setInt(5, qtProdotto);
		prst.setFloat(6, prezzoProdotto);
		
		prst.executeUpdate();
		
		conn.close();
	}
	
	public static JSONObject getIncassoPrenotazioni(String data) throws Exception, SQLException {
		JSONObject incasso=new JSONObject();
		Connection conn=cdb.startConnection();
		
		String query="SELECT DataPrenotazione, SUM(PrezzoTotale) as incassoTotale, count(IDPrenotazione) as numeroPrenotazioni FROM smartshore_db.prenotazione where DataPrenotazione=?";
		prst=conn.prepareStatement(query);
		prst.setString(1,data);
		
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		
		if(rs.next()) {
			incasso = MyUtils.convertToJSONObject(rs);
			}
		conn.close();
		return incasso;
	}
	public static JSONObject getIncassoOrdinazioni(String data) throws Exception, SQLException {
		JSONObject incasso=new JSONObject();
		Connection conn=cdb.startConnection();
		String query="SELECT DataOrdinazione, SUM(PrezzoAggregato) as incassoTotale FROM smartshore_db.ordinazione where DataOrdinazione=?;";
		prst=conn.prepareStatement(query);
		prst.setString(1,data);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		
		if(rs.next()) {
			incasso = MyUtils.convertToJSONObject(rs);
			}
		conn.close();
		return incasso;
	}

	public static JSONArray getPrenotazioniUtente(int idUtente) throws Exception {
		JSONArray prenotazioni=null;
		
		Connection conn=cdb.startConnection();
		
		String query="Select * FROM prenotazione WHERE Ref_IDUtente=?";
		prst=conn.prepareStatement(query);
		prst.setInt(1,idUtente);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		if(rs!=null) {
			prenotazioni = MyUtils.convertToJSONArray(rs);
			}
		conn.close();
		return prenotazioni;
	}

	public static JSONArray getOrdinazioniUtente(int idUtente)throws Exception {
		JSONArray ordinazioni=null;
		
		Connection conn=cdb.startConnection();
		
		String query="Select * FROM ordinazione , prodotto WHERE Ref_IDUtente=? AND Ref_IdProdotto=IDProdotto";
		prst=conn.prepareStatement(query);
		prst.setInt(1,idUtente);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		if(rs!=null) {
			ordinazioni = MyUtils.convertToJSONArray(rs);
			}
		conn.close();
		return ordinazioni;
	}
/*Mi faccio dare gli ordini della data corrente da mostrare al Barista*/
	public static JSONArray getOrdiniGiornaliereInCoda(String data) throws Exception {
		JSONArray ordini=null;
		
		Connection conn=cdb.startConnection();
		
		String query="SELECT IDOrdinazione, ordinazione.Ref_IDUtente, QtProdotto, NomeProdotto, Categoria, Stato, Ref_IDPosto, Ref_IdProdotto FROM smartshore_db.ordinazione, smartshore_db.prodotto, smartshore_db.prenotazione WHERE DataOrdinazione=? AND Ref_IdProdotto=IDProdotto and prenotazione.Ref_IDUtente=ordinazione.Ref_IDUtente and DataPrenotazione=?";
		prst=conn.prepareStatement(query);
		prst.setString(1,data);
		prst.setString(2, data);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		if(rs!=null) {
			ordini = MyUtils.convertToJSONArray(rs);
			}
		conn.close();
		return ordini;
		
	}
	public static JSONArray getOrdiniGiornalierePronti(String data) throws Exception {
		JSONArray ordiniPronti=null;
		
		Connection conn=cdb.startConnection();
		
		String query="SELECT IDOrdinazione, Ref_IDUtente, QtProdotto, NomeProdotto, Categoria FROM smartshore_db.ordinazione, smartshore_db.prodotto WHERE DataOrdinazione=? AND Ref_IdProdotto=IDProdotto AND stato=1";
		prst=conn.prepareStatement(query);
		prst.setString(1,data);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		if(rs!=null) {
			
			ordiniPronti = MyUtils.convertToJSONArray(rs);
			}
		conn.close();
		return ordiniPronti;
		
	}
/*Imposto lo stato dell'ordine ad 1 quando il barista ha completato l'rdine corrispondente*/
	public static void setStatoOrdine(int idOrdine, int idUtente, int idProdotto, String data) throws Exception {
		Connection conn=cdb.startConnection();
		
		String query="UPDATE smartshore_db.ordinazione SET Stato = 1 WHERE IDOrdinazione=? AND Ref_IDUtente=? AND Ref_IdProdotto=? AND DataOrdinazione=?";
		prst=conn.prepareStatement(query);
		prst.setInt(1, idOrdine);
		prst.setInt(2, idUtente);
		prst.setInt(3, idProdotto);
		prst.setString(4,data);
		prst.executeUpdate();
		
		conn.close();
		
	}

	
	/*Inserisco un nuovo utente, dopo che la "biglietteria ha inserito i dati utili alla regitrazione
	 * Non viene richiesta password, sarà l'utente finale ad inserirla se vorrà dall'apposita sezione
	 * "*/
	public static int registrazioneUtenteTemp(String nome, String cognome, String email, String sesso) throws Exception {
		Connection conn=cdb.startConnection();
		
		
		String query="INSERT INTO utente(NomeUtente,CognomeUtente,EMailUtente,Sesso) VALUES(?,?,?,?)";
		prst=conn.prepareStatement(query);
		prst.setString(1, nome);
		prst.setString(2, cognome);
		prst.setString(3, email);
		prst.setString(4, sesso);
		prst.executeUpdate();
		
		/*mi faccio restituire l' id, dell'utente appena inserito
		 * per passarlo alla servlet "PrenotaPosto" per effettuare la prenotazione
		 * a nome dell'utente temporaneo appena "registrato"
		 * */
		String que="SELECT IDUtente from smartshore_db.utente ORDER BY IDUtente DESC LIMIT 1";
		prst=conn.prepareStatement(que);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		int id=0;
		if(rs.next()) {
		id=rs.getInt("IDUtente");
		}
		
		conn.close();
		return id;
		
		
	}
	
/*Inserisco la password data dall'utente, associandola all'indirizzo email
 * dichiarato dall'utente nella sezione della biglietteria
 * */
	
	public static int completaRegistrazione(String email, String password) throws SQLException, NamingException {
		Connection conn=cdb.startConnection();
		int flag;
		
		/*Controllo se esiste davvero la mail dell'utente*/
		String que="SELECT * from smartshore_db.utente WHERE EmailUtente=? ";
		prst=conn.prepareStatement(que);
		prst.setString(1,email);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		
		if(rs.next()) {
		
		String query="UPDATE utente SET PasswordUtente = ? WHERE EmailUtente=?"; 
		prst=conn.prepareStatement(query);
		prst.setString(1, password);
		prst.setString(2,email);
		prst.executeUpdate();
		flag=1;
		
		}else {
			flag=0;
		}
	
		conn.close();
		return flag;
	}

	/*
	 * Richiedo i dati della postazione richiesta per mostrarli nella schermata corrispondente.*/	
	public static JSONObject getInfoPostazione(int id, String data, int fasciaOraria) throws Exception {
		JSONObject info=new JSONObject();
		Connection conn=cdb.startConnection();
		
		String query="SELECT IDUtente, NomeUtente, CognomeUtente, Turno, Presente, NumeroLettini from smartshore_db.prenotazione, smartshore_db.utente WHERE IDUtente=Ref_IDUtente and DataPrenotazione=? and (Turno=? OR Turno=3) and Ref_IDPosto=?";
		prst=conn.prepareStatement(query);
		prst.setString(1,data);
		prst.setInt(2, fasciaOraria);
		prst.setInt(3, id);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		if(rs.next()) {
			info = MyUtils.convertToJSONObject(rs);
			}
		
		
		conn.close();	
		return info;
		
	}
	
	/*Aggiorno la colonna "Presente" della tabella Prenotazione settando il valore ad 1, per segnare che l'utente adesso si trova
	 * nella postazione prenotata.
	 *  */
	public static void setIngressoLido(String data, int turno, int idUtente) throws Exception {
		Connection conn=cdb.startConnection();
		
		String query="UPDATE smartshore_db.prenotazione SET Presente = 1 WHERE DataPrenotazione=? AND Ref_IDUtente=? AND Turno=?";
		prst=conn.prepareStatement(query);
		prst.setString(1,data);
		prst.setInt(2, idUtente);
		prst.setInt(3, turno);
		
		prst.executeUpdate();
		
		conn.close();
	}
	/*Aggiorno la colonna "Presente" della tabella Prenotazione settando il valore ad 0, per segnare che l'utente NON si trova
	 * nella postazione prenotata.
	 *  */
	public static void setUscitaLido(String data, int turno, int idUtente) throws Exception {
		Connection conn=cdb.startConnection();
		
		String query="UPDATE smartshore_db.prenotazione SET Presente = 0 WHERE DataPrenotazione=? AND Ref_IDUtente=? AND Turno=?";
		prst=conn.prepareStatement(query);
		prst.setString(1,data);
		prst.setInt(2, idUtente);
		prst.setInt(3, turno);
		
		prst.executeUpdate();
		
		conn.close();
	}
	
	
	/*Elimina record selzionato attraverso l'ID dalla tabella dipendente*/
	public static void eliminaDipendente(int id) throws Exception {
		Connection conn=cdb.startConnection();
		
		String query = "DELETE FROM dipendente WHERE IDDipendente = ?";
		prst=conn.prepareStatement(query);
		prst.setInt(1,id);
		
		prst.executeUpdate();
		
		conn.close();
		
	}
/*Aggiungo nuovo dipendente dopo aver inserito i campi necessari nella schermata dell'amminitsratore */
	public static void registrazioneDipendente(String nome, String cognome, String email, String password, String sesso,String ruolo) throws Exception {
		Connection conn=cdb.startConnection();
		
		String query="INSERT INTO Dipendente(NomeDipendente,CognomeDipendente,EMailDipendente,PasswordDipendente,Sesso,Ruolo) VALUES(?,?,?,?,?,?)";
		prst=conn.prepareStatement(query);
		prst.setString(1, nome);
		prst.setString(2, cognome);
		prst.setString(3, email);
		prst.setString(4, password);
		prst.setString(5, sesso);
		prst.setString(6, ruolo);
		prst.executeUpdate();
		
		
		conn.close();
		
	}
	
	
	/*Funzione utile a verificare se l'utente si trova nel lido per poter effettuare un ordinazione al bar*/
	public static boolean verificaPresenza(int idUtente, String data) throws SQLException, NamingException {
		boolean flag=false;
		
		Connection conn=cdb.startConnection();
		String que="SELECT * from prenotazione WHERE Ref_IDUtente=? AND DataPrenotazione=?";
		prst=conn.prepareStatement(que);
		prst.setInt(1,idUtente);
		prst.setString(2, data);
		prst.executeQuery();
		ResultSet rs=prst.getResultSet();
		
		if(rs.next()) {
			int i=rs.getInt("Presente");
			if(i==0) {
				flag=true;
			}
		}
		
		conn.close();
		return flag;
	}

}