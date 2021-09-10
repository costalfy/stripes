package net.sourceforge.stripes.controller;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.UrlBinding;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests for various methods in the NameBasedActionResolver that can be tested
 * in isolation. The resolver is also tested by a lot of the mock tests that run
 * from request through the action layer.
 *
 * @author Tim Fennell
 */
public class NameBasedActionResolverTest {

    private NameBasedActionResolver resolver = new NameBasedActionResolver() {
        @Override
        protected Set<Class<? extends ActionBean>> findClasses() {
            Set<Class<? extends ActionBean>> classes = new HashSet<>();
            classes.add(SimpleActionBean.class);
            classes.add(OverloadedActionBean.class);
            classes.add(Container1.OverloadedActionBean.class);
            classes.add(Container2.OverloadedActionBean.class);
            return classes;
        }
    };

    static class SimpleActionBean implements ActionBean {

        public void setContext(ActionBeanContext context) {
        }

        public ActionBeanContext getContext() {
            return null;
        }
    }

    static class OverloadedActionBean implements ActionBean {

        public void setContext(ActionBeanContext context) {
        }

        public ActionBeanContext getContext() {
            return null;
        }
    }

    static class Container1 {

        static class OverloadedActionBean implements ActionBean {

            public void setContext(ActionBeanContext context) {
            }

            public ActionBeanContext getContext() {
                return null;
            }
        }
    }

    static class Container2 {

        static class OverloadedActionBean implements ActionBean {

            public void setContext(ActionBeanContext context) {
            }

            public ActionBeanContext getContext() {
                return null;
            }
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        resolver.init(null);
    }

    @Test
    public void generateBinding() {
        String binding = this.resolver.getUrlBinding("foo.bar.web.admin.ControlCenterActionBean");
        Assertions.assertEquals(binding,
                                "/admin/ControlCenter.action");
    }

    @Test
    public void generateBindingForNonPackagedClass() {
        String binding = this.resolver.getUrlBinding("ControlCenterActionBean");
        Assertions.assertEquals(binding,
                                "/ControlCenter.action");
    }

    @Test
    public void generateBindingForClassWithSingleBasePackage() {
        String binding = this.resolver.getUrlBinding("www.ControlCenterActionBean");
        Assertions.assertEquals(binding,
                                "/ControlCenter.action");
    }

    @Test
    public void generateBindingWithMultipleBasePackages() {
        String binding = this.resolver.getUrlBinding("foo.web.stripes.bar.www.ControlCenterActionBean");
        Assertions.assertEquals(binding,
                                "/ControlCenter.action");
    }

    @Test
    public void generateBindingWithMultipleBasePackages2() {
        String binding = this.resolver.getUrlBinding("foo.web.stripes.www.admin.ControlCenterActionBean");
        Assertions.assertEquals(binding,
                                "/admin/ControlCenter.action");
    }

    @Test
    public void generateBindingWithoutSuffix() {
        String binding = this.resolver.getUrlBinding("foo.web.stripes.www.admin.ControlCenter");
        Assertions.assertEquals(binding,
                                "/admin/ControlCenter.action");
    }

    @Test
    public void generateBindingWithDifferentSuffix() {
        String binding = this.resolver.getUrlBinding("foo.web.stripes.www.admin.ControlCenterBean");
        Assertions.assertEquals(binding,
                                "/admin/ControlCenter.action");
    }

    @Test
    public void generateBindingWithDifferentSuffix2() {
        String binding = this.resolver.getUrlBinding("foo.web.stripes.www.admin.ControlCenterAction");
        Assertions.assertEquals(binding,
                                "/admin/ControlCenter.action");
    }

    @Test
    public void testWithAnnotatedClass() {
        String name = net.sourceforge.stripes.test.TestActionBean.class.getName();
        String binding = this.resolver.getUrlBinding(name);
        Assertions.assertEquals(binding,
                                "/test/Test.action");

        binding = this.resolver.getUrlBinding(net.sourceforge.stripes.test.TestActionBean.class);
        Assertions.assertEquals(binding,
                                net.sourceforge.stripes.test.TestActionBean.class.
                                        getAnnotation(UrlBinding.class)
                                        .value());
    }

    @Test
    public void testGetFindViewAttempts() {
        String urlBinding = "/account/ViewAccount.action";
        List<String> viewAttempts = this.resolver.getFindViewAttempts(urlBinding);
        Assertions.assertEquals(viewAttempts.size(),
                                3);
        Assertions.assertEquals(viewAttempts.get(0),
                                "/account/ViewAccount.jsp");
        Assertions.assertEquals(viewAttempts.get(1),
                                "/account/viewAccount.jsp");
        Assertions.assertEquals(viewAttempts.get(2),
                                "/account/view_account.jsp");
    }

    @Test
    public void testFindByNameWithSuffixes() {
        Assertions.assertNotNull(resolver.getActionBeanByName("Simple"));
        Assertions.assertNotNull(resolver.getActionBeanByName("SimpleAction"));
    }

    @Test
    public void testOverloadedBeanNameWithSuffixes() {
        Assertions.assertNull(resolver.getActionBeanByName("Overloaded"));
    }
}
