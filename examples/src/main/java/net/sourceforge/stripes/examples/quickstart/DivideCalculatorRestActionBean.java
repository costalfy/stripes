package net.sourceforge.stripes.examples.quickstart;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.examples.bugzooky.ext.Public;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

/**
 * A very simple calculator action.
 * @author Tim Fennell
 */
@Public
@RestActionBean
@UrlBinding( "/calculate/divide" )
public class DivideCalculatorRestActionBean implements ActionBean {
    private ActionBeanContext context;
    @Validate(required=true) private double numberOne;
    @Validate(required=true) private double numberTwo;
    private double result;
    private String errorMessage;



    public ActionBeanContext getContext() { return context; }
    public void setContext(ActionBeanContext context) { this.context = context; }


    /** An event handler method that divides number one by number two. */
    public Resolution post() {
        result = numberOne / numberTwo;
        return new JsonResolution( Double.toString( result ) );
    }

    /**
     * An example of a custom validation that checks that division operations
     * are not dividing by zero.
     */
    @ValidationMethod(on="post")
    public void avoidDivideByZero(ValidationErrors errors) {

        if (this.numberTwo == 0) {
            //errors.add("numberTwo", new JsonError("Dividing by zero is not allowed."));
            errorMessage = "Dividing by zero is not allowed.";
        }
    }
    
    public void setNumberOne( double numberOne ) { this.numberOne = numberOne; }
    public void setNumberTwo( double numberTwo ) { this.numberTwo = numberTwo; }
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
