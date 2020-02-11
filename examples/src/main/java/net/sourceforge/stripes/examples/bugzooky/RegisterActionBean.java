package net.sourceforge.stripes.examples.bugzooky;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.examples.bugzooky.biz.Person;
import net.sourceforge.stripes.examples.bugzooky.biz.PersonManager;
import net.sourceforge.stripes.examples.bugzooky.ext.Public;
import net.sourceforge.stripes.validation.*;

/**
 * ActionBean that handles the registration of new users.
 *
 * @author Tim Fennell
 */
@Public
@Wizard(startEvents = "start")
@UrlBinding( "/bugzooky/Register.action" )
public class RegisterActionBean extends BugzookyActionBean {
    @ValidateNestedProperties({
        @Validate(field="username", required=true, minlength=5, maxlength=20),
        @Validate(field="password", required=true, minlength=5, maxlength=20),
        @Validate(field="firstName", required=true, maxlength=50),
        @Validate(field="lastName", required=true, maxlength=50)
    })
    private Person user;

    @Validate(required=true, minlength=5, maxlength=20) // TODO bug : using expression doesn't work expression="this == user.password")
    private String confirmPassword;

    /** The user being registered. */
    public void setUser(Person user) { this.user = user; }

    /** The user being registered. */
    public Person getUser() { return user; }

    /** The 2nd/confirmation password entered by the user. */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /** The 2nd/confirmation password entered by the user. */
    public String getConfirmPassword() { return confirmPassword; }

    /**
     * Validates that the two passwords entered match each other, and that the
     * username entered is not already taken in the system.
     */
    @ValidationMethod
    public void validate(ValidationErrors errors) {
        if ( new PersonManager().getPerson(this.user.getUsername()) != null ) {
            errors.add("user.username", new LocalizableError("usernameTaken"));
        }
        if (confirmPassword!=null && !confirmPassword.equals(user.getPassword())) {
            errors.add("confirmPassword", new LocalizableError("confirmPassword.valueFailedExpression"));
        }
    }

    @DontBind
    @DefaultHandler
    public Resolution start() {
        return new ForwardResolution("/bugzooky/Register.jsp");
    }

    public Resolution gotoStep2() {
        return new ForwardResolution("/bugzooky/Register2.jsp");
    }

    /**
     * Registers a new user, logs them in, and redirects them to the bug list page.
     */
    public Resolution register() {
        PersonManager pm = new PersonManager();
        pm.saveOrUpdate(this.user);
        getContext().setUser(this.user);
        getContext().getMessages().add(
                new LocalizableMessage(getClass().getName() + ".successMessage",
                                       this.user.getFirstName(),
                                       this.user.getUsername()));

        return new RedirectResolution(BugListActionBean.class);
    }
}
