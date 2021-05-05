package it.unipa.community.digangi.giuseppe.servlet.utente;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unipa.community.digangi.giuseppe.controller.ControllerDB;
import it.unipa.community.digangi.giuseppe.controller.MyUtils;
import it.unipa.community.digangi.giuseppe.model.Utente;

/**
 * Servlet implementation class MostraDisponibilita
 */
@WebServlet("/MostraDisponibilita")
@MultipartConfig
public class MostraDisponibilitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostraDisponibilitaServlet() {
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
        
      
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String errorString = null;
		String data = request.getParameter("dataPren");
		String fasciaOraria = request.getParameter("turnoPren");
	
		String errore;
		
		if(data==""||fasciaOraria=="") {
			errore = "Seleziona tutti i campi richiesti";
			request.setAttribute("errorString", errore);     
			 
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().print(errore);
			response.getWriter().close();
		}
		
			
		/*Controllo che la data inserita non sia antecedente alla data attuale
		 * e che non sia maggiore della data massima consentita
		 * */
		LocalDate now = LocalDate.now();
		LocalDate dataInserita = LocalDate.parse(data);
		if(dataInserita.isBefore(now) || dataInserita.isAfter(LocalDate.parse("2020-09-30"))) {
			errore = "La data inserita non rispetta il formato richiesto.";
			request.setAttribute("errorString", errore);     
			 
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().print(errore);
			response.getWriter().close();
		}else {
			
		
            	
			try {
				response.setContentType("application/json");
				response.getWriter().println(ControllerDB.richiediPostazioniPrenotate(data, fasciaOraria));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            }
	}
}

