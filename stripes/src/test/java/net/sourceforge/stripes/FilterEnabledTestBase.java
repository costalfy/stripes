package net.sourceforge.stripes;

import net.sourceforge.stripes.mock.MockServletContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class FilterEnabledTestBase {

    static MockServletContext context;

    @BeforeAll
    public static void initCtx() {
        context = StripesTestFixture.createServletContext();
    }

    @AfterAll
    public static void closeCtx() {
        context.close();
    }

    public MockServletContext getMockServletContext() {
        return context;
    }
}
