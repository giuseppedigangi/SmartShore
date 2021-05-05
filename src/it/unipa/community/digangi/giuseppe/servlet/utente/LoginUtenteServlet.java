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
 * Servlet implementation class LoginUtenteServlet
 */
@WebServlet("/Login")
public class LoginUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginUtenteServlet() {
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
        
        String address = "/WEB-INF/utente/Login.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember");
        
        
        Utente ut=null;
        boolean hasError = false;
        String errorString = null;
        
        
        //check form
        if(email == null || password == null || email.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Inserisci email e password!";
        }else {
        	try {
				ut = ControllerDB.loginUtente(email,password);
				
				 if (ut == null) {
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
           
 
            // Forward to /WEB-INF/Login.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/utente/Login.jsp");
            dispatcher.forward(request, response);
        }
        // If no error
        // Store user information in Session
        // And redirect to userInfo page.
        else {
            HttpSession session = request.getSession();
            MyUtils.storeLoginedUser(session, ut);
 
            // Redirect to AreaRiservata page
            response.sendRedirect(request.getContextPath() + "/AreaRiservata");
        }
    }
	

}
