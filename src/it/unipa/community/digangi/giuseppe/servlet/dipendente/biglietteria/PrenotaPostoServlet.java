package it.unipa.community.digangi.giuseppe.servlet.dipendente.biglietteria;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unipa.community.digangi.giuseppe.controller.ControllerDB;
import it.unipa.community.digangi.giuseppe.controller.MyUtils;
import it.unipa.community.digangi.giuseppe.model.Dipendente;

/**
 * Servlet implementation class PrenotaPostoServlet
 */
@WebServlet("/PrenotaPosto")
public class PrenotaPostoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrenotaPostoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		 
        // Check Dip has logged on
        Dipendente loginedDip = MyUtils.getLoginedDip(session);
        String ruolo=loginedDip.getRuolo();
     // Not logged in
        if (loginedDip == null) {
            // Redirect to login page.
            response.sendRedirect(request.getContextPath() + "/LoginDipendente");
            return;
        }
        /*Controllo se il dipendente ha i privilegi appropriati per accedere alla pagina*/
        if(!ruolo.equals("biglietteria")) {
        
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
        String sesso = request.getParameter("sesso");
        String errore;
        int idUtente=0;
        int turno=Integer.parseInt(request.getParameter("turno"));
		String data=LocalDate.now().toString();
		int nrLettini=Integer.parseInt(request.getParameter("nr"));
		int idPosto=Integer.parseInt(request.getParameter("posto"));
		float prezzo=Float.parseFloat(request.getParameter("costo"));
		int flag = 0;
		
        try {
			int flag1=ControllerDB.verificaEmailDuplicata(email);
			if(flag1==0) {
				errore = "L'email inserita esiste, indica un altra email";
			    
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(errore);
				response.getWriter().close();
				return;
			}
			
			} catch (NamingException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
			try {
				flag = ControllerDB.checkPosto(turno, data, idPosto);
			} catch (SQLException | NamingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(flag==0) {
				errore = "Postazione prenotata, scegli un altro posto";
				 
			    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(errore);
				response.getWriter().close();
				return;
			}
        
        try {	
        	/*effettuo la registrazione temporanea dell'utente*/
			 idUtente=ControllerDB.registrazioneUtenteTemp(nome, cognome, email, sesso);
		} catch (NamingException | SQLException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
			if(idUtente!=0) {
			
				try {
						ControllerDB.effettuaPrenotazion(turno, data, idPosto, idUtente, nrLettini, prezzo);
				
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}else {
				errore = "Errore, controlla i dati e riprova";
				 
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(errore);
				response.getWriter().close();
			}
		}

}
