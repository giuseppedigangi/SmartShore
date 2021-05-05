package it.unipa.community.digangi.giuseppe.servlet.dipendente;

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
import it.unipa.community.digangi.giuseppe.model.Dipendente;
import it.unipa.community.digangi.giuseppe.model.Utente;

/**
 * Servlet implementation class LoginDipendenteServlet
 */
@WebServlet("/LoginDipendente")
public class LoginDipendenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginDipendenteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		 
        // Check User has logged 
        Dipendente loginedDip = MyUtils.getLoginedDip(session);
 
        if (loginedDip != null) {
            // Redirect to main page.
            response.sendRedirect(request.getContextPath() + "/AreaDipendente");
            return;
        }
        
        String address = "/WEB-INF/dipendente/LoginDipendente.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		
        
		Dipendente dip = null;
		boolean hasError = false;
        String errorString = null;
        
       
		if(email == null || password == null || email.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Inserisci email e password!";
        }else {
        	try {
				dip = ControllerDB.loginDipendente(email,password);
				
				 if (dip == null) {
	                    hasError = true;
	                    errorString = "Email or password invalid";
	                }
			} catch (SQLException e) {
				 e.printStackTrace();
	                hasError = true;
	                errorString = e.getMessage();
				
				e.printStackTrace();
			} catch (NamingException e) {
			
				e.printStackTrace();
			}
        	
        }
        if (hasError) {
           
            // Store information in request attribute, before forward.
            request.setAttribute("errorString", errorString);
           
 
            // Forward to LoginDipendente.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/dipendente/LoginDipendente.jsp");
 
            dispatcher.forward(request, response);
        }
        /*Se non ci sono errori, salvo le info del dipendente nella sessione e mostro l'area dipendente*/
        else {
            HttpSession session = request.getSession();
            MyUtils.storeLoginedDip(session, dip);
            
            
            String ruolo= dip.getRuolo();
            request.setAttribute("ruolo", ruolo);
            
            // Redirect to "AreaDipendente" page.
            response.sendRedirect(request.getContextPath() + "/AreaDipendente");
           
            
            }
    
        
	}

}
