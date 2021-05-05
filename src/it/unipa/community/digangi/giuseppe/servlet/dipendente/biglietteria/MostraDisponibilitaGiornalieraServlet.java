package it.unipa.community.digangi.giuseppe.servlet.dipendente.biglietteria;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

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
import it.unipa.community.digangi.giuseppe.model.Dipendente;

/**
 * Servlet implementation class MostraDisponibilitaGiornalieraServlet
 */
@WebServlet("/MostraDisponibilitaGiornaliera")
public class MostraDisponibilitaGiornalieraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostraDisponibilitaGiornalieraServlet() {
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
        /*Controllo se il dipendente ha i privilegi appropriati per accedere alla pagina
         * Questa servlet viene richiamata da Bagino, biglietteria e controllore
         * e alla fine viene dispatchata la pagina a seconda del ruolo che il dipendente possiede
         * */
        
        if(!(!ruolo.equals("biglietteria") || !ruolo.equals("controllore") || !ruolo.equals("bagnino"))) {
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
		String data = LocalDate.now().toString();
		int orario=LocalTime.now().getHour();
		String fasciaOraria = null;
		if (orario>=8 & orario<14) {
			fasciaOraria = "1";
			

		}else {
			fasciaOraria="2";
			
		}
		
		JSONArray postiOccupati=new JSONArray();
			try {
				response.setContentType("application/json");
				postiOccupati=ControllerDB.richiediPostazioniPrenotate(data, fasciaOraria);
				request.setAttribute("postiOccupati", postiOccupati);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JSONArray posti=new JSONArray();
	        try {
				posti=ControllerDB.getPosti();
				if(posti!=null) {
					
					  request.setAttribute("posti",posti);
					
						
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	     
	        
	        
	        RequestDispatcher dispatcher;
	        
	        /*Reindirizzo i dati richiesti, alla pagina jsp corrispondente
	         * in relazione al ruolo dell'utente che ne fa richiesta
	         * */
	        switch (ruolo)
            {
	        
	            case "bagnino":
	            	// Redirect to bagnino page.
	            	dispatcher = request.getRequestDispatcher("/WEB-INF/dipendente/bagnino/SituazioneLido.jsp");
			        dispatcher.forward(request, response);
		            break;   
		            
		            
	            case "biglietteria":
		            // Redirect to Biglietteria page.
	            	dispatcher = request.getRequestDispatcher("/WEB-INF/dipendente/biglietteria/PrenotazioneBiglietteria.jsp");
			        dispatcher.forward(request, response);
		            break;
		            
	            case "controllore":
	            	// Redirect to controllore page.
	            	dispatcher = request.getRequestDispatcher("/WEB-INF/dipendente/controllore/VarcoAccessi.jsp");
			        dispatcher.forward(request, response);
		            break;
            }
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
