package net.sourceforge.stripes.controller;

import net.sourceforge.stripes.util.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

/**
 * Tests for {@link ExecutionContext}.
 *
 * @author Ben Gunter
 */
public class ExecutionContextTests {

    private static final Log log = Log.getInstance(ExecutionContextTests.class);

    @Test
    public void testCurrentContext() throws Exception {
        log.debug("Testing ExecutionContext.currentContext()");
        final ExecutionContext ctx = new ExecutionContext();

        for (LifecycleStage stage : LifecycleStage.values()) {
            log.debug("Setting lifecycle stage: " + stage);
            ctx.setLifecycleStage(stage);

            List<Interceptor> interceptors = Collections.emptyList();
            ctx.setInterceptors(interceptors);

            ctx.wrap(context -> {
                Assertions.assertSame(ExecutionContext.currentContext(),
                                      ctx,
                                      "The current context is not what was expected!");
                return null;
            });
        }

        log.debug("Lifecycle complete. Making sure current context is null.");
        Assertions.assertNull(ExecutionContext.currentContext(),
                              "The current context was not cleared at the end of the lifecycle.");
    }
}
