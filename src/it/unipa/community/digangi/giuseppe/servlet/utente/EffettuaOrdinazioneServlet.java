package it.unipa.community.digangi.giuseppe.servlet.utente;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unipa.community.digangi.giuseppe.controller.ControllerDB;
import it.unipa.community.digangi.giuseppe.controller.MyUtils;
import it.unipa.community.digangi.giuseppe.model.Carrello;
import it.unipa.community.digangi.giuseppe.model.Prodotto;
import it.unipa.community.digangi.giuseppe.model.Utente;

/**
 * Servlet implementation class EffettuaOrdinazioneServlet
 */
@WebServlet("/EffettuaOrdinazione")
public class EffettuaOrdinazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EffettuaOrdinazioneServlet() {
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
		Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
		if(carrello == null) {
			errore = "Nessun prodotto inserito nel carrelo, aggiungi almeno un prodotto prima di effettuare l'ordine";
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().print(errore);
			return;
		}
		HttpSession session = request.getSession();
		Utente utente =  MyUtils.getLoginedUser(session);
		
		int idUtente = utente.getId();
		String data=LocalDate.now().toString();
		
		// Verifica se il cliente è presente fisicamente in una postazione del lido.
		try {
			if(ControllerDB.verificaPresenza(idUtente,data)) {
				errore = "Per poter effettuare un'ordine devi essere presente nel lido";
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(errore);
				return;
			}
			else {
			
				String today = LocalDate.now().toString();
				float totale=0;
				
				int idOrdine=0;
				try {
					idOrdine = ControllerDB.getLastIDOrdine();
					idOrdine+=1;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (Prodotto p : carrello.getProdotti().keySet()) {
					totale=p.getPrezzo()*carrello.getProdotti().get(p);
					try {
						ControllerDB.inserisciOrdini(idOrdine,idUtente, p.getIDProdotto(), today, carrello.getProdotti().get(p), totale);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
						carrello.eliminaCarrello();
						request.getSession().removeAttribute("carrello");
			}
		} catch (SQLException | NamingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
 }
}
