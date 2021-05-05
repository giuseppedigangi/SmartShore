package it.unipa.community.digangi.giuseppe.servlet.dipendente.amministratore;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import it.unipa.community.digangi.giuseppe.controller.ControllerDB;
import it.unipa.community.digangi.giuseppe.controller.MyUtils;
import it.unipa.community.digangi.giuseppe.model.Dipendente;

/**
 * Servlet implementation class CassaGiornalieraServlet
 */
@WebServlet("/CassaGiornaliera")
public class CassaGiornalieraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CassaGiornalieraServlet() {
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
        if(!ruolo.equals("amministratore")) {
        	
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
        	
        	JSONObject incassoPrenotazioni=new JSONObject();
        	JSONObject incassoOrdinazioni=new JSONObject();
        	
        	String data=LocalDate.now().toString();
        	
        	
				try {
					incassoPrenotazioni=ControllerDB.getIncassoPrenotazioni(data);
					request.setAttribute("incassoPren", incassoPrenotazioni);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
        	
	        	try {
					incassoOrdinazioni=ControllerDB.getIncassoOrdinazioni(data);
					request.setAttribute("incassoOrd", incassoOrdinazioni);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
        	
		        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/dipendente/amministratore/CassaGiornaliera.jsp");
		            dispatcher.forward(request, response);

        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
