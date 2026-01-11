package com.smartlibraryplus;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void testGetGreeting() {
        App app = new App();
        assertEquals("Hello, SmartLibraryPlus!", app.getGreeting());
    }
}
