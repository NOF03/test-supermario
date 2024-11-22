package org.grupogjl;

import org.grupogjl.gui.LanternaGui;
import org.grupogjl.state.StateGame;
import org.grupogjl.state.StateMenu;
import org.grupogjl.state.StatePause;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GameTests {
    private LanternaGui mockGui;
    private Game game;


    @BeforeEach
    void setUp() throws FontFormatException, IOException, URISyntaxException {
        // Mock dependencies
        mockGui = Mockito.mock(LanternaGui.class);
        // Inject mocked dependencies
        game = new Game(mockGui);
    }

    @Test
    void testGetInstance_CreatesSingleton() throws FontFormatException, IOException, URISyntaxException {
        Game instance1 = Game.getInstance();
        Game instance2 = Game.getInstance();

        assertThat(instance1).isSameAs(instance2);
    }

    @Test
    void testGetState_DefaultIsStateMenu() {
        assertThat(game.getState()).isInstanceOf(StateMenu.class);
    }

    @Test
    void testGetGui_ReturnsGuiInstance() {
        assertThat(game.getGui()).isEqualTo(mockGui);
    }

    @Test
    void testSetStateMenu_SetsStateToStateMenu() {
        game.setStateMenu();
        assertThat(game.getState()).isInstanceOf(StateMenu.class);
    }

    @Test
    void testSetStateGame_SetsStateToStateGame() throws IOException {
        game.setStateGame();
        assertThat(game.getState()).isInstanceOf(StateGame.class);
    }

    @Test
    void testSetStatePause_SetsStateToStatePause() throws IOException {
        game.setStateGame(); // State must be StateGame before pause
        game.setStatePause();
        assertThat(game.getState()).isInstanceOf(StatePause.class);
    }

    @Test
    void testSetStateNull_SetsStateToNull() {
        game.setStateNull();
        assertThat(game.getState()).isNull();
    }

    @Test
    void testGetStateGame_WhenStateIsStateGame() throws IOException {
        game.setStateGame();
        StateGame stateGame = game.getStateGame();
        assertThat(stateGame).isNotNull();
    }

    @Test
    void testGetStateGame_ThrowsExceptionWhenNotStateGame() {
        assertThrows(ClassCastException.class, () -> game.getStateGame());
    }

    @Test
    void testGetStateMenu_WhenStateIsStateMenu() {
        StateMenu stateMenu = game.getStateMenu();
        assertThat(stateMenu).isNotNull();
    }

    @Test
    void testGetStatePause_WhenStateIsStatePause() throws IOException {
        game.setStateGame();
        game.setStatePause();
        StatePause statePause = game.getStatePause();
        assertThat(statePause).isNotNull();
    }

    @Test
    void testRun_StopsWhenStateIsNull() throws InterruptedException, IOException {
        Game spyGame = Mockito.spy(game);

        // Set the state to null to trigger the exit in the run loop
        spyGame.setStateNull();

        Thread testThread = new Thread(() -> {
            try {
                spyGame.run();
            } catch (InterruptedException | IOException ignored) {
            }
        });

        // Start the run loop
        testThread.start();
        // Give it a moment to process
        Thread.sleep(100);
        // Interrupt the thread to stop the loop for the test
        testThread.interrupt();
        // Ensure the state is still null
        assertThat(spyGame.getState()).isNull();
    }

    @Test
    void testRun_CoversMainLoop() throws InterruptedException, IOException {
        // Mock the State to control the behavior
        StateGame mockStateGame = Mockito.mock(StateGame.class);

        // Use the existing method to set the state to StateGame
        game.setStateGame();  // Switch to StateGame
        game.setStateGame(mockStateGame);  // Use the mock object for StateGame

        // Spy on the game object to override the exit behavior
        Game spyGame = Mockito.spy(game);

        // Run the game loop in a separate thread
        Thread testThread = new Thread(() -> {
            try {
                spyGame.run();
            } catch (InterruptedException | IOException ignored) {
            }
        });

        // Start the run loop
        testThread.start();
        // Let the loop run for a short duration to cover the lines
        Thread.sleep(200);

        // Interrupt the loop to stop execution
        testThread.interrupt();
        // Ensure that the state was used in the loop
        verify(mockStateGame, atLeastOnce()).step(eq(spyGame), any(LanternaGui.class), anyLong());
    }

}