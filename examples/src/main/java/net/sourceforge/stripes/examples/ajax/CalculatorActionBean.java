package net.sourceforge.stripes.examples.ajax;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.examples.bugzooky.ext.Public;
import net.sourceforge.stripes.validation.*;

import java.io.StringReader;
import java.util.List;

/**
 * A very simple calculator action that is designed to work with an ajax front end.
 * Handles 'add' and 'divide' events just like the non-ajax calculator. Each event
 * calculates the result, and then "streams" it back to the browser. Implements the
 * ValidationErrorHandler interface to intercept any validation errors, convert them
 * to an HTML message and stream the back to the browser for display.
 *
 * @author Tim Fennell
 */
@Public
@UrlBinding("/Calculator.action")
public class CalculatorActionBean implements ActionBean, ValidationErrorHandler {
    private ActionBeanContext context;
    @Validate(required=true) private double numberOne;
    @Validate(required=true) private double numberTwo;

    public ActionBeanContext getContext() { return context; }
    public void setContext(ActionBeanContext context) { this.context = context; }

    /** Converts errors to HTML and streams them back to the browser. */
    public Resolution handleValidationErrors(ValidationErrors errors) {
        StringBuilder message = new StringBuilder();

        for (List<ValidationError> fieldErrors : errors.values()) {
            for (ValidationError error : fieldErrors) {
                message.append("<div style=\"color: firebrick;\">");
                message.append(error.getMessage(getContext().getLocale()));
                message.append("</div>");
            }
        }

        return new StreamingResolution("text/html", new StringReader(message.toString()));
    }

    /** Handles the 'add' event, adds the two numbers and returns the result. */
    @DefaultHandler public Resolution add() {
        String result = String.valueOf(numberOne + numberTwo);
        return new StreamingResolution("text", new StringReader(result));
    }

    /** Handles the 'divide' event, divides number two by oneand returns the result. */
    public Resolution divide() {
        String result = String.valueOf(numberOne / numberTwo);
        return new StreamingResolution("text", new StringReader(result));
    }

    /**
     * An example of a custom validation that checks that division operations
     * are not dividing by zero.
     */
    @ValidationMethod(on="divide")
    public void avoidDivideByZero(ValidationErrors errors) {
        if (this.numberTwo == 0) {
            errors.add("numberTwo", new SimpleError("Dividing by zero is not allowed."));
        }
    }


    // Standard getter and setter methods
    public double getNumberOne() { return numberOne; }
    public void setNumberOne(double numberOne) { this.numberOne = numberOne; }

    public double getNumberTwo() { return numberTwo; }
    public void setNumberTwo(double numberTwo) { this.numberTwo = numberTwo; }
}
