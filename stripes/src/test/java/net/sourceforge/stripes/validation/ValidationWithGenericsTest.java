package net.sourceforge.stripes.validation;

import net.sourceforge.stripes.FilterEnabledTestBase;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.mock.MockRoundtrip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests some cases where generics have been known to mess up validation.
 *
 * @author Ben Gunter
 */
public class ValidationWithGenericsTest extends FilterEnabledTestBase {

    public static class User {

        private String username, password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class AdminUser extends User {
    }

    public static class SuperUser extends AdminUser {
    }

    public static class BaseActionBean<T> implements ActionBean {

        private ActionBeanContext context;
        private T model;

        public T getModel() {
            return model;
        }

        public void setModel(T model) {
            this.model = model;
        }

        public ActionBeanContext getContext() {
            return context;
        }

        public void setContext(ActionBeanContext context) {
            this.context = context;
        }
    }

    public static class OverrideGetterAndSetterActionBean extends BaseActionBean<User> {

        @Override
        @ValidateNestedProperties({
            @Validate(field = "username", required = true)
            ,
                @Validate(field = "password", required = true)
        })
        public User getModel() {
            return super.getModel();
        }

        @Override
        public void setModel(User user) {
            super.setModel(user);
        }

        public Resolution login() {
            return null;
        }
    }

    public static class OverrideGetterActionBean extends BaseActionBean<User> {

        @Override
        @ValidateNestedProperties({
            @Validate(field = "username", required = true)
            ,
                @Validate(field = "password", required = true)
        })
        public User getModel() {
            return super.getModel();
        }

        public Resolution login() {
            return null;
        }
    }

    public static class OverrideSetterActionBean extends BaseActionBean<User> {

        @Override
        @ValidateNestedProperties({
            @Validate(field = "username", required = true)
            ,
                @Validate(field = "password", required = true)
        })
        public void setModel(User user) {
            super.setModel(user);
        }

        public Resolution login() {
            return null;
        }
    }

    public static class OverloadSetterActionBean extends BaseActionBean<User> {

        @Override
        @ValidateNestedProperties({
            @Validate(field = "username", required = true)
            ,
                @Validate(field = "password", required = true)
        })
        public void setModel(User user) {
            super.setModel(user);
        }

        public void setModel(AdminUser user) {
        }

        public void setModel(SuperUser user) {
        }

        public void setModel(String string) {
        }

        public void setModel(Integer integer) {
        }

        public Resolution login() {
            return null;
        }
    }

    public static class ExtendOverloadSetterActionBean extends OverloadSetterActionBean {
    }

    public static class ExtendOverloadSetterAgainActionBean extends ExtendOverloadSetterActionBean {

        @Override
        @ValidateNestedProperties({
            @Validate(field = "username", required = true)
            ,
                @Validate(field = "password", required = true)
        })
        public void setModel(User user) {
            super.setModel(user);
        }
    }

    /**
     * Attempts to trigger validation errors on an ActionBean declared with a
     * type parameter. Validation was crippled by a bug in JDK6 and earlier.
     *
     * @see //stripesframework.atlassian.net/browse/STS-664
     */
    @Test
    public void testActionBeanWithTypeParameter() throws Exception {
        runValidationTests(OverrideGetterAndSetterActionBean.class);
        runValidationTests(OverrideGetterActionBean.class);
        runValidationTests(OverrideSetterActionBean.class);
    }

    @Test
    @Disabled
    public void testActionBeanWithTypeParameterFailsRandomlyOnJava8() throws Exception {
        runValidationTests(OverloadSetterActionBean.class);
        runValidationTests(ExtendOverloadSetterActionBean.class);
        runValidationTests(ExtendOverloadSetterAgainActionBean.class);
    }

    protected void runValidationTests(Class<? extends BaseActionBean<User>> type) throws Exception {
        // Trigger the validation errors
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(),
                                               type);
        trip.execute("login");
        ValidationErrors errors = trip.getValidationErrors();
        Assertions.assertNotNull(errors,
                                 "Expected validation errors but got none");
        Assertions.assertFalse(errors.isEmpty(),
                               "Expected validation errors but got none");
        Assertions.assertEquals(errors.size(),
                                2,
                                "Expected two validation errors but got " + errors.size());

        // Now add the required parameters and make sure the validation errors don't happen
        trip.addParameter("model.username",
                          "Scooby");
        trip.addParameter("model.password",
                          "Shaggy");
        trip.execute("login");
        errors = trip.getValidationErrors();
        Assertions.assertTrue(errors == null || errors.isEmpty(),
                              "Got unexpected validation errors");

        BaseActionBean<User> bean = trip.getActionBean(type);
        Assertions.assertNotNull(bean);
        Assertions.assertNotNull(bean.getModel());
        Assertions.assertEquals(bean.getModel()
                                        .getUsername(),
                                "Scooby");
        Assertions.assertEquals(bean.getModel()
                                        .getPassword(),
                                "Shaggy");
    }

    public static void main(String[] args) throws Exception {
        new ValidationWithGenericsTest().testActionBeanWithTypeParameter();
    }
}
