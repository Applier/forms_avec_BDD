/**
 * 
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

import intergiciels.beans.Facade;
import intergiciels.beans.User;
import intergiciels.etc.Form;


/**
 * @author Jade BOUMAZA
 *
 */

@WebServlet("/signin")
public class Signin extends HttpServlet {	
	
	@EJB
	Facade facade;

	private static final long serialVersionUID = 1L;
	
	public static final String VUE = "/WEB-INF/signin.jsp";
	public static final String ATTRIBUT_PSEUDO = "pseudo";
    public static final String ATTRIBUT_FORM = "form";
    public static final String ATTRIBUT_SESSION_ID = "id";
    public static final String ATTRIBUT_SESSION_USER = "sessionUser";
    
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Affichage de la page de connexion */
		this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        Form form = new Form(); 
        
        String mail = form.getValeurChamp(request, "mail");
	    String password = form.getValeurChamp(request, "password");
	
        
        /* Récupération de la session depuis la requete */
        HttpSession session = request.getSession();
        
        /* Si aucune erreur de validation n'a eu lieu, alors ajout de la String
         * mail à la session, sinon suppression de la session */
        if (facade.userPresent(mail)) {
        	User user = facade.getUser(mail);
        	if (facade.verificationUser(mail, password)) {
        		System.out.println("Vérif mail password OK");
        		session.setAttribute(ATTRIBUT_SESSION_ID, mail);
        		session.setAttribute(ATTRIBUT_SESSION_USER, user);        		
	        } else {
	            session.setAttribute(ATTRIBUT_SESSION_ID, null);
	            session.setAttribute(ATTRIBUT_SESSION_USER, null);
	        }
        }
        else { 
        	// afficher erreur
        }
        
        
        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute(ATTRIBUT_FORM, form);
        request.setAttribute(ATTRIBUT_PSEUDO, mail);
        
        this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
	}		

}
