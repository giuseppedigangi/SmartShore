package it.unipa.community.digangi.giuseppe.servlet.utente;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class EffettuaPrenotazioneServlet
 */
@WebServlet("/EffettuaPrenotazione")
public class EffettuaPrenotazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EffettuaPrenotazioneServlet() {
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
		String errore;
		HttpSession session = request.getSession();
		Utente utente =  MyUtils.getLoginedUser(session);
		
		int idUtente = utente.getId();
		
		int turno=Integer.parseInt(request.getParameter("turno"));
		String data=request.getParameter("data");
		int nrLettini=Integer.parseInt(request.getParameter("nr"));
		int idPosto=Integer.parseInt(request.getParameter("posto"));
		float prezzo=Float.parseFloat(request.getParameter("costo"));
		
		
		/*Controllo che tutti i dati siano inseriti correttamente*/
		if(data==""||turno<0 ||turno >3 ||nrLettini<1 ||nrLettini>4||idPosto==0||idPosto>25||prezzo==0) {
			errore = "Dati inseriti non correti, riprova";
			 
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().print(errore);
			response.getWriter().close();
		}else {
		
		
				try {
					/*Controllo se il posto è effettivamente libero*/
					int flag=ControllerDB.checkPosto(turno, data, idPosto);
					
					if(flag==0) {
						errore = "Postazione prenotata, scegli un altro posto";
						 
			            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						response.getWriter().print(errore);
						response.getWriter().close();
						return;
					}
					/*Effettuo la prenotazione*/
					ControllerDB.effettuaPrenotazion(turno, data, idPosto, idUtente, nrLettini, prezzo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		}
	}

}
