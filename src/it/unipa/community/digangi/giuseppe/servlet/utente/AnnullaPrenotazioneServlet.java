package it.unipa.community.digangi.giuseppe.servlet.utente;

import java.io.IOException;
import java.time.LocalDate;

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
 * Servlet implementation class AnnullaPrenotazioneServlet
 */
@WebServlet("/AnnullaPrenotazione")
public class AnnullaPrenotazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnnullaPrenotazioneServlet() {
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
		HttpSession session = request.getSession();
		Utente utente =  MyUtils.getLoginedUser(session);
		
		int idUtente = utente.getId();
		int idPrenotazione=Integer.parseInt(request.getParameter("idPre"));
		String dataPre=request.getParameter("dataPre");
		LocalDate dataPrenotazione=LocalDate.parse(dataPre);
		LocalDate today = LocalDate.now();
	
		/*Controllo che la prenotazione che l'utente vuole eliminare non sia della data odierna o una data passata*/
		
		
		if((dataPrenotazione.isBefore(today)||dataPrenotazione.isEqual(today))) {
			String errore = "Non puoi annullare una prenotazione passata o della data odierna";
			 
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().print(errore);
			response.getWriter().close();
		}else {
			
		
			
			try {
				ControllerDB.annullaPrenotazione(idPrenotazione, idUtente);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
