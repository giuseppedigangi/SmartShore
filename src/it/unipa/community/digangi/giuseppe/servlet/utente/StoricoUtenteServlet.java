package it.unipa.community.digangi.giuseppe.servlet.utente;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import it.unipa.community.digangi.giuseppe.controller.ControllerDB;
import it.unipa.community.digangi.giuseppe.controller.MyUtils;
import it.unipa.community.digangi.giuseppe.model.Utente;

/**
 * Servlet implementation class StoricoUtenteServlet
 */
@WebServlet("/StoricoUtente")
public class StoricoUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoricoUtenteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		 
        // Check User has logged on
        Utente loginedUser = MyUtils.getLoginedUser(session);
 
        // Not logged in
        if (loginedUser == null) {
            // Redirect to login page.
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }
        // Store info to the request attribute before forwarding.
       
        int idUtente=loginedUser.getId();
        
        response.setContentType("application/json");
        
        
        JSONArray prenotazioni=new JSONArray();
        try {
			prenotazioni=ControllerDB.getPrenotazioniUtente(idUtente);
			if(prenotazioni!=null) {
				
				request.setAttribute("prenotazioni",prenotazioni);
				 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        JSONArray ordinazioni=new JSONArray();
        try {
        	ordinazioni=ControllerDB.getOrdinazioniUtente(idUtente);
			if(ordinazioni!=null) {
				
				request.setAttribute("ordinazioni",ordinazioni);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/utente/Storico.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
