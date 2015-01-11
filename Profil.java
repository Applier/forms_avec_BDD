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
import intergiciels.beans.InfosCV;
import intergiciels.etc.Form;

/**
 * @author Jade BOUMAZA
 *
 */

@WebServlet
public class Profil extends HttpServlet {
	
	@EJB
	Facade facade;

	private static final long serialVersionUID = 1L;
	
	private static final String VUE = "/WEB-INF/profil.jsp";
    public static final String ATTRIBUT_FORM = "form";
    public static final String ATTRIBUT_INFOS = "infos";
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
			/* Affichage de la page de profil */
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
			
			/* Récupération des infos de l'user de la session */
			InfosCV infos = facade.getInfos(id);
			
			/* Préparation de l'objet formulaire */
			Form form = new Form();	
			
			/* Récupération des champs */
			String nom = form.getValeurChamp(request, "nom");
			String prenom = form.getValeurChamp(request, "prenom");
			String numTel = form.getValeurChamp(request, "numTel");
			String dateNaissance = form.getValeurChamp(request, "dateNaissance");
			String adresse = form.getValeurChamp(request, "adresse");
			String mailPro = form.getValeurChamp(request, "mailPro");
			
			/* Ajout du contenu des champs aux infos de l'user de la session */
			infos.setNom(nom);
			infos.setPrenom(prenom);
			infos.setNumTel(numTel);
			infos.setDateNaissance(dateNaissance);
			infos.setAdresse(adresse);
			infos.setMailPro(mailPro);
			
			/* Modification des infos de l'user de la session */
			user.setInfos(infos);
			
			/* Modification des infos dans la Base de Données */
			facade.setInfos(id, infos);
			
			
			
			session.setAttribute(ATTRIBUT_SESSION_USER, user);
			
			
			
			/* Stockage du formulaire et du bean dans l'objet request */
			request.setAttribute(ATTRIBUT_FORM, form);
			
			
			this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
		}
		
	}

}

	


