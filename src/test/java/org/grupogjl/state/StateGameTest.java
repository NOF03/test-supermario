package org.grupogjl.state;

import org.grupogjl.Game;
import org.grupogjl.controller.ControllerGame;
import org.grupogjl.gui.GeneralGui;
import org.grupogjl.gui.LanternaGui;
import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.viewer.Viewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StateGameTest {

    private StateGame stateGame;
    private LanternaGui mockGui;
    private Game mockGame;
    private Viewer mockViewer;

    @BeforeEach
    public void setUp() throws IOException {
        mockGui = mock(LanternaGui.class);
        mockGame = mock(Game.class);
        mockViewer = mock(Viewer.class);

        stateGame = new StateGame();
        stateGame.setViewer(mockViewer); // Replace real ViewerGame with a mock
    }

    @Test
    public void testInitialState() {
        assertThat(stateGame.getState()).isEqualTo(2);
        assertThat(stateGame.isGameOver()).isFalse();
    }

    @Test
    public void testGetAndSetLevel() {
        Level mockLevel = mock(Level.class);
        stateGame.setLevel(mockLevel);
        assertThat(stateGame.getLevel()).isSameAs(mockLevel);
    }

    @Test
    public void testDraw() throws IOException {
        stateGame.draw(mockGui);
        verify(mockViewer).draw(stateGame, mockGui); // Verify interaction with the mock viewer
    }

    @Test
    public void testDrawThrowsRuntimeException() throws IOException {
        GeneralGui mockGui = mock(GeneralGui.class);
        Viewer mockViewer = mock(Viewer.class);
        stateGame.setViewer(mockViewer);

        // Simulate IOException being thrown
        doThrow(new IOException("Test Exception")).when(mockViewer).draw(eq(stateGame), eq(mockGui));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> stateGame.draw(mockGui));
        assertThat(exception.getMessage()).isEqualTo("java.io.IOException: Test Exception");
    }

    @Test
    public void testGetModel() {
        Level mockLevel = mock(Level.class);
        stateGame.setLevel(mockLevel);

        Level result = stateGame.getModel();

        assertThat(result).isEqualTo(mockLevel);
    }

    @Test
    public void testSetGameOver() {
        stateGame.setGameOver(true);
        assertThat(stateGame.isGameOver()).isTrue();

        stateGame.setGameOver(false);
        assertThat(stateGame.isGameOver()).isFalse();
    }

    @Test
    public void testStep() throws IOException {
        ControllerGame mockController = mock(ControllerGame.class);
        stateGame.setController(mockController);

        when(mockGui.getNextAction()).thenReturn(LanternaGui.ACTION.UP);

        stateGame.step(mockGame, mockGui, 12345L);

        verify(mockController).step(mockGame, LanternaGui.ACTION.UP, 12345L);
        verify(mockViewer).draw(stateGame, mockGui);
        verify(mockGui).refresh();
    }

    @Test
    public void testResetLevel() throws IOException {
        Mario mario = stateGame.getLevel().getMario(); // Use real Mario instance
        Level initialLevel = stateGame.getLevel();

        stateGame.resetLevel();

        assertThat(stateGame.getLevel()).isNotSameAs(initialLevel); // New level is created

        assertThat(mario.getX()).isEqualTo(0);
        assertThat(mario.getY()).isEqualTo(0);
    }

    @Test
    public void testNextLevel() throws IOException {
        Mario mario = stateGame.getLevel().getMario();
        int initialLevelNumber = stateGame.getLeveln();

        stateGame.nextLevel();

        assertThat(stateGame.getLeveln()).isEqualTo(initialLevelNumber + 1); // Level number increments
        assertThat(mario.getX()).isEqualTo(0);
        assertThat(mario.getY()).isEqualTo(0);
    }
}
