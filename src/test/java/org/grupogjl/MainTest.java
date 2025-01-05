package org.grupogjl;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

public class MainTest {

    @Test
    public void testMain() throws IOException, URISyntaxException, FontFormatException, InterruptedException {
        // Mock the Game class
        Game mockGame = mock(Game.class);

        // Mock Game.getInstance() to return the mocked instance
        try (var mockedGameClass = mockStatic(Game.class)) {
            mockedGameClass.when(Game::getInstance).thenReturn(mockGame);

            // Call the main method
            Main.main(new String[]{});

            // Verify that Game.getInstance() was called
            mockedGameClass.verify(Game::getInstance);
            // Verify that run() was called on the mocked game instance
            verify(mockGame).run();
        }
    }
}
