/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
@FacesValidator("validators.UsernameREG")
public class UsernameREG implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String username_ = o.toString();

//        if (!username.equals("hello")) {
//            FacesMessage msg
//                    = new FacesMessage("URL validation failed", "Invalid URL format");
//            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//            throw new ValidatorException(msg);
//        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createNamedQuery("User.findByUsername");
        q.setParameter("username", username_);
        List users = q.getResultList();
        tx.commit();

        if ((users != null && users.size() == 1) || username_.toLowerCase().equals("admin") || username_.toLowerCase().equals("user") || username_.toLowerCase().equals("guest")) {
            em.close();
            FacesMessage msg = new FacesMessage("Username allready exists", "Username allready exists");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
//            return "register.xhtml";
        }

    }

}
