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
import it.unipa.community.digangi.giuseppe.model.Utente;

/**
 * Servlet implementation class RimuoviProdottoCarrelloServlet
 */
@WebServlet("/RimuoviProdottoCarrello")
public class RimuoviProdottoCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RimuoviProdottoCarrelloServlet() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
		
		int id = Integer.parseInt(request.getParameter("idProd"));
		carrello.rimuoviProd(id);
		System.out.println("rimozione prodotto");
		System.out.println(carrello);
		if(carrello.getProdotti().isEmpty()) {
			request.getSession().removeAttribute("carrello");
		}
		request.getSession().setAttribute("carrello", carrello);
		
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/utente/Carrello.jsp");
		        dispatcher.forward(request, response);

	}

}
