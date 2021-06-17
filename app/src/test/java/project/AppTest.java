package project;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testAppInitialize() {
        App app = new App();
        assertNotNull(app);
    }
}
