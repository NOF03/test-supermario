package org.grupogjl;

import org.grupogjl.gui.LanternaGui;
import org.grupogjl.state.StateGame;
import org.grupogjl.state.StateMenu;
import org.grupogjl.state.StatePause;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GameTest {
    private LanternaGui mockGui;
    private Game game;


    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockGui = mock(LanternaGui.class);
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
        StateGame mockStateGame = mock(StateGame.class);

        // Use the existing method to set the state to StateGame
        game.setStateGame();  // Switch to StateGame
        game.setStateGame(mockStateGame);  // Use the mock object for StateGame

        // Spy on the game object to override the exit behavior
        Game spyGame = spy(game);

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

    @Test
    void testRun_SleepTimeLessThanOrEqualToZero() throws InterruptedException, IOException {
        // Mock the state
        StateGame mockStateGame = mock(StateGame.class);

        // Set the mocked state
        game.setStateGame();
        game.setStateGame(mockStateGame);

        // Spy on the game to control the behavior
        Game spyGame = Mockito.spy(game);

        // Use CountDownLatch to ensure synchronization
        CountDownLatch latch = new CountDownLatch(1);

        // Override the state loop behavior to simulate elapsed time exceeding the target frame time
        doAnswer(invocation -> {
            Thread.sleep(20); // Simulate processing time exceeding the frame time
            latch.countDown(); // Signal that the step was called
            return null;
        }).when(mockStateGame).step(eq(spyGame), any(LanternaGui.class), anyLong());

        // Run the game loop in a separate thread
        Thread testThread = new Thread(() -> {
            try {
                spyGame.run();
            } catch (InterruptedException | IOException ignored) {
            }
        });

        // Start the loop
        testThread.start();

        // Wait for the latch to ensure the step was called
        latch.await(400, TimeUnit.MILLISECONDS);

        // Interrupt the thread to stop execution
        testThread.interrupt();

        // Wait for the thread to finish
        testThread.join();

        // Verify that the state was used in the loop
        verify(mockStateGame, atLeastOnce()).step(eq(spyGame), any(LanternaGui.class), anyLong());

        // Assert that the state was processed and the thread stopped
        assertThat(testThread.isAlive()).isFalse();
    }


}