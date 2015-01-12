/**
 * 
 */
package intergiciels.servlets;

import java.io.IOException;

import intergiciels.beans.Facade;
import intergiciels.beans.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Jade Boumaza
 *
 */

@WebServlet
public class CV extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@EJB
	Facade facade;
	
	public static final String VUE = "/WEB-INF/cv.jsp";
	public static final String ATTRIBUT_SESSION_ID = "id";
	public static final String ATTRIBUT_SESSION_USER = "sessionUser";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* Récupération de la session depuis la requête */
		HttpSession session = request.getSession();
		
		/* Récupération de l'id (mail) de la session en cours */
		String id = (String) session.getAttribute(ATTRIBUT_SESSION_ID);
		
		if (id == null) { // l'utilisateur n'est pas connecté
			// renvoi vers la page de connexion
			this.getServletContext().getRequestDispatcher("/WEB-INF/signin.jsp").forward(request,response);
		}
		else { // L'utilisateur est connecté
			/* Affichage de la page des correspondances */
			this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/* Récupération de la session depuis la requête */
		HttpSession session = request.getSession();
		
		/* Récupération de l'id (mail) de la session en cours */
		String id = (String) session.getAttribute(ATTRIBUT_SESSION_ID);
		
		if (id == null) { // l'utilisateur n'est pas connecté
			// renvoi vers la page de connexion
			this.getServletContext().getRequestDispatcher("/WEB-INF/signin.jsp").forward(request,response);
		}
		else { // l'utilisateur est connecté
		
			/* Récupération de l'user de la session */
			User user = (User) session.getAttribute(ATTRIBUT_SESSION_USER);
			System.out.println("Récup de l'user : " + user);
			
			
			session.setAttribute(ATTRIBUT_SESSION_USER, user);
		
			
			
			this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
		}
		
	}

}
