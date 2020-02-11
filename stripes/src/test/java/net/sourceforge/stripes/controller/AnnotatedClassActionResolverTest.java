package net.sourceforge.stripes.controller;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.UrlBinding;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class AnnotatedClassActionResolverTest {

    private AnnotatedClassActionResolver resolver = new AnnotatedClassActionResolver() {
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

    @UrlBinding("/Simple.action")
    static class SimpleActionBean implements ActionBean {

        public void setContext(ActionBeanContext context) {
        }

        public ActionBeanContext getContext() {
            return null;
        }
    }

    @UrlBinding("/Overloaded.action")
    static class OverloadedActionBean implements ActionBean {

        public void setContext(ActionBeanContext context) {
        }

        public ActionBeanContext getContext() {
            return null;
        }
    }

    static class Container1 {

        @UrlBinding("/container1/Overloaded.action")
        static class OverloadedActionBean implements ActionBean {

            public void setContext(ActionBeanContext context) {
            }

            public ActionBeanContext getContext() {
                return null;
            }
        }
    }

    static class Container2 {

        @UrlBinding("/container2/Overloaded.action")
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
    public void findByName() {
        Class<? extends ActionBean> actionBean = resolver.getActionBeanByName("SimpleActionBean");
        Assertions.assertNotNull(actionBean);
    }

    @Test
    public void multipleActionBeansWithSameSimpleName() {
        Class<? extends ActionBean> actionBean = resolver.getActionBeanByName("OverloadedActionBean");
        Assertions.assertNull(actionBean);
    }
}
