package it.unipa.community.digangi.giuseppe.controller;

import java.sql.ResultSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import it.unipa.community.digangi.giuseppe.model.Dipendente;
import it.unipa.community.digangi.giuseppe.model.Utente;

public class MyUtils {
	
	
	
	 public static void storeLoginedUser(HttpSession session, Utente loginedUser) {
		 // posso accedere nella JSP via ${loginedUser}
	        session.setAttribute("loginedUser", loginedUser);
	    }
	 
	 public static Utente getLoginedUser(HttpSession session) {
	        Utente loginedUser = (Utente) session.getAttribute("loginedUser");
	        return loginedUser;
	    }
	 public static void storeLoginedDip(HttpSession session, Dipendente loginedDip) {
	        // posso accedere nella JSP via ${loginedDip}
	        session.setAttribute("loginedDip", loginedDip);
	    }
	 
	 public static Dipendente getLoginedDip(HttpSession session) {
	        Dipendente loginedDip = (Dipendente) session.getAttribute("loginedDip");
	        return loginedDip;
	    }
	 
	 
	  /*Le funzioni  che seguono mi permettono di trasformare il resultset in un oggetto JSON 
	   * o in un array di JSON
	   * */  
	    
	    public static JSONArray convertToJSONArray(ResultSet resultSet) throws Exception {
	    	JSONArray jsonArray = new JSONArray();
	        while (resultSet.next()) {
	        	int total_rows = resultSet.getMetaData().getColumnCount();
	            JSONObject obj = new JSONObject();
	            for (int i = 0; i < total_rows; i++) {
	                obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
	                	.toLowerCase(), resultSet.getObject(i + 1));               
	            }
	            jsonArray.put(obj);
	        }
	        return jsonArray;
	    }
	    
	    public static JSONObject convertToJSONObject(ResultSet resultSet) throws Exception {
	    	JSONObject object = new JSONObject();
	    	
	    		int totalColumns = resultSet.getMetaData().getColumnCount();
	    		
	            	for (int i = 0; i < totalColumns; i++) {
	            		
	            		object.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
	            	}
	    	
	    
	        return object;
	    }

	    
	    /**
		 * Verifica la validità dei campi email e password relativi al form di registrazione
		 * e che rispettino il formato richiesto. 
		 * 
		 */
		public static String verificaDatiInseriti(String email, String password) {
			String errore ="";
			String regex;
			
			/*controllo validità email*/
			regex = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@"
					+ "((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)"
					+ "+[a-zA-Z]{2,}))$";
		
			if(!email.matches(regex))
				return errore = "L'email non rispetta il formato richiesto.";
			
			/*controllo validità password*/
			regex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
			
			if(!password.matches(regex))
				return errore = "La password deve contenere almeno 8 caratteri, almeno una lettera maiuscola ed un numero";

			
			return errore;
		}
		
		

}
