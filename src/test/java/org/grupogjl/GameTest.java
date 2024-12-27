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


}