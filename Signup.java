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

import intergiciels.beans.Facade;
import intergiciels.beans.User;
import intergiciels.forms.SignupForm;

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
	public static final String ATTRIBUT_USER = "user";
    public static final String ATTRIBUT_FORM = "form";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Affichage de la page d'inscription */
		this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
        	SignupForm form = new SignupForm();
					
					
		    String mail = form.getValeurChamp(request, "mail");
		    String password = form.getValeurChamp(request, "password");
		   
		    User user = new User();

		    user.setMail(mail);
		    
		   // faire vérif password/confirmation dans jsp
	    	user.setPassword(password);	  
	    	

		
		
        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        //User user = form.inscrireUser(request,facade);
        if (!facade.userPresent(mail)) {
        	facade.ajoutUser(mail, password);
        }        
        
        // traiter cas de présence dans la BDD !!! (message d'erreur)
		
        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute(ATTRIBUT_FORM, form);
        request.setAttribute(ATTRIBUT_USER, user);
        
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
