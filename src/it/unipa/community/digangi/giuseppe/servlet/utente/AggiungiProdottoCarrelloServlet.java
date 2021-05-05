package it.unipa.community.digangi.giuseppe.servlet.utente;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unipa.community.digangi.giuseppe.controller.MyUtils;
import it.unipa.community.digangi.giuseppe.model.Carrello;
import it.unipa.community.digangi.giuseppe.model.Prodotto;
import it.unipa.community.digangi.giuseppe.model.Utente;

/**
 * Servlet implementation class AggiungiProdottoCarrelloServlet
 */
@WebServlet("/AggiungiProdottoCarrello")
public class AggiungiProdottoCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiProdottoCarrelloServlet() {
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
        // Store info to the request attribute before forwarding.
        request.setAttribute("utente", loginedUser);
        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/utente/Carrello.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String errore;
		Prodotto prodotto;
		
		int id = Integer.parseInt(request.getParameter("idProd"));
		int numero = Integer.parseInt(request.getParameter("quantita"));
		float prezzo=Float.parseFloat(request.getParameter("prezzoProd"));
		String categoria=request.getParameter("categoria");
		String nome=request.getParameter("nomeProd");
		prodotto=new Prodotto(id,nome,prezzo);
		Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
		if(carrello == null) {
			carrello = new Carrello();
		}
		
		carrello.aggiungiProd(prodotto, numero);
		request.getSession().setAttribute("carrello", carrello);
		

		 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/utente/Carrello.jsp");
	        dispatcher.forward(request, response);

	}

}
