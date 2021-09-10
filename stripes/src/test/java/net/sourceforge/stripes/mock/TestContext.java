package net.sourceforge.stripes.mock;

import net.sourceforge.stripes.StripesTestFixture;
import net.sourceforge.stripes.action.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author JC Carrillo
 */
public class TestContext {

    private static final String MESSAGE = "This is a message";

    @UrlBinding("/simple")
    public static class ContextActionBean implements ActionBean {

        private ActionBeanContext context;

        @DefaultHandler
        public Resolution view() {
            return null;
        }

        public Resolution messages() {
            getContext().getMessages().add(new SimpleMessage(MESSAGE));
            return null;
        }

        public void setContext(ActionBeanContext context) {
            this.context = context;
        }

        public ActionBeanContext getContext() {
            return context;
        }
    }

    @Test
    public void testMessagesNothingInContext() throws Exception {
        MockServletContext c = StripesTestFixture.createServletContext();
        MockRoundtrip mockRoundtrip = new MockRoundtrip(c, ContextActionBean.class);
        mockRoundtrip.execute();
        ContextActionBean contextActionBean = mockRoundtrip.getActionBean(ContextActionBean.class);
        ActionBeanContext context = contextActionBean.getContext();
        Assertions.assertNotNull(context);
        List<Message> messages = context.getMessages();
        Assertions.assertNotNull(messages);
    }

    @Test
    public void testMessagesWithMessages() throws Exception {
        Assertions.assertThrows(NullPointerException.class,
                                () -> {
                                    MockServletContext c = StripesTestFixture.createServletContext();
                                    MockRoundtrip mockRoundtrip = new MockRoundtrip(c,
                                                                                    ContextActionBean.class);
                                    mockRoundtrip.execute("messages");
                                    ContextActionBean contextActionBean = mockRoundtrip.getActionBean(ContextActionBean.class);
                                    ActionBeanContext context = contextActionBean.getContext();
                                    Assertions.assertNotNull(context);
                                    List<Message> messages = context.getMessages();
                                    Assertions.assertNotNull(messages);
                                });
    }

    @Test
    public void testMessages() throws Exception {
        MockServletContext c = StripesTestFixture.createServletContext();
        final MockRoundtrip mockRoundtrip = new MockRoundtrip(c,
                                                              ContextActionBean.class);
        mockRoundtrip.execute("messages");
        List<Message> messages = mockRoundtrip.getMessages();
        Assertions.assertNotNull(messages);
        Assertions.assertEquals(1,
                                messages.size());
        Message message = messages.get(0);
        Assertions.assertTrue(message instanceof SimpleMessage);
        Assertions.assertEquals(MESSAGE,
                                ((SimpleMessage) message).getMessage());
    }
}
