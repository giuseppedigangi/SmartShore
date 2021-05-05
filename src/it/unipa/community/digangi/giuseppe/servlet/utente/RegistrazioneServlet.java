package it.unipa.community.digangi.giuseppe.servlet.utente;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unipa.community.digangi.giuseppe.controller.ControllerDB;
import it.unipa.community.digangi.giuseppe.controller.MyUtils;
import it.unipa.community.digangi.giuseppe.model.Utente;

/**
 * Servlet implementation class RegistrazioneServlet
 */
@WebServlet("/Registrazione")
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		 
        // Check User has logged 
        Utente loginedUser = MyUtils.getLoginedUser(session);
 
        if (loginedUser != null) {
            // Redirect to main page.
            response.sendRedirect(request.getContextPath() + "/AreaRiservata");
            return;
        }
        
        String address = "/WEB-INF/utente/Registrazione.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        String sesso = request.getParameter("sesso");
        String errorString=null;
        
        /*verifico che i dati siano tutti inseriti*/
        if(nome==null|| cognome==null|| sesso==null|| email == null || password == null || email.length() == 0 || password.length() == 0) {
            
            errorString = "Completa tutti i campi!";
            request.setAttribute("errorString", errorString);
           
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/utente/Registrazione.jsp");
            dispatcher.forward(request, response);
        }else {
        	
        	/*verifico che email e password inseriti siano del formato corretto*/
        	errorString = MyUtils.verificaDatiInseriti(email, password);
        	
        	if(!errorString.equals("")) {
        		request.setAttribute("errorString", errorString);
        		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/utente/Registrazione.jsp");
	            dispatcher.forward(request, response);
        	}else {
        		       	  
		        try {
					int flag=ControllerDB.registrazioneUtente(nome, cognome, email, password, sesso);
					if(flag==0) {
						errorString="Controlla i dati inseriti,email esistente";
						
						request.setAttribute("errorString", errorString);
						
			            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/utente/Registrazione.jsp");
			            dispatcher.forward(request, response);
			            
			            
					}else {
						response.sendRedirect(request.getContextPath() + "/Login");
					}
						} catch (NamingException | SQLException e) {
						e.printStackTrace();
					}
	        
	        	}
        }
	}
}
