/**
 * Signup :
 * servlet permettant à l'utilisateur de s'inscrire sur le site
 */
package intergiciels.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import intergiciels.beans.*;
import intergiciels.etc.Form;

/**
 * @author Jade BOUMAZA
 *
 */

@WebServlet("/signup")
public class Signup extends HttpServlet {
	
	@EJB
	Facade facade;

	private static final long serialVersionUID = 1L;
	
	public static final String VUE = "/WEB-INF/signup.jsp";
	public static final String ATTRIBUT_MAIL = "mail";
    public static final String ATTRIBUT_FORM = "form";
    public static final String ATTRIBUT_ERREUR = "erreur";
    public static final String ATTRIBUT_SESSION_ID = "id";
    public static final String ATTRIBUT_SESSION_USER = "sessionUser";
  

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* Récupération de la session depuis la requête */
		HttpSession session = request.getSession();
		
		/* Récupération de l'id (mail) de la session en cours */
		String id = (String) session.getAttribute(ATTRIBUT_SESSION_ID);
		
		if (id == null) { // l'utilisateur n'est pas connecté
			// renvoi vers la page d'inscription
			this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
		}
		else { // L'utilisateur est connecté
			/* renvoi vers la page d'index */
			this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request,response);
		}
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
        Form form = new Form();
       	
       	/* Récupération de la session depuis la requête */           
        HttpSession session = request.getSession();
				
        /* Récuération des champs du formulaire */
		String mail = form.getValeurChamp(request, "mail");
		String password = form.getValeurChamp(request, "password");
		   

		User user = new User();
		
		user.setMail(mail);
		    
		// faire vérif password/confirmation dans jsp
	    user.setPassword(password);
	        			
	    /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute(ATTRIBUT_FORM, form);
        request.setAttribute(ATTRIBUT_MAIL, mail);
	    
        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        //User user = form.inscrireUser(request,facade);
        if (!facade.userPresent(mail)) {
        	facade.ajoutUser(mail, password);
        	session.setAttribute(ATTRIBUT_SESSION_ID, mail);
        	session.setAttribute(ATTRIBUT_SESSION_USER, user);
        	this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        } 
        else {
        	request.setAttribute(ATTRIBUT_ERREUR, "Echec de l'inscription : cet email est déjà associé à un compte !");
        	this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
        }        
        
        
	}

}
