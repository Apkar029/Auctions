/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import java.time.Instant;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Admin
 */
@FacesValidator("validators.newOfferCategoryValidator")
public class NewOfferCategoryValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String categories_temp = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("hidden-tags");
        System.out.println("categories validator: " + categories_temp);
        if (categories_temp == null || categories_temp.equals("")) {
            FacesMessage msg = new FacesMessage("Please select one or more categories");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
