package it.unipa.community.digangi.giuseppe.servlet.dipendente.bagnino;

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

import org.json.JSONObject;

import it.unipa.community.digangi.giuseppe.controller.ControllerDB;
import it.unipa.community.digangi.giuseppe.controller.MyUtils;
import it.unipa.community.digangi.giuseppe.model.Dipendente;

/**
 * Servlet implementation class MostraDettagliServlet
 */
@WebServlet("/MostraDettagli")
public class MostraDettagliServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostraDettagliServlet() {
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
        System.out.println(ruolo);
     // Not logged in
        if (loginedDip == null) {
            // Redirect to login page.
            response.sendRedirect(request.getContextPath() + "/LoginDipendente");
            return;
        }
        /*Controllo se il dipendente ha i privilegi appropriati per accedere alla pagina*/
        if(!ruolo.equals("bagnino")) {
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
        	
	        
	        JSONObject informazioni=new JSONObject();
	        response.setContentType("application/json");
	        
	        int id=Integer.parseInt(request.getParameter("idPosto"));
	        String data = LocalDate.now().toString();
	       
			int orario=LocalTime.now().getHour();
			int fasciaOraria = 0;
			if (orario>=8 & orario<14) {
				fasciaOraria = 1;

			}else {
				fasciaOraria=2;
			}
			
	        
	        try {
	        	
	        	response.getWriter().println(ControllerDB.getInfoPostazione(id,data,fasciaOraria));
	        	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject informazioni=new JSONObject();
        response.setContentType("application/json");
        
        int id=Integer.parseInt(request.getParameter("idPosto"));
        String data = LocalDate.now().toString();
       
		int orario=LocalTime.now().getHour();
		int fasciaOraria = 0;
		if (orario>=8 & orario<14) {
			fasciaOraria = 1;

		}else {
			fasciaOraria=2;
		}
		
        
        try {
        	
        	response.getWriter().println(ControllerDB.getInfoPostazione(id,data,fasciaOraria));
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
