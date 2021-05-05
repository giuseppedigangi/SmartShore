package it.unipa.community.digangi.giuseppe.servlet.dipendente.controllore;

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

import it.unipa.community.digangi.giuseppe.controller.ControllerDB;
import it.unipa.community.digangi.giuseppe.controller.MyUtils;
import it.unipa.community.digangi.giuseppe.model.Dipendente;

/**
 * Servlet implementation class SegnaIngressoServlet
 */
@WebServlet("/SegnaIngresso")
public class SegnaIngressoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SegnaIngressoServlet() {
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
        if(!ruolo.equals("controllore")) {
        
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data = LocalDate.now().toString();
		int idUtente=Integer.parseInt(request.getParameter("id"));
		int turno=Integer.parseInt(request.getParameter("turno"));
		
		try {
			ControllerDB.setIngressoLido(data, turno, idUtente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
