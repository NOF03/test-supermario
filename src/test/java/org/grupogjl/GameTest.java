package org.grupogjl;

import org.grupogjl.gui.LanternaGui;
import org.grupogjl.state.StateGame;
import org.grupogjl.state.StateMenu;
import org.grupogjl.state.StatePause;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class GameTest {
    private LanternaGui mockGui;
    private Game game;


    @BeforeEach
    public void setUp() {
        // Mock dependencies
        mockGui = mock(LanternaGui.class);
        // Inject mocked dependencies
        game = new Game(mockGui);
    }

    @Test
    public void testGetInstance_CreatesSingleton() throws FontFormatException, IOException, URISyntaxException {
        Game instance1 = Game.getInstance();
        Game instance2 = Game.getInstance();

        assertThat(instance1).isSameAs(instance2);
    }

    @Test
    public void testGetState_DefaultIsStateMenu() {
        assertThat(game.getState()).isInstanceOf(StateMenu.class);
    }

    @Test
    public void testGetGui_ReturnsGuiInstance() {
        assertThat(game.getGui()).isEqualTo(mockGui);
    }

    @Test
    public void testSetStateMenu_SetsStateToStateMenu() {
        game.setStateMenu();
        assertThat(game.getState()).isInstanceOf(StateMenu.class);
    }

    @Test
    public void testSetStateGame_SetsStateToStateGame() throws IOException {
        game.setStateGame();
        assertThat(game.getState()).isInstanceOf(StateGame.class);
    }

    @Test
    public void testSetStatePause_SetsStateToStatePause() throws IOException {
        game.setStateGame(); // State must be StateGame before pause
        game.setStatePause();
        assertThat(game.getState()).isInstanceOf(StatePause.class);
    }

    @Test
    public void testSetStateNull_SetsStateToNull() {
        game.setStateNull();
        assertThat(game.getState()).isNull();
    }

    @Test
    public void testGetStateGame_WhenStateIsStateGame() throws IOException {
        game.setStateGame();
        StateGame stateGame = game.getStateGame();
        assertThat(stateGame).isNotNull();
    }

    @Test
    public void testGetStateGame_ThrowsExceptionWhenNotStateGame() {
        assertThrows(ClassCastException.class, () -> game.getStateGame());
    }

    @Test
    public void testGetStateMenu_WhenStateIsStateMenu() {
        StateMenu stateMenu = game.getStateMenu();
        assertThat(stateMenu).isNotNull();
    }

    @Test
    public void testGetStatePause_WhenStateIsStatePause() throws IOException {
        game.setStateGame();
        game.setStatePause();
        StatePause statePause = game.getStatePause();
        assertThat(statePause).isNotNull();
    }


}