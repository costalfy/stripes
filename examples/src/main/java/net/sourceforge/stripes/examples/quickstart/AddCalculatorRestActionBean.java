package net.sourceforge.stripes.examples.quickstart;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.examples.bugzooky.ext.Public;
import net.sourceforge.stripes.validation.Validate;

/**
 * A very simple calculator action.
 * @author Tim Fennell
 */
@Public
@RestActionBean
@UrlBinding( "/calculate/add" )
public class AddCalculatorRestActionBean implements ActionBean {
    private ActionBeanContext context;
    @Validate(required=true) private double numberOne;
    @Validate(required=true) private double numberTwo;
    private double result;

    public ActionBeanContext getContext() { return context; }
    public void setContext(ActionBeanContext context) { this.context = context; }

    public Resolution post() {
        result = numberOne + numberTwo;
        return new JsonResolution( Double.toString( result ) );
    }
    
    public void setNumberOne( double numberOne ) { this.numberOne = numberOne; }
    public void setNumberTwo( double numberTwo ) { this.numberTwo = numberTwo; }
}
