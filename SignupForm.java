/**
 * 
 */
package intergiciels.forms;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jade BOUMAZA
 *
 */

public class SignupForm {
	
	/**
	 * Retourne null si un champ est vide, et son contenu sinon
	 * @param request
	 * @param nomChamp
	 */
	public String getValeurChamp(HttpServletRequest request, String nomChamp) {
	    String valeur = request.getParameter(nomChamp).toString();
	    if (valeur == null || valeur.length() == 0) {
	        return null;
	    } else {
	        return valeur;
	    }
	}

}
