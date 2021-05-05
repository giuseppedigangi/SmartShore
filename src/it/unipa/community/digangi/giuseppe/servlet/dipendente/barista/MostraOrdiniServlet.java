package it.unipa.community.digangi.giuseppe.servlet.dipendente.barista;

import java.io.IOException;
import java.time.LocalDate;

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
 * Servlet implementation class MostraOrdiniServlet
 */
@WebServlet("/MostraOrdini")
public class MostraOrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostraOrdiniServlet() {
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
        if(!ruolo.equals("barista")) {
        
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
        	
		 response.setContentType("application/json");
		        
		        
		 String data = LocalDate.now().toString();

 
        JSONArray ordini=new JSONArray();
        try {
        	ordini=ControllerDB.getOrdiniGiornaliereInCoda(data);
			if(ordini!=null) {
				 
				request.setAttribute("ordini",ordini);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/dipendente/barista/BarPanel.jsp");
        dispatcher.forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String data= LocalDate.now().toString();
		int idProdotto=Integer.parseInt(request.getParameter("prodotto"));
		int idUtente=Integer.parseInt(request.getParameter("utente"));
		int idOrdine=Integer.parseInt(request.getParameter("ordine"));
		
		try {
			ControllerDB.setStatoOrdine(idOrdine,idUtente,idProdotto,data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("application/json");


       JSONArray ordini=new JSONArray();
       try {
       	ordini=ControllerDB.getOrdiniGiornaliereInCoda(data);
			if(ordini!=null) {
				request.setAttribute("ordini",ordini);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/dipendente/barista/BarPanel.jsp");
        dispatcher.forward(request, response);
	}

}
