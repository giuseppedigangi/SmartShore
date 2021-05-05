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

import it.unipa.community.digangi.giuseppe.controller.ControllerDB;
import it.unipa.community.digangi.giuseppe.controller.MyUtils;

/**
 * Servlet implementation class CompletaRegistrazioneServlet
 */
@WebServlet("/CompletaRegistrazione")
public class CompletaRegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompletaRegistrazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String address = "/WEB-INF/utente/CompletaRegistrazione.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        String errorString = null;
        
        /*verifico che email e password inseriti siano del formato corretto*/
    	errorString = MyUtils.verificaDatiInseriti(email, password);
    	
    	if(!errorString.equals("")) {
    		request.setAttribute("errorString", errorString);
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/utente/CompletaRegistrazione.jsp");
            dispatcher.forward(request, response);
    	}else {
		        try {
					int flag=ControllerDB.completaRegistrazione(email, password);
		
					if(flag==0) {
						errorString="Controlla i dati inseriti e riprova";
						request.setAttribute("errorString", errorString);     
						
			            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/utente/CompletaRegistrazione.jsp");
			            dispatcher.forward(request, response);
					}else {
						response.sendRedirect(request.getContextPath()+ "/Login");
					}
		
				} catch (SQLException | NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	}
		
	}

}
