package it.unipa.community.digangi.giuseppe.servlet.dipendente.amministratore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * Servlet implementation class MostraPersonaleServlet
 */
@WebServlet("/MostraPersonale")
public class MostraPersonaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostraPersonaleServlet() {
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
        
	        List<Dipendente> listaDipendenti=new ArrayList<Dipendente>();
	        
	        try {
				listaDipendenti=ControllerDB.displayPersonale();
				System.out.println(listaDipendenti.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        request.setAttribute("listaDipendenti",listaDipendenti);
	        // If the user has logged in, then forward to the page
	        // /WEB-INF/views/userInfoView.jsp
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/dipendente/amministratore/MostraPersonale.jsp");
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
